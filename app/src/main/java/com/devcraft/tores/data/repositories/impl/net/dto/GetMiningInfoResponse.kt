package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse

class GetMiningInfoResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val myDeposit: Double,
        val rankBalance: Double,
        val myBalance: String,
        val myRankBalance: String,
        val totalTopUps: Double,
        val availableToMining: Double,
    )
}
