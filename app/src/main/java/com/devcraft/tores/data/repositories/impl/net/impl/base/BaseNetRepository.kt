package com.devcraft.tores.data.repositories.impl.net.impl.base

import com.devcraft.tores.data.repositories.contract.common_results.ResultStatus
import com.devcraft.tores.data.repositories.contract.common_results.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import org.json.JSONObject
import retrofit2.Response

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
                    resultData = mapper.mapDtoToEntity(it)
                    ResultStatus.success()
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
            val jObjError = JSONObject(response.errorBody()!!.string())
            val error = jObjError.getString("error")
            if (!error.isNullOrEmpty()) {
                ResultStatus.failure(error)
            } else {
                ResultStatus.failure("Request error, response code: ${response.code()}")
            }
        } else {
            ResultStatus.failure("Request error, response code: ${response.code()}")
        }
    }

    fun <TypeOfDto : NetworkBaseResponse> parseEmptyStatusResponse(response: Response<TypeOfDto>): ResultStatus {
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
}