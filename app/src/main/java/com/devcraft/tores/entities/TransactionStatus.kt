package com.devcraft.tores.entities

import android.content.Context
import androidx.core.content.ContextCompat
import com.devcraft.tores.R

enum class TransactionStatus {
    CREATED,
    CANCELLED_BY_USER,
    CANCELLED_BY_ADMINISTRATOR,
    CANCELLED,
    PERFORMED,
    PAID,
    WAITING_TAC,
    WRONG_TAC,
    PERFORMING,
    AWAITING_WITHDRAW,
    AWAITING,
    SENT_TO_EXCHANGE;

    companion object {
        fun parseStatus(statusString: String): TransactionStatus {
            return when (statusString) {
                "Created" -> CREATED
                "Cancelled by user" -> CANCELLED_BY_USER
                "Cancelled by administrator" -> CANCELLED_BY_ADMINISTRATOR
                "Cancelled" -> CANCELLED
                "Performed" -> PERFORMED
                "Paid" -> PAID
                "Waiting TAC" -> WAITING_TAC
                "Cancelled due to tac attempts" -> WRONG_TAC
                "Performing" -> PERFORMING
                "Awaiting withdraw" -> AWAITING_WITHDRAW
                "Awaiting" -> AWAITING
                "Sent to exchange" -> SENT_TO_EXCHANGE
                else -> throw Exception("Error parsing transaction status, passed status: $statusString")
            }
        }
    }

    fun getStatusColor(context: Context): Int {
        return when (this) {
            CREATED,
            WAITING_TAC,
            PERFORMING,
            AWAITING_WITHDRAW,
            AWAITING -> ContextCompat.getColor(context, R.color.colorOrange)
            CANCELLED_BY_USER,
            CANCELLED_BY_ADMINISTRATOR,
            CANCELLED,
            WRONG_TAC -> ContextCompat.getColor(context, R.color.colorRed)
            PERFORMED,
            PAID,
            SENT_TO_EXCHANGE -> ContextCompat.getColor(context, R.color.colorGreen)
        }
    }

    fun getStatusText(context: Context, performedArePaid: Boolean = false): String {
        return when (this) {
            CREATED -> context.getString(R.string.payment_await)
            CANCELLED_BY_USER -> context.getString(R.string.cancelled_by_user)
            CANCELLED_BY_ADMINISTRATOR -> context.getString(R.string.cancelled_by_admin)
            CANCELLED -> context.getString(R.string.cancelled)
            PERFORMED -> if (performedArePaid) context.getString(R.string.paid) else context.getString(
                R.string.performed
            )
            PAID -> context.getString(R.string.paid)
            WAITING_TAC -> context.getString(R.string.waiting_tac)
            WRONG_TAC -> context.getString(R.string.cancelled_due_to_tac_attempts)
            PERFORMING -> context.getString(R.string.performing)
            AWAITING_WITHDRAW -> context.getString(R.string.awaiting_withdraw)
            AWAITING -> context.getString(R.string.awaiting)
            SENT_TO_EXCHANGE -> context.getString(R.string.sent_to_exchange)
        }
    }
}
