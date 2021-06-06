package com.devcraft.tores.entities

data class AffiliateTreeUser(
    val id: Int,
    val login: String,
    val line: Int,
    val registeredAtDate: String,
    val registeredAtTime: String,
    val percent: Int,
    val colorClass: String,
    val firstLevelExists: Boolean,
    val deposit: Double,
    val profit: Double
)
