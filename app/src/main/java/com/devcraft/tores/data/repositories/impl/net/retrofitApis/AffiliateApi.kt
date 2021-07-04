package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.GetAffiliateResponse
import com.devcraft.tores.data.repositories.impl.net.dto.GetAffiliateTreeFirstLineResponse
import com.devcraft.tores.data.repositories.impl.net.dto.GetAffiliateTreeSpecificLineResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AffiliateApi {
    @GET(ApiConstants.API_ENDPOINT_GET_AFFILIATE)
    fun getAffiliate(): Call<GetAffiliateResponse>

    @GET(ApiConstants.API_ENDPOINT_GET_AFFILIATE_TREE_FIRST_LINE)
    fun getAffiliateTreeFirstLine(): Call<GetAffiliateTreeFirstLineResponse>

    @GET(ApiConstants.API_ENDPOINT_GET_AFFILIATE_TREE_SPECIFIC_LINE)
    fun getAffiliateTreeSpecificLine(
        @Path("userId") userId: Int,
        @Path("line") line: Int
    ): Call<GetAffiliateTreeSpecificLineResponse>
}
