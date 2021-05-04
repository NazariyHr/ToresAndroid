package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.*
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.*
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.FinancesApi
import com.devcraft.tores.entities.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FinancesRepositoryImpl(
    private val financesApi: FinancesApi,
    private val tokenRepository: TokenRepository,
    private val getTopupsAndWithdrawalsMapper: GetTopupsAndWithdrawalsMapper,
    private val getMiningHistoryMapper: GetMiningHistoryMapper,
    private val getTransfersHistoryMapper: GetTransfersHistoryMapper,
    private val getReferralProfitsHistoryMapper: GetReferralProfitsHistoryMapper,
    private val getFinanceAllInfoToRankProfitsHistoryMapper: GetFinanceAllInfoToRankProfitsHistoryMapper
) : BaseNetRepository(), FinancesRepository {
    override suspend fun getTopupsAndWithdrawalsData(): ResultWithStatus<TopupsAndWithdrawalsData> {
        return suspendCoroutine { continuation ->
            financesApi
                .getTopupsAndWithdrawals(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetTopupsAndWithdrawalsResponse> {
                    override fun onResponse(
                        call: Call<GetTopupsAndWithdrawalsResponse>,
                        response: Response<GetTopupsAndWithdrawalsResponse>
                    ) {
                        val result = parseResult(response, getTopupsAndWithdrawalsMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(
                        call: Call<GetTopupsAndWithdrawalsResponse>,
                        t: Throwable
                    ) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }

    override suspend fun getMiningHistory(): ResultWithStatus<MiningHistoryData> {
        return suspendCoroutine { continuation ->
            financesApi
                .getMiningHistory(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetMiningHistoryResponse> {
                    override fun onResponse(
                        call: Call<GetMiningHistoryResponse>,
                        response: Response<GetMiningHistoryResponse>
                    ) {
                        val result = parseResult(response, getMiningHistoryMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(
                        call: Call<GetMiningHistoryResponse>,
                        t: Throwable
                    ) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }

    override suspend fun cancelWithdraw(transactionId: Long): ResultStatus {
        return suspendCoroutine { continuation ->
            financesApi
                .cancelWithdrawFromDeposit(
                    tokenRepository.getToken().bearerToken,
                    CancelWithdrawalRequest(transactionId)
                )
                .enqueue(object : Callback<NetworkBaseResponse> {
                    override fun onResponse(
                        call: Call<NetworkBaseResponse>,
                        response: Response<NetworkBaseResponse>
                    ) {
                        val result = parseStatus(response)
                        continuation.resume(result)
                    }

                    override fun onFailure(
                        call: Call<NetworkBaseResponse>,
                        t: Throwable
                    ) {
                        continuation.resume(ResultStatus.failure(t))
                    }
                })
        }
    }

    override suspend fun getTransfersHistory(): ResultWithStatus<TransfersHistoryData> {
        return suspendCoroutine { continuation ->
            financesApi
                .getTransfersHistory(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetTransfersHistoryResponse> {
                    override fun onResponse(
                        call: Call<GetTransfersHistoryResponse>,
                        response: Response<GetTransfersHistoryResponse>
                    ) {
                        val result = parseResult(response, getTransfersHistoryMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(
                        call: Call<GetTransfersHistoryResponse>,
                        t: Throwable
                    ) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }

    override suspend fun getReferralProfitsHistory(): ResultWithStatus<ReferralProfitsHistoryData> {
        return suspendCoroutine { continuation ->
            financesApi
                .getReferralProfitsHistory(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetReferralProfitsHistoryResponse> {
                    override fun onResponse(
                        call: Call<GetReferralProfitsHistoryResponse>,
                        response: Response<GetReferralProfitsHistoryResponse>
                    ) {
                        val result = parseResult(response, getReferralProfitsHistoryMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(
                        call: Call<GetReferralProfitsHistoryResponse>,
                        t: Throwable
                    ) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }

    override suspend fun getRankProfitsHistory(): ResultWithStatus<RankProfitsHistoryData> {
        return suspendCoroutine { continuation ->
            financesApi
                .getGetFinanceAllInfo(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetFinanceAllInfoResponse> {
                    override fun onResponse(
                        call: Call<GetFinanceAllInfoResponse>,
                        response: Response<GetFinanceAllInfoResponse>
                    ) {
                        val result =
                            parseResult(response, getFinanceAllInfoToRankProfitsHistoryMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(
                        call: Call<GetFinanceAllInfoResponse>,
                        t: Throwable
                    ) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }
}
