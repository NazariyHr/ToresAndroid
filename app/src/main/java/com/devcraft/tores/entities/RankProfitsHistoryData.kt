package com.devcraft.tores.entities

class RankProfitsHistoryData(
    val rankProfits: List<RankProfit>
) {
    class RankProfit(
        val id: Long,
        val createdAt: String,
        val rank: Rank,
        val amount: Double
    )
}
