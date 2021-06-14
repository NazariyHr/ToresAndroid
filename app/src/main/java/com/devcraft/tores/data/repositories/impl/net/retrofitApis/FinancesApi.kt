package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.*
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import retrofit2.Call
import retrofit2.http.*

interface FinancesApi {

    @GET(ApiConstants.API_ENDPOINT_FINANCE)
    @Headers(
        "Content-Type: application/json"
    )
    fun getGetFinanceAllInfo(@Header("Authorization") token: String): Call<GetFinanceAllInfoResponse>

    @GET(ApiConstants.API_ENDPOINT_TOPUPS_AND_WITHDRAWALS)
    @Headers(
        "Content-Type: application/json"
    )
    fun getTopupsAndWithdrawals(@Header("Authorization") token: String): Call<GetTopupsAndWithdrawalsResponse>

    @GET(ApiConstants.API_ENDPOINT_MINING_HISTORY)
    @Headers(
        "Content-Type: application/json"
    )
    fun getMiningHistory(@Header("Authorization") token: String): Call<GetMiningHistoryResponse>

    @POST(ApiConstants.API_ENDPOINT_CANCEL_WITHDRAW_FROM_DEPOSIT)
    @Headers(
        "Content-Type: application/json"
    )
    fun cancelWithdrawFromDeposit(
        @Header("Authorization") token: String,
        @Body request: CancelWithdrawalRequest
    ): Call<NetworkBaseResponse>

    @GET(ApiConstants.API_ENDPOINT_TRANSFERS_HISTORY)
    @Headers(
        "Content-Type: application/json"
    )
    fun getTransfersHistory(@Header("Authorization") token: String): Call<GetTransfersHistoryResponse>

    @GET(ApiConstants.API_ENDPOINT_REFERRAL_PROFITS_HISTORY)
    @Headers(
        "Content-Type: application/json"
    )
    fun getReferralProfitsHistory(@Header("Authorization") token: String): Call<GetReferralProfitsHistoryResponse>

    @POST(ApiConstants.API_ENDPOINT_CANCEL_TOPUP)
    @Headers(
        "Content-Type: application/json"
    )
    fun cancelTopup(
        @Header("Authorization") token: String,
        @Body request: CancelTopupRequest
    ): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_SUBMIT_TAC)
    @Headers(
        "Content-Type: application/json"
    )
    fun submitTac(
        @Header("Authorization") token: String,
        @Body request: SubmitTacRequest
    ): Call<NetworkBaseResponse>

    @GET(ApiConstants.API_ENDPOINT_GET_CURRENCY_RATES)
    @Headers(
        "Content-Type: application/json"
    )
    fun getCurrencyRates(
        @Header("Authorization") token: String
    ): Call<GetCurrencyRatesResponse>

    @POST(ApiConstants.API_ENDPOINT_TOPUP)
    @Headers(
        "Content-Type: application/json"
    )
    fun topup(
        @Header("Authorization") token: String,
        @Body request: TopupRequest
    ): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_WITHDRAW)
    @Headers(
        "Content-Type: application/json"
    )
    fun withdraw(
        @Header("Authorization") token: String,
        @Body request: WithdrawRequest
    ): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_TRANSFER_TO_USER)
    @Headers(
        "Content-Type: application/json"
    )
    fun transferToUser(
        @Header("Authorization") token: String,
        @Body request: TransferToUserRequest
    ): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_TRANSFER_TO_EXCHANGE)
    @Headers(
        "Content-Type: application/json"
    )
    fun transferToExchange(
        @Header("Authorization") token: String,
        @Body request: TransferToExchangeRequest
    ): Call<NetworkBaseResponse>
}
