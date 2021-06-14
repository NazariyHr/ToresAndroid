package com.devcraft.tores.presentation.ui.main.dashboard.currencyListAdapter

import com.devcraft.tores.entities.Currency


data class DH(
    val currency: Currency
) {
    override fun toString(): String {
        return "${currency.getShortTitle()} | ${currency.getTitle()}"
    }
}