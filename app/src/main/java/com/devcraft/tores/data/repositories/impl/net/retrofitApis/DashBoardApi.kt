package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.GetDashboardResponse
import retrofit2.Call
import retrofit2.http.GET

interface DashBoardApi {
    @GET(ApiConstants.API_ENDPOINT_DASHBOARD)
    fun getDashboard(): Call<GetDashboardResponse>
}