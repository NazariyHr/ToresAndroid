package com.devcraft.tores.data.repositories.impl.net.dto

import com.google.gson.annotations.SerializedName

class ChangePasswordRequest(
    @SerializedName("oldPassword") val oldPass: String,
    @SerializedName("newPassword") val newPass: String,
    @SerializedName("passwordConfirm") val newPassConfirm: String
)
