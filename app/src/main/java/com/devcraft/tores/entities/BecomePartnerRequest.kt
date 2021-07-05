package com.devcraft.tores.entities

data class BecomePartnerRequest(
    val createdAt: String,
    val companyName: String,
    val url: String,
    val status: PartnerStatus
)
