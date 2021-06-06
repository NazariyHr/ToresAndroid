package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.GetRankInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface RanksApi {
    @GET(ApiConstants.API_ENDPOINT_GET_RANK_INFO)
    @Headers(
        "Content-Type: application/json"
    )
    fun getRankInfo(@Header("Authorization") token: String): Call<GetRankInfoResponse>
}
