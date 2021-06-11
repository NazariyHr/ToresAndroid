package com.devcraft.tores.data.repositories.impl.net.dto

import com.google.gson.annotations.SerializedName

class SetFinancePasswordRequest(
    @SerializedName("newPassword") val pass: String,
    @SerializedName("passwordConfirm") val passConfirm: String,
    @SerializedName("way") val way: String = "pp"
)
