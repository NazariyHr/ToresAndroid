package com.devcraft.tores.data.repositories.impl.net.retrofitApis

import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.dto.BecomePartnerNetRequest
import com.devcraft.tores.data.repositories.impl.net.dto.GetPartnersAndUserRequestsResponse
import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PartnersApi {
    @GET(ApiConstants.API_ENDPOINT_GET_PARTNERS_AND_USER_REQUESTS)
    fun getPartnersAndUserRequests(): Call<GetPartnersAndUserRequestsResponse>

    @POST(ApiConstants.API_ENDPOINT_BECOME_PARTNER)
    fun becomePartner(@Body request: BecomePartnerNetRequest): Call<NetworkBaseResponse>
}
