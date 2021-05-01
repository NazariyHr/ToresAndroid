package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.GetTopupsAndWithdrawalsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface FinancesApi {
    @GET(ApiConstants.API_ENDPOINT_TOPUPS_AND_WITHDRAWALS)
    @Headers(
        "Content-Type: application/json"
    )
    fun getTopupsAndWithdrawals(@Header("Authorization") token: String): Call<GetTopupsAndWithdrawalsResponse>
}
