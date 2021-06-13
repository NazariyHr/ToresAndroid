package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.entities.BalanceType
import com.google.gson.annotations.SerializedName
import java.util.*

class AddToMiningRequest(
    val amount: Double,
    balanceType: BalanceType,
) {

    @SerializedName("balance")
    var balance: String = balanceType.name.toLowerCase(Locale.US)
}
