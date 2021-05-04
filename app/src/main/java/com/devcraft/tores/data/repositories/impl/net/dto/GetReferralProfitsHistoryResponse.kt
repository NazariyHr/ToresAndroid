package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetReferralProfitsHistoryResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val referralProfits: List<ReferralProfit>,
        val referralProfitsTotal: Double
    ) {
        class ReferralProfit(
            val id: Long,
            @SerializedName("created_at") val createdAt: String,
            val login: String,
            val level: String,
            val partnerProfit: String,
            val percent: String,
            val amount: Double
        )
    }
}