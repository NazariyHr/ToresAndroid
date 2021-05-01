package com.devcraft.tores.entities

import java.math.BigDecimal

class TopupsAndWithdrawalsData(
    val transactions: List<Transaction>,
    val totalTopUps: Double
) {

    class Transaction(
        val id: Long,
        val createdAt: String,
        val type: Type,
        val currency: Currency,
        val amountInCurrency: BigDecimal,
        val amount: Double,
        val transactionStatus: TransactionStatus,
        val wallet: String,
        val remaining: Long
    ) {
        companion object {
            fun parseType(typeString: String): Type {
                return when (typeString) {
                    "Topup" -> Type.TOPUP
                    "Withdrawal" -> Type.WITHDRAWAL
                    else -> throw Exception("Error parsing type, passed type: $typeString")
                }
            }
        }

        enum class Type {
            TOPUP, WITHDRAWAL
        }
    }
}
