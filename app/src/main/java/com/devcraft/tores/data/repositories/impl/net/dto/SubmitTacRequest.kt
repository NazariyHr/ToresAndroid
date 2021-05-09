package com.devcraft.tores.data.repositories.impl.net.dto

import com.google.gson.annotations.SerializedName

class SubmitTacRequest(
    @SerializedName("id") var transactionID: Long,
    @SerializedName("type") var transactionType: String,
    var tac: String
)
