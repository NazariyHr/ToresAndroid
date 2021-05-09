package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetTopupsAndWithdrawalsResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val transactions: List<Transaction>,
        val myDeposit: Double,
        val rankBalance: Double,
        val myBalance: Double,
        val totalTopUps: Double
    ) {

        class Transaction(
            val id: Long,
            val amount: Double,
            @SerializedName("amount_in_currency") val amountInCurrency: String,
            val currency: String,
            val status: String,
            val wallet: String,
            val qr: String?,
            val remaining: Long,
            val type: String,
            @SerializedName("created_at") val createdAt: String,
            val confirmationsTotal: Int,
            val confirmationsNeeded: Int,
            @SerializedName("user_id") val userId: Long?,
            val payeerSign: String?
        )
    }
}
