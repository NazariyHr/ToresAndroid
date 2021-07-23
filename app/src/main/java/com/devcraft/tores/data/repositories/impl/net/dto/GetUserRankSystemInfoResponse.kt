package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse

class GetUserRankSystemInfoResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val myDeposit: String,
        val myLevel: Int,
        val levels: String,
        val volume: String,
        val nextVolume: String
    )
}
