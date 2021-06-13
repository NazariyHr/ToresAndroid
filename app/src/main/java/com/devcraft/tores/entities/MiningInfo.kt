package com.devcraft.tores.entities

import java.math.BigDecimal

data class MiningInfo(
    val myDeposit: Double,
    val rankBalance: Double,
    val myBalance: BigDecimal,
    val myRankBalance: BigDecimal,
    val totalTopUps: Double,
    val availableToMining: Double,
)