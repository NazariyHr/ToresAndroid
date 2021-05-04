package com.devcraft.tores.data.repositories.impl.net.dto

import com.google.gson.annotations.SerializedName

class CancelWithdrawalRequest(
    @SerializedName("id") var transactionID: Long
)
