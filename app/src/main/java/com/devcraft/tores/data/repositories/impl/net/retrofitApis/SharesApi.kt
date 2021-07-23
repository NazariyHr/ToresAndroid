package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.BuySharesRequest
import com.devcraft.tores.data.repositories.impl.net.dto.GetSharesHistoryResponse
import com.devcraft.tores.data.repositories.impl.net.dto.GetSharesTotalInfoResponse
import com.devcraft.tores.data.repositories.impl.net.dto.TransferSharesRequest
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SharesApi {
    @GET(ApiConstants.API_ENDPOINT_GET_SHARES_TOTAL_INFO)
    fun getSharesTotalInfo(): Call<GetSharesTotalInfoResponse>

    @GET(ApiConstants.API_ENDPOINT_GET_SHARES_HISTORY)
    fun getSharesHistory(): Call<GetSharesHistoryResponse>

    @POST(ApiConstants.API_ENDPOINT_BUY_SHARES)
    fun buyShares(@Body request: BuySharesRequest): Call<NetworkBaseResponse>

    @POST(ApiConstants.API_ENDPOINT_TRANSFER_SHARES)
    fun transferShares(@Body request: TransferSharesRequest): Call<NetworkBaseResponse>
}
