package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetFinanceAllInfoResponse(
    val history: History
) : NetworkBaseResponse() {
    class History(
        val rankProfits: List<RankProfit>
    ) {
        class RankProfit(
            val id: Long,
            @SerializedName("created_at") val createdAt: String,
            val rank: String,
            val amount: Double
        )
    }
}
