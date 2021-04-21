package com.devcraft.tores.data.repositories.impl.net.dto.base

import com.google.gson.annotations.SerializedName

open class NetworkBaseResponse {
    @SerializedName("success")
    var success: Boolean = true

    @SerializedName("error")
    var error: String? = null
}