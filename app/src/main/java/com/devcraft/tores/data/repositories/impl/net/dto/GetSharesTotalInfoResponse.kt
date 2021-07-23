package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse

class GetSharesTotalInfoResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val totalShares: String,
        val sharesLocked: String,
        val sharesBought: String,
        val myBalance: String,
        val myRankBalance: String,
        val oneSharePrice: String,
        val totalProgressInPercent: String,
        val availableShares: String
    )
}
