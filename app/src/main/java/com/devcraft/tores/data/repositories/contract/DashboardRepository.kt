package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.entities.ProfitsAndRegisters

interface DashboardRepository {
    suspend fun getDashboardInfo(): ResultWithStatus<ProfitsAndRegisters>
}