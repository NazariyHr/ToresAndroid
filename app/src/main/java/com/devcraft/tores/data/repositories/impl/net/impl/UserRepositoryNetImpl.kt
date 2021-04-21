package com.devcraft.tores.data.repositories.impl.net.impl

import android.util.Log
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.common_results.ResultStatus
import com.devcraft.tores.data.repositories.contract.common_results.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.LogInRequest
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.LogInTokenMapper
import com.devcraft.tores.data.repositories.impl.net.retrofit_apis.UserApi
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepositoryNetImpl(
    private val userApi: UserApi,
    private val tokenRepository: TokenRepository,
    private val logInTokenMapper: LogInTokenMapper,
) : BaseNetRepository(), UserRepository {

    override suspend fun login(
        email: String,
        password: String,
        token: String
    ): ResultStatus {
        return suspendCoroutine { continuation ->
            userApi
                .login(LogInRequest(email, password, token))
                .enqueue(object : Callback<Any> {
                    override fun onResponse(
                        call: Call<Any>,
                        response: Response<Any>
                    ) {
                        var resultStatus = ResultStatus.failure()

                        if (response.body() != null) {
                            response.body()?.let {
                                //todo uncomment
                                Log.d("login response", it as String)
                                ResultStatus.success()

//                                resultStatus = if (it.token.isNotEmpty()) {
//                                    tokenRepository.saveToken(logInTokenMapper.mapDtoToEntity(it))
//                                    ResultStatus.success()
//                                } else {
//                                    ResultStatus.failure("Token is empty, Status: ${it.status}, response message: ${it.message.orEmpty()}")
//                                }
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

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        continuation.resume(ResultStatus.failure(t))
                    }
                })
        }
    }

//    override suspend fun getUser(): ResultWithStatus<User> {
//        return suspendCoroutine { continuation ->
//            userApi.getProfile(tokenRepository.getToken().token)
//                .enqueue(object : Callback<ProfileResponse> {
//                    override fun onResponse(
//                        call: Call<ProfileResponse>,
//                        response: Response<ProfileResponse>
//                    ) {
//                        val result = parseResult(response, profileMapper)
//                        if (result.data == null) {
//                            continuation.resume(
//                                ResultWithStatus(
//                                    null,
//                                    ResultStatus.failure("User is null")
//                                )
//                            )
//                        } else {
//                            continuation.resume(ResultWithStatus(result.data, result.status))
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
//                    }
//                })
//        }
//    }
}