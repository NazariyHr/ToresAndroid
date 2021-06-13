package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.entities.MiningInfo

interface MiningRepository {
    suspend fun getMiningInfo(): ResultWithStatus<MiningInfo>

    suspend fun addToMining(amount: Double, balanceType: BalanceType): ResultStatus

    suspend fun withdrawFromMining(amount: Double): ResultStatus
}