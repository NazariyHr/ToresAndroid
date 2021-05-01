package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.GetDashboardResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface DashBoardApi {
    @GET(ApiConstants.API_ENDPOINT_DASHBOARD)
    @Headers(
        "Content-Type: application/json"
    )
    fun getDashboard(@Header("Authorization") token: String): Call<GetDashboardResponse>
}