package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.GetRankInfoResponse
import retrofit2.Call
import retrofit2.http.GET

interface RanksApi {
    @GET(ApiConstants.API_ENDPOINT_GET_RANK_INFO)
    fun getRankInfo(): Call<GetRankInfoResponse>
}
