package com.devcraft.tores.entities

class AffiliateInfo(
    val sponsorLogin: String,
    val sponsorEmail: String,
    val sponsorPhone: String,
    val totalPartners: Int,
    val totalDepositsAmount: Int,
    val activePartners: Int,
    val inactivePartners: Int,
    val refLinkVisits: Int,
    val overallProfit: Double,
    val overallProfit24h: Double
)
