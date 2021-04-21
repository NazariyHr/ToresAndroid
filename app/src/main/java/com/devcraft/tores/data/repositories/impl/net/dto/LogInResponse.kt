package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class LogInResponse : NetworkBaseResponse() {
    @SerializedName("token") //maybe
    var token: String = ""
}