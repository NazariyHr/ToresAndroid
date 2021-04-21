package com.devcraft.tores.data.repositories.impl.net.dto

import com.google.gson.annotations.SerializedName

class LogInRequest(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("token") var token: String //from reCaptcha
)
