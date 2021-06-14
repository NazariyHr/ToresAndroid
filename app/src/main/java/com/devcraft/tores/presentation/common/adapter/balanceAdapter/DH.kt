package com.devcraft.tores.presentation.common.adapter.balanceAdapter

import com.devcraft.tores.R
import com.devcraft.tores.app.App
import com.devcraft.tores.entities.BalanceType


data class DH(
    val balanceTypeTitle: String,
    val balanceType: BalanceType,
    val balance: String
) {
    override fun toString(): String {
        return String.format(
            App.instance.getString(R.string.balance_title_with_tores),
            balanceTypeTitle,
            balance
        )
    }
}