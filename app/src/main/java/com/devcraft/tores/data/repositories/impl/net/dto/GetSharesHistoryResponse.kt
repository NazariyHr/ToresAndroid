package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetSharesHistoryResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val transactions: MutableList<Transaction>
    )

    class Transaction(
        val id: String,
        @SerializedName("created_at") val createdAt: String,
        val type: String,
        val login: String?,
        val amount: String,
        val sharesAmount: String,
        val status: String
    )
}
