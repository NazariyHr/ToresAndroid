package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetMiningHistoryResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val transactions: List<Transaction>,
        val myDeposit: Double
    ) {

        class Transaction(
            val id: Long,
            @SerializedName("created_at") val createdAt: String,
            val type: String,
            val amount: Double,
            val timeLeft: String,
            val balance: String,
            val status: String
        )
    }
}
