package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetPartnersAndUserRequestsResponse(
    val requests: MutableList<Request>,
    val partners: MutableList<Partner>
) : NetworkBaseResponse() {
    class Request(
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("company_name") val companyName: String,
        val url: String,
        val status: String
    )

    class Partner(
        val id: Int,
        @SerializedName("user_id") val userId: Int,
        @SerializedName("company_name") val companyName: String,
        val type: String?,
        val url: String,
        val contacts: String,
        val percent: String?,
        val additional: String?,
        val status: String
    )
}