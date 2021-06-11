package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.*
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
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
    fun getUser(@Header("Authorization") token: String): Call<GetUserResponse>

    @POST(ApiConstants.API_ENDPOINT_CHANGE_PASSWORD)
    @Headers(
        "Content-Type: application/json"
    )
    fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): Call<ChangePasswordResponse>

    @POST(ApiConstants.API_ENDPOINT_SET_FINANCE_PASSWORD)
    @Headers(
        "Content-Type: application/json"
    )
    fun setFinancePassword(
        @Header("Authorization") token: String,
        @Body request: SetFinancePasswordRequest
    ): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_REMOVE_FINANCE_PASSWORD)
    @Headers(
        "Content-Type: application/json"
    )
    fun removeFinancePassword(
        @Header("Authorization") token: String
    ): Call<NetworkBaseResponse>
}