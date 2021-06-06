package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse

class GetRankInfoResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val currentRank: String,
        val currentRankRU: String,
        val nextRank: String,
        val nextRankRU: String,
        val myLevel: Int,
        val myDeposit: Double
    )
}
