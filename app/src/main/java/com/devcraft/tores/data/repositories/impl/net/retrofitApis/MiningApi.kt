package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.AddToMiningRequest
import com.devcraft.tores.data.repositories.impl.net.dto.GetMiningInfoResponse
import com.devcraft.tores.data.repositories.impl.net.dto.WithdrawFromMiningRequest
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MiningApi {
    @GET(ApiConstants.API_ENDPOINT_GET_MINING_INFO)
    fun getMiningInfo(): Call<GetMiningInfoResponse>

    @POST(ApiConstants.API_ENDPOINT_ADD_TO_MINING)
    fun addToMining(@Body request: AddToMiningRequest): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_WITHDRAW_FROM_MINING)
    fun withdrawFromMining(@Body request: WithdrawFromMiningRequest):
            Call<NetworkBaseResponse>
}