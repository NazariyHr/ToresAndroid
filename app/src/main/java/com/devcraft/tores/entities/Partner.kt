package com.devcraft.tores.entities

data class Partner(
    val companyName: String,
    val percent: String?,
    val url: String,
    val status: PartnerStatus
)
