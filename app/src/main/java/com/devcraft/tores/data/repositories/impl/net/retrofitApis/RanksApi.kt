package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.GetRankInfoResponse
import com.devcraft.tores.data.repositories.impl.net.dto.GetUserRankSystemInfoResponse
import retrofit2.Call
import retrofit2.http.GET

interface RanksApi {
    @GET(ApiConstants.API_ENDPOINT_GET_RANK_INFO)
    fun getRankInfo(): Call<GetRankInfoResponse>

    @GET(ApiConstants.API_ENDPOINT_GET_USER_RANK_SYSTEM_INFO)
    fun getUserRankSystemInfo(): Call<GetUserRankSystemInfoResponse>
}
