package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetDashboardNetResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val lastProfits: List<LastProfit>,
        @SerializedName("lastRegistered") val lastRegisters: List<LastRegistered>
    ) {
        class LastProfit(
            val createdAtDate: String,
            val createdAtTime: String,
            val profit: String
        )

        class LastRegistered(
            val login: String,
            val createdAtDate: String,
            val createdAtTime: String
        )
    }
}
