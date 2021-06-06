package com.devcraft.tores.entities

data class User(
    val balance: Double,
    val rankBalance: Double,
    val totalProfit: Double,
    val partnerProfit: Double,
    val rankProfit: Double,
    val paymentConfirmationWay: PaymentConfirmationWay,
    val rankLevel: RankLevel
)