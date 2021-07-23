package com.devcraft.tores.entities

data class User(
    val email: String,
    val login: String,
    val balance: Double,
    val rankBalance: Double,
    val totalProfit: Double,
    val partnerProfit: Double,
    val rankProfit: Double,
    val paymentConfirmationWay: PaymentConfirmationWay,
    val rankLevel: Int?,
    val rankLevelGotAt: String?,
    val currentRank: Rank,
    val nextRank: Rank?,
    val referralCode: String,
    val registeredAt: String,
    val lastEntrance: String,
    val ip: String,
    val financePasswordSet: Boolean
)