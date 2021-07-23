package com.devcraft.tores.data.repositories.impl.net.impl.base

import com.devcraft.tores.R
import com.devcraft.tores.app.App
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

open class BaseNetRepository {
    fun <TypeOfDto : NetworkBaseResponse, TypeOfEntity : Any?> parseResult(
        response: Response<TypeOfDto>,
        mapper: BaseRepositoryMapper<TypeOfDto, TypeOfEntity>
    ): ResultWithStatus<TypeOfEntity> {
        var resultData: TypeOfEntity? = null
        var resultStatus = ResultStatus.failure()

        if (response.body() != null) {
            response.body()?.let {
                resultStatus = if (it.success) {
                    try {
                        resultData = mapper.mapDtoToEntity(it)
                        ResultStatus.success()
                    } catch (e: Exception) {
                        ResultStatus.failure(e)
                    }
                } else {
                    if (!it.error.isNullOrEmpty()) {
                        ResultStatus.failure(it.error.orEmpty())
                    } else {
                        ResultStatus.failure(
                            App.instance.getString(R.string.request_error_code, response.code())
                        )
                    }
                }
            }
        } else {
            //something get wrong
            resultStatus = parseError(response)
        }
        return ResultWithStatus(resultData, resultStatus)
    }

    fun <TypeOfDto : NetworkBaseResponse> parseError(
        response: Response<TypeOfDto>
    ): ResultStatus {
        return if (response.errorBody() != null) {
            try {
                val jObjError = JSONObject(response.errorBody()!!.string())
                val error = jObjError.getString("error")
                if (!error.isNullOrEmpty()) {
                    ResultStatus.failure(error)
                } else {
                    ResultStatus.failure(
                        App.instance.getString(R.string.request_error_code, response.code())
                    )
                }
            } catch (e: Exception) {
                if (response.code() == 403) {
                    ResultStatus.failure(
                        App.instance.getString(R.string.request_error_forbidden)
                    )
                } else {
                    ResultStatus.failure(
                        App.instance.getString(R.string.request_error_code, response.code())
                    )
                }
            }

        } else {
            ResultStatus.failure(
                App.instance.getString(R.string.request_error_code, response.code())
            )
        }
    }

    fun <TypeOfDto : NetworkBaseResponse> parseStatus(response: Response<TypeOfDto>): ResultStatus {
        if (response.body() != null) {
            response.body()!!.let {
                return if (it.success) {
                    ResultStatus.success()
                } else {
                    if (!it.error.isNullOrEmpty()) {
                        ResultStatus.failure(it.error.orEmpty())
                    } else {
                        ResultStatus.failure(
                            App.instance.getString(R.string.request_error_code, response.code())
                        )
                    }
                }
            }
        } else {
            //something get wrong
            return parseError(response)
        }
    }

    fun <ResponseType : NetworkBaseResponse, EntityType : Any?> enqueueCallResultWithStatus(
        call: Call<ResponseType>,
        mapper: BaseRepositoryMapper<ResponseType, EntityType>,
        continuation: Continuation<ResultWithStatus<EntityType>>,
        throwErrorIfDataIsNull: Boolean = false,
        errorMsgIfDataIsNull: String? = null
    ) {
        call.enqueue(object : Callback<ResponseType> {
            override fun onResponse(
                call: Call<ResponseType>,
                response: Response<ResponseType>
            ) {
                val result = parseResult(response, mapper)
                if (throwErrorIfDataIsNull) {
                    if (result.data == null) {
                        continuation.resume(
                            ResultWithStatus(
                                null,
                                ResultStatus.failure(
                                    errorMsgIfDataIsNull
                                        ?: App.instance.getString(R.string.request_error_data_is_null)
                                )
                            )
                        )
                    } else {
                        continuation.resume(result)
                    }
                } else {
                    continuation.resume(result)
                }
            }

            override fun onFailure(
                call: Call<ResponseType>,
                t: Throwable
            ) {
                continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
            }
        })
    }

    fun <ResponseType : NetworkBaseResponse> enqueueCallOnlyStatus(
        call: Call<ResponseType>,
        continuation: Continuation<ResultStatus>
    ) {
        call.enqueue(object : Callback<ResponseType> {
            override fun onResponse(
                call: Call<ResponseType>,
                response: Response<ResponseType>
            ) {
                val result = parseStatus(response)
                continuation.resume(result)
            }

            override fun onFailure(
                call: Call<ResponseType>,
                t: Throwable
            ) {
                continuation.resume(ResultStatus.failure(t))
            }
        })
    }

    suspend fun <ResponseType : NetworkBaseResponse, EntityType : Any?>
            enqueueCallResultWithStatusSuspended(
        call: Call<ResponseType>,
        mapper: BaseRepositoryMapper<ResponseType, EntityType>,
        throwErrorIfDataIsNull: Boolean = false,
        errorMsgIfDataIsNull: String? = null
    ): ResultWithStatus<EntityType> {
        return suspendCoroutine { continuation ->
            enqueueCallResultWithStatus(
                call,
                mapper,
                continuation,
                throwErrorIfDataIsNull,
                errorMsgIfDataIsNull
            )
        }
    }

    suspend fun <ResponseType : NetworkBaseResponse> enqueueCallOnlyStatusSuspended(
        call: Call<ResponseType>
    ): ResultStatus {
        return suspendCoroutine { continuation ->
            enqueueCallOnlyStatus(call, continuation)
        }
    }
}