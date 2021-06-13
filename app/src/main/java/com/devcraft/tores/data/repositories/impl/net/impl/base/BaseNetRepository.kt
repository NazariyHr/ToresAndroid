package com.devcraft.tores.data.repositories.impl.net.impl.base

import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.AddToMiningRequest
import com.devcraft.tores.data.repositories.impl.net.dto.GetMiningInfoResponse
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import org.json.JSONException
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
                    ResultStatus.failure(it.error.orEmpty())
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
                    ResultStatus.failure("Request error, response code: ${response.code()}")
                }
            } catch (e: Exception) {
                if (response.code() == 403) {
                    ResultStatus.failure("Request error, Forbidden")
                } else {
                    ResultStatus.failure("Request error, response code: ${response.code()}")
                }
            }

        } else {
            ResultStatus.failure("Request error, response code: ${response.code()}")
        }
    }

    fun <TypeOfDto : NetworkBaseResponse> parseStatus(response: Response<TypeOfDto>): ResultStatus {
        if (response.body() != null) {
            response.body()!!.let {
                return if (it.success) {
                    ResultStatus.success()
                } else {
                    ResultStatus.failure(it.error.orEmpty())
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
        continuation: Continuation<ResultWithStatus<EntityType>>
    ) {
        call.enqueue(object : Callback<ResponseType> {
            override fun onResponse(
                call: Call<ResponseType>,
                response: Response<ResponseType>
            ) {
                val result = parseResult(response, mapper)
                continuation.resume(result)
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
        mapper: BaseRepositoryMapper<ResponseType, EntityType>
    ): ResultWithStatus<EntityType> {
        return suspendCoroutine { continuation ->
            enqueueCallResultWithStatus(call, mapper, continuation)
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