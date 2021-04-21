package com.devcraft.tores.data.repositories.impl.net.retrofit_apis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.LogInRequest
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST(ApiConstants.API_ENDPOINT_LOGIN)
    @Headers(
        "Content-Type: application/json"
    )
    fun login(@Body request: LogInRequest): Call<Any>

    @GET(ApiConstants.API_ENDPOINT_USER)
    @Headers(
        "Content-Type: application/json"
    )
    fun getUser(@Header("X-Authorization") token: String): Call<Any>
}