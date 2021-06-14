package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetCurrencyRatesResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        @SerializedName("BTCRate") val btcRate: Double,
        @SerializedName("ETHRate") val ethRate: Double,
        @SerializedName("TRXRate") val trxRate: Double,
        @SerializedName("LTCRate") val ltcRate: Double
    )
}
