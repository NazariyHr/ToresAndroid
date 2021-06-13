package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.AddToMiningRequest
import com.devcraft.tores.data.repositories.impl.net.dto.GetMiningInfoResponse
import com.devcraft.tores.data.repositories.impl.net.dto.WithdrawFromMiningRequest
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import retrofit2.Call
import retrofit2.http.*

interface MiningApi {
    @GET(ApiConstants.API_ENDPOINT_GET_MINING_INFO)
    @Headers(
        "Content-Type: application/json"
    )
    fun getMiningInfo(@Header("Authorization") token: String): Call<GetMiningInfoResponse>

    @POST(ApiConstants.API_ENDPOINT_ADD_TO_MINING)
    @Headers(
        "Content-Type: application/json"
    )
    fun addToMining(
        @Header("Authorization") token: String,
        @Body request: AddToMiningRequest
    ): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_WITHDRAW_FROM_MINING)
    @Headers(
        "Content-Type: application/json"
    )
    fun withdrawFromMining(
        @Header("Authorization") token: String,
        @Body request: WithdrawFromMiningRequest
    ): Call<NetworkBaseResponse>
}