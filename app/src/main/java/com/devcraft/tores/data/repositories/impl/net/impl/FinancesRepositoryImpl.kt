package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.*
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.*
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.FinancesApi
import com.devcraft.tores.entities.*
import com.devcraft.tores.entities.Currency
import java.util.*

class FinancesRepositoryImpl(
    private val financesApi: FinancesApi,
    private val getTopupsAndWithdrawalsMapper: GetTopupsAndWithdrawalsMapper,
    private val getMiningHistoryMapper: GetMiningHistoryMapper,
    private val getTransfersHistoryMapper: GetTransfersHistoryMapper,
    private val getReferralProfitsHistoryMapper: GetReferralProfitsHistoryMapper,
    private val getFinanceAllInfoToRankProfitsHistoryMapper: GetFinanceAllInfoToRankProfitsHistoryMapper,
    private val getCurrencyRatesMapper: GetCurrencyRatesMapper
) : BaseNetRepository(), FinancesRepository {

    override suspend fun getTopupsAndWithdrawalsData(): ResultWithStatus<TopupsAndWithdrawalsData> {
        return enqueueCallResultWithStatusSuspended(
            financesApi.getTopupsAndWithdrawals(),
            getTopupsAndWithdrawalsMapper
        )
    }

    override suspend fun getMiningHistory(): ResultWithStatus<MiningHistoryData> {
        return enqueueCallResultWithStatusSuspended(
            financesApi.getMiningHistory(),
            getMiningHistoryMapper
        )
    }

    override suspend fun cancelWithdraw(transactionId: Long): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            financesApi.cancelWithdrawFromDeposit(
                CancelWithdrawalRequest(transactionId)
            )
        )
    }

    override suspend fun getTransfersHistory(): ResultWithStatus<TransfersHistoryData> {
        return enqueueCallResultWithStatusSuspended(
            financesApi.getTransfersHistory(),
            getTransfersHistoryMapper
        )
    }

    override suspend fun getReferralProfitsHistory(): ResultWithStatus<ReferralProfitsHistoryData> {
        return enqueueCallResultWithStatusSuspended(
            financesApi.getReferralProfitsHistory(),
            getReferralProfitsHistoryMapper
        )
    }

    override suspend fun getRankProfitsHistory(): ResultWithStatus<RankProfitsHistoryData> {
        return enqueueCallResultWithStatusSuspended(
            financesApi.getGetFinanceAllInfo(),
            getFinanceAllInfoToRankProfitsHistoryMapper
        )
    }

    override suspend fun cancelTopup(transactionId: Long): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            financesApi.cancelTopup(CancelTopupRequest(transactionId))
        )
    }

    override suspend fun submitTac(transactionId: Long, type: String, tac: String): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            financesApi.submitTac(
                SubmitTacRequest(transactionId, type, tac)
            )
        )
    }

    override suspend fun getCurrencyRates(): ResultWithStatus<CurrencyRatesInfo> {
        return enqueueCallResultWithStatusSuspended(
            financesApi.getCurrencyRates(),
            getCurrencyRatesMapper
        )
    }

    override suspend fun topup(amount: Double, currency: Currency): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            financesApi.topup(
                TopupRequest(amount, currency.getShortTitle())
            )
        )
    }

    override suspend fun withdraw(
        amount: Double,
        currency: Currency,
        wallet: String
    ): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            financesApi.withdraw(
                WithdrawRequest(amount, currency.getShortTitle(), wallet)
            )
        )
    }

    override suspend fun transferToUser(
        amount: Double,
        login: String,
        balanceType: BalanceType
    ): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            financesApi.transferToUser(
                TransferToUserRequest(amount, login, balanceType.name.toLowerCase(Locale.US))
            )
        )
    }

    override suspend fun transferToExchange(amount: Double, wallet: String): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            financesApi.transferToExchange(
                TransferToExchangeRequest(amount, wallet)
            )
        )
    }
}
