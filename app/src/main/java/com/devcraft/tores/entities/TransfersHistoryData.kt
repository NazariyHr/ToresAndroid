package com.devcraft.tores.entities

import android.content.Context
import com.devcraft.tores.R

class TransfersHistoryData(
    val transactions: List<Transaction>
) {
    class Transaction(
        val id: Long,
        val amount: Double,
        val login: String,
        val wallet: String,
        val balance: BalanceType,
        val type: Type,
        val createdAt: String,
        val transactionStatus: TransactionStatus
    ) {

        enum class Type {
            RECEIVED_FROM_USER, TRANSFER_TO_EXCHANGE, TRANSFER_TO_USER;

            companion object {
                fun parse(typeString: String): Type {
                    return when (typeString) {
                        "Received from user" -> RECEIVED_FROM_USER
                        "Transfer to exchange" -> TRANSFER_TO_EXCHANGE
                        "Transfer to user" -> TRANSFER_TO_USER
                        else -> throw Exception("Error parsing type, passed type: $typeString")
                    }
                }
            }

            fun getTypeText(context: Context): String {
                return when (this) {
                    RECEIVED_FROM_USER -> context.getString(R.string.transaction_type_received_from_user)
                    TRANSFER_TO_EXCHANGE -> context.getString(R.string.transaction_type_sent_to_exchange)
                    TRANSFER_TO_USER -> context.getString(R.string.transaction_type_send_to_user)
                }
            }
        }
    }
}