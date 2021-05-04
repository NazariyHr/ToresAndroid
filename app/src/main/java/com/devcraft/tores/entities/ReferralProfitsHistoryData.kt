package com.devcraft.tores.entities

class ReferralProfitsHistoryData(
    val referralProfits: List<ReferralProfit>,
    val total: Double
) {
    class ReferralProfit(
        val id: Long,
        val createdAt: String,
        val login: String,
        val level: String,
        val partnerProfit: String,
        val percent: String,
        val amount: Double
    )
}
