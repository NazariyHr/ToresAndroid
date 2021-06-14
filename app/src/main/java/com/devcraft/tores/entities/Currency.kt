package com.devcraft.tores.entities

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.devcraft.tores.R

enum class Currency {
    BITCOIN,
    LITECOIN,
    ETHEREUM,
    TRON,
    TETHER,
    PAYEER,
    PERFECTMONEY,
    NOT_SPECIFIED;

    companion object {
        fun parseCurrency(currencyString: String): Currency {
            return when (currencyString) {
                "BTC", "bts" -> BITCOIN
                "LTC" -> LITECOIN
                "ETH" -> ETHEREUM
                "TRX" -> TRON
                "usdt" -> TETHER
                "Payeer", "PYR" -> PAYEER
                "Perfectmoney", "PM" -> PERFECTMONEY
                "-" -> NOT_SPECIFIED
                else -> throw Exception("Error parsing currency, passed currency: $currencyString")
            }
        }

        fun getCurrenciesForTopup(): MutableList<Currency> =
            mutableListOf(BITCOIN, PERFECTMONEY, ETHEREUM, TRON, LITECOIN)

        fun getCurrenciesForWithdraw(): MutableList<Currency> =
            mutableListOf(BITCOIN, ETHEREUM, PAYEER, PERFECTMONEY, TETHER, TRON, LITECOIN)
    }

    fun getCurrencyDrawable(context: Context): Drawable? {
        return when (this) {
            BITCOIN -> ContextCompat.getDrawable(context, R.drawable.ic_bitcoin)
            LITECOIN -> ContextCompat.getDrawable(context, R.drawable.ic_litecoin)
            ETHEREUM -> ContextCompat.getDrawable(context, R.drawable.ic_ethereum)
            TRON -> ContextCompat.getDrawable(context, R.drawable.ic_tron)
            TETHER -> ContextCompat.getDrawable(context, R.drawable.ic_tether)
            PERFECTMONEY -> ContextCompat.getDrawable(context, R.drawable.ic_perfect_money)
            PAYEER -> ContextCompat.getDrawable(context, R.drawable.ic_payeer)
            NOT_SPECIFIED -> null
        }
    }

    fun getTitle(): String {
        return when (this) {
            BITCOIN -> "Bitcoin"
            LITECOIN -> "Litecoin"
            ETHEREUM -> "Ethereum"
            TRON -> "Tron"
            TETHER -> "Tether"
            PERFECTMONEY -> "Perfectmoney"
            PAYEER -> "Payeer"
            NOT_SPECIFIED -> ""
        }
    }

    fun getShortTitle(): String {
        return when (this) {
            BITCOIN -> "BTC"
            LITECOIN -> "LTC"
            ETHEREUM -> "ETH"
            TRON -> "TRX"
            TETHER -> "USDT"
            PERFECTMONEY -> "PM"
            PAYEER -> "PYR"
            NOT_SPECIFIED -> ""
        }
    }
}