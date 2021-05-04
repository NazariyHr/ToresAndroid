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
    NOT_SPECIFIED;

    companion object {
        fun parseCurrency(currencyString: String): Currency {
            return when (currencyString) {
                "BTC", "bts" -> BITCOIN
                "LTC" -> LITECOIN
                "ETH" -> ETHEREUM
                "TRX" -> TRON
                "usdt" -> TETHER
                "-" -> NOT_SPECIFIED
                else -> throw Exception("Error parsing currency, passed currency: $currencyString")
            }
        }
    }

    fun getCurrencyDrawable(context: Context): Drawable? {
        return when (this) {
            BITCOIN -> ContextCompat.getDrawable(context, R.drawable.ic_bitcoin)
            LITECOIN -> ContextCompat.getDrawable(context, R.drawable.ic_litecoin)
            ETHEREUM -> ContextCompat.getDrawable(context, R.drawable.ic_ethereum)
            TRON -> ContextCompat.getDrawable(context, R.drawable.ic_tron)
            TETHER -> ContextCompat.getDrawable(context, R.drawable.ic_tether)
            NOT_SPECIFIED -> null
        }
    }
}