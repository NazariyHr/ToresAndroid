package com.devcraft.tores.entities

import android.content.Context
import androidx.core.content.ContextCompat
import com.devcraft.tores.R

enum class ShareTransferStatus {
    BUY_SHARES,
    TRANSFER_SHARES,
    PERFORMED,
    AWAITING;

    companion object {
        fun parseStatus(statusString: String): ShareTransferStatus {
            return when (statusString) {
                "Buy shares" -> BUY_SHARES
                "Transfer shares" -> TRANSFER_SHARES
                "Performed" -> PERFORMED
                "Awaiting" -> AWAITING
                else -> throw Exception("Error parsing shares status, passed status: $statusString")
            }
        }
    }

    fun getStatusColor(context: Context): Int {
        return when (this) {
            BUY_SHARES -> ContextCompat.getColor(context, R.color.colorOrange)
            TRANSFER_SHARES -> ContextCompat.getColor(context, R.color.colorRed)
            PERFORMED -> ContextCompat.getColor(context, R.color.colorGreen)
            AWAITING -> ContextCompat.getColor(context, R.color.colorOrange)
        }
    }

    fun getStatusText(context: Context): String {
        return when (this) {
            BUY_SHARES -> context.getString(R.string.shares_transaction_type_buy_shares)
            TRANSFER_SHARES -> context.getString(R.string.shares_transaction_type_transfer_shares)
            PERFORMED -> context.getString(R.string.performed)
            AWAITING -> context.getString(R.string.performing)
        }
    }
}
