package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetTransfersHistoryResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val transactions: List<Transaction>
    ) {
        class Transaction(
            val id: Long,
            val amount: Double,
            val login: String,
            val wallet: String,
            val balance: String,
            val type: String,
            @SerializedName("created_at") val createdAt: String,
            val status: String
        )
    }
}
