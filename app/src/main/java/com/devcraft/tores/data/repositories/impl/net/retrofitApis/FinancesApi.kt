package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.*
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FinancesApi {

    @GET(ApiConstants.API_ENDPOINT_FINANCE)
    fun getGetFinanceAllInfo(): Call<GetFinanceAllInfoResponse>

    @GET(ApiConstants.API_ENDPOINT_TOPUPS_AND_WITHDRAWALS)
    fun getTopupsAndWithdrawals(): Call<GetTopupsAndWithdrawalsResponse>

    @GET(ApiConstants.API_ENDPOINT_MINING_HISTORY)
    fun getMiningHistory(): Call<GetMiningHistoryResponse>

    @POST(ApiConstants.API_ENDPOINT_CANCEL_WITHDRAW_FROM_DEPOSIT)
    fun cancelWithdrawFromDeposit(@Body request: CancelWithdrawalRequest):
            Call<NetworkBaseResponse>

    @GET(ApiConstants.API_ENDPOINT_TRANSFERS_HISTORY)
    fun getTransfersHistory(): Call<GetTransfersHistoryResponse>

    @GET(ApiConstants.API_ENDPOINT_REFERRAL_PROFITS_HISTORY)
    fun getReferralProfitsHistory(): Call<GetReferralProfitsHistoryResponse>

    @POST(ApiConstants.API_ENDPOINT_CANCEL_TOPUP)
    fun cancelTopup(@Body request: CancelTopupRequest): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_SUBMIT_TAC)
    fun submitTac(@Body request: SubmitTacRequest): Call<NetworkBaseResponse>

    @GET(ApiConstants.API_ENDPOINT_GET_CURRENCY_RATES)
    fun getCurrencyRates(): Call<GetCurrencyRatesResponse>

    @POST(ApiConstants.API_ENDPOINT_TOPUP)
    fun topup(@Body request: TopupRequest): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_WITHDRAW)
    fun withdraw(@Body request: WithdrawRequest): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_TRANSFER_TO_USER)
    fun transferToUser(@Body request: TransferToUserRequest):
            Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_TRANSFER_TO_EXCHANGE)
    fun transferToExchange(@Body request: TransferToExchangeRequest):
            Call<NetworkBaseResponse>
}
