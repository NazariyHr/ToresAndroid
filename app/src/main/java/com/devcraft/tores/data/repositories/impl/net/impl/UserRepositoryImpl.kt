package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.*
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.ChangePasswordResponseMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.GetUserMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.LogInTokenMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.UserApi
import com.devcraft.tores.entities.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val tokenRepository: TokenRepository,
    private val logInTokenMapper: LogInTokenMapper,
    private val getUserMapper: GetUserMapper,
    private val changePasswordResponseMapper: ChangePasswordResponseMapper
) : BaseNetRepository(), UserRepository {

    override suspend fun login(
        email: String,
        password: String,
        token: String
    ): ResultStatus {
        return suspendCoroutine { continuation ->
            userApi
                .login(LogInRequest(email, password, token))
                .enqueue(object : Callback<LogInResponse> {
                    override fun onResponse(
                        call: Call<LogInResponse>,
                        response: Response<LogInResponse>
                    ) {
                        var resultStatus: ResultStatus

                        if (response.body() != null) {
                            response.body()!!.let {
                                resultStatus =
                                    if (it.token.plainTextToken.isNotEmpty()) {
                                        tokenRepository.saveToken(logInTokenMapper.mapDtoToEntity(it))
                                        ResultStatus.success()
                                    } else {
                                        ResultStatus.failure("Token is empty, response code: ${response.code()}, response message: ${it.error}")
                                    }
                            }
                        } else {
                            //something get wrong
                            resultStatus = if (response.errorBody() != null) {
                                val jObjError = JSONObject(response.errorBody()!!.string())
                                val error = jObjError.getString("error")
                                if (!error.isNullOrEmpty()) {
                                    ResultStatus.failure(error)
                                } else {
                                    ResultStatus.failure("Authorize error, response code: ${response.code()}")
                                }
                            } else {
                                ResultStatus.failure("Authorize error, response code: ${response.code()}")
                            }
                        }

                        continuation.resume(resultStatus)
                    }

                    override fun onFailure(
                        call: Call<LogInResponse>,
                        t: Throwable
                    ) {
                        continuation.resume(ResultStatus.failure(t))
                    }
                })
        }
    }

    override suspend fun getUser(): ResultWithStatus<User> {
        return suspendCoroutine { continuation ->
            userApi.getUser(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetUserResponse> {
                    override fun onResponse(
                        call: Call<GetUserResponse>,
                        response: Response<GetUserResponse>
                    ) {
                        val result = parseResult(response, getUserMapper)
                        if (result.data == null) {
                            continuation.resume(
                                ResultWithStatus(
                                    null,
                                    ResultStatus.failure("User is null")
                                )
                            )
                        } else {
                            continuation.resume(ResultWithStatus(result.data, result.status))
                        }
                    }

                    override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }

    override suspend fun changePassword(
        oldPass: String,
        newPass: String,
        newPassConfirm: String
    ): ResultWithStatus<String> {
        return suspendCoroutine { continuation ->
            userApi.changePassword(
                tokenRepository.getToken().bearerToken,
                ChangePasswordRequest(oldPass, newPass, newPassConfirm)
            )
                .enqueue(object : Callback<ChangePasswordResponse> {
                    override fun onResponse(
                        call: Call<ChangePasswordResponse>,
                        response: Response<ChangePasswordResponse>
                    ) {
                        val result = parseResult(response, changePasswordResponseMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }

    override suspend fun setFinancePassword(pass: String, passConfirm: String): ResultStatus {
        return suspendCoroutine { continuation ->
            userApi.setFinancePassword(
                tokenRepository.getToken().bearerToken,
                SetFinancePasswordRequest(pass, passConfirm)
            )
                .enqueue(object : Callback<NetworkBaseResponse> {
                    override fun onResponse(
                        call: Call<NetworkBaseResponse>,
                        response: Response<NetworkBaseResponse>
                    ) {
                        val result = parseStatus(response)
                        continuation.resume(result)
                    }

                    override fun onFailure(call: Call<NetworkBaseResponse>, t: Throwable) {
                        continuation.resume(ResultStatus.failure(t))
                    }
                })
        }
    }

    override suspend fun removeFinancePassword(): ResultStatus {
        return suspendCoroutine { continuation ->
            userApi.removeFinancePassword(
                tokenRepository.getToken().bearerToken
            )
                .enqueue(object : Callback<NetworkBaseResponse> {
                    override fun onResponse(
                        call: Call<NetworkBaseResponse>,
                        response: Response<NetworkBaseResponse>
                    ) {
                        val result = parseStatus(response)
                        continuation.resume(result)
                    }

                    override fun onFailure(call: Call<NetworkBaseResponse>, t: Throwable) {
                        continuation.resume(ResultStatus.failure(t))
                    }
                })
        }
    }
}
