package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class LogInResponse(
    var token: Token
) : NetworkBaseResponse() {
    class Token(
        @SerializedName("plainTextToken") var plainTextToken: String
    )
}
