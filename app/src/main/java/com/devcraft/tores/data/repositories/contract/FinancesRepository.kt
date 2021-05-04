package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.entities.*

interface FinancesRepository {
    suspend fun getTopupsAndWithdrawalsData(): ResultWithStatus<TopupsAndWithdrawalsData>

    suspend fun getMiningHistory(): ResultWithStatus<MiningHistoryData>

    suspend fun cancelWithdraw(transactionId: Long): ResultStatus

    suspend fun getTransfersHistory(): ResultWithStatus<TransfersHistoryData>

    suspend fun getReferralProfitsHistory(): ResultWithStatus<ReferralProfitsHistoryData>

    suspend fun getRankProfitsHistory(): ResultWithStatus<RankProfitsHistoryData>
}