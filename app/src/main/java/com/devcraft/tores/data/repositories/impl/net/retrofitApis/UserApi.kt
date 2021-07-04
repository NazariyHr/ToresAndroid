package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.*
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST(ApiConstants.API_ENDPOINT_LOGIN)
    fun login(@Body request: LogInRequest): Call<LogInResponse>

    @GET(ApiConstants.API_ENDPOINT_USER)
    fun getUser(): Call<GetUserResponse>

    @POST(ApiConstants.API_ENDPOINT_CHANGE_PASSWORD)
    fun changePassword(@Body request: ChangePasswordRequest):
            Call<ChangePasswordResponse>

    @POST(ApiConstants.API_ENDPOINT_SET_FINANCE_PASSWORD)
    fun setFinancePassword(@Body request: SetFinancePasswordRequest):
            Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_REMOVE_FINANCE_PASSWORD)
    fun removeFinancePassword(): Call<NetworkBaseResponse>
}