package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.DashboardRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetDashboardMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.DashBoardApi
import com.devcraft.tores.entities.ProfitsAndRegisters

class DashboardRepositoryImpl(
    private val dashboardApi: DashBoardApi,
    private val getDashboardMapper: GetDashboardMapper
) : BaseNetRepository(), DashboardRepository {

    override suspend fun getDashboardInfo(): ResultWithStatus<ProfitsAndRegisters> {
        return enqueueCallResultWithStatusSuspended(
            dashboardApi.getDashboard(),
            getDashboardMapper
        )
    }
}
