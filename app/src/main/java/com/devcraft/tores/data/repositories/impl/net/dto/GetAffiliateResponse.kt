package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse

class GetAffiliateResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val sponsor: Sponsor,
        val totalPartners: Int,
        val totalDepositsAmount: Int,
        val activePartners: Int,
        val inactivePartners: Int,
        val refLinkVisits: Int,
        val overallProfit: Double,
        val overallProfit24h: Double
    ) {
        class Sponsor(
            val login: String,
            val email: String,
            val phone: String
        )
    }
}
