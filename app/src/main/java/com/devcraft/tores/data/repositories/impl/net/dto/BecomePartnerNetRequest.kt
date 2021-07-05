package com.devcraft.tores.data.repositories.impl.net.dto

import com.google.gson.annotations.SerializedName

class BecomePartnerNetRequest(
    @SerializedName("company_name") val companyName: String,
    val url: String,
    val contacts: String,
    val percent: String?,
    val type: String?,
    val additional: String?,
)