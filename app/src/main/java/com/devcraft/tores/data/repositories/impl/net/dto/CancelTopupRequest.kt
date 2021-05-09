package com.devcraft.tores.data.repositories.impl.net.dto

import com.google.gson.annotations.SerializedName

class CancelTopupRequest(
    @SerializedName("id") var transactionID: Long
)
