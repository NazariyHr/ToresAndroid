package com.devcraft.tores.entities

import android.content.Context
import androidx.core.content.ContextCompat
import com.devcraft.tores.R

enum class PartnerStatus{
    MODERATION,
    ACCEPTED,
    DECLINED;

    companion object {
        fun parseStatus(statusString: String): PartnerStatus {
            return when (statusString) {
                "Moderation" -> MODERATION
                "Accepted" -> ACCEPTED
                "Declined" -> DECLINED
                else -> throw Exception("Error parsing partner status, passed status: $statusString")
            }
        }
    }

    fun getStatusColor(context: Context): Int {
        return when (this) {
            MODERATION -> ContextCompat.getColor(context, R.color.colorOrange)
            ACCEPTED -> ContextCompat.getColor(context, R.color.colorGreen)
            DECLINED -> ContextCompat.getColor(context, R.color.colorRed)
        }
    }

    fun getStatusText(context: Context): String {
        return when (this) {
            MODERATION -> context.getString(R.string.partner_status_moderation)
            ACCEPTED -> context.getString(R.string.partner_status_accepted)
            DECLINED -> context.getString(R.string.partner_status_declined)
        }
    }
}
