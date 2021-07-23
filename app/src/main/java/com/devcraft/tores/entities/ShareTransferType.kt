package com.devcraft.tores.entities

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.devcraft.tores.R

enum class ShareTransferType {
    BUY_SHARES, TRANSFER_SHARES;

    companion object {
        fun parse(typeString: String): ShareTransferType {
            return when (typeString) {
                "Buy shares" -> BUY_SHARES
                "Transfer shares" -> TRANSFER_SHARES
                else -> throw Exception("Error parsing type, passed type: $typeString")
            }
        }
    }

    fun getTypeText(context: Context): String {
        return when (this) {
            BUY_SHARES -> context.getString(R.string.shares_transaction_type_buy_shares)
            TRANSFER_SHARES -> context.getString(R.string.shares_transaction_type_transfer_shares)
        }
    }

    fun getTypeDrawable(context: Context): Drawable? {
        return when (this) {
            BUY_SHARES -> ContextCompat.getDrawable(context, R.drawable.ic_arrow_up_filled_green)
            TRANSFER_SHARES -> ContextCompat.getDrawable(
                context,
                R.drawable.ic_arrow_right_filled_oragne
            )
        }
    }
}
