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

    suspend fun cancelTopup(transactionId: Long): ResultStatus

    suspend fun submitTac(transactionId: Long, type: String, tac: String): ResultStatus

    suspend fun getCurrencyRates(): ResultWithStatus<CurrencyRatesInfo>

    suspend fun topup(amount: Double, currency: Currency): ResultStatus

    suspend fun withdraw(amount: Double, currency: Currency, wallet: String): ResultStatus

    suspend fun transferToUser(
        amount: Double,
        login: String,
        balanceType: BalanceType
    ): ResultStatus

    suspend fun transferToExchange(amount: Double, wallet: String): ResultStatus
}