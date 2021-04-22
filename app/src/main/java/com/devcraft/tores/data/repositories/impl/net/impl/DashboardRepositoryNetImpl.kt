package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.DashboardRepository
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.GetDashboardNetResponse
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetDashboardNetMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.DashBoardApi
import com.devcraft.tores.entities.ProfitsAndRegisters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DashboardRepositoryNetImpl(
    private val dashboardApi: DashBoardApi,
    private val tokenRepository: TokenRepository,
    private val getDashboardNetMapper: GetDashboardNetMapper
) : BaseNetRepository(), DashboardRepository {

    override suspend fun getDashboardInfo(): ResultWithStatus<ProfitsAndRegisters> {
        return suspendCoroutine { continuation ->
            dashboardApi
                .getDashboard(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetDashboardNetResponse> {
                    override fun onResponse(
                        call: Call<GetDashboardNetResponse>,
                        response: Response<GetDashboardNetResponse>
                    ) {
                        val result = parseResult(response, getDashboardNetMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(call: Call<GetDashboardNetResponse>, t: Throwable) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }
}
