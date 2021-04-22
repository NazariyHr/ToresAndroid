package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.GetUserNetResponse
import com.devcraft.tores.data.repositories.impl.net.dto.LogInRequest
import com.devcraft.tores.data.repositories.impl.net.dto.LogInResponse
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST(ApiConstants.API_ENDPOINT_LOGIN)
    @Headers(
        "Content-Type: application/json"
    )
    fun login(@Body request: LogInRequest): Call<LogInResponse>

    @GET(ApiConstants.API_ENDPOINT_USER)
    @Headers(
        "Content-Type: application/json"
    )
    fun getUser(@Header("Authorization") token: String): Call<GetUserNetResponse>
}