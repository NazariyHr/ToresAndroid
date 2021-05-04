package com.devcraft.tores.entities

import android.content.Context
import com.devcraft.tores.R

class MiningHistoryData(
    val transactions: List<Transaction>,
    val myDeposit: Double
) {

    class Transaction(
        val id: Long,
        val createdAt: String,
        val type: Type,
        val balance: BalanceType,
        val amount: Double,
        val timeLeft: String,
        val transactionStatus: TransactionStatus
    ) {
        enum class Type {
            DEPOSIT_PROFIT, OPEN_DEPOSIT, REINVEST, WITHDRAW_FROM_DEPOSIT;

            companion object {
                fun parse(typeString: String): Type {
                    return when (typeString) {
                        "Deposit profit" -> DEPOSIT_PROFIT
                        "Open deposit" -> OPEN_DEPOSIT
                        "Reinvest" -> REINVEST
                        "Withdraw from deposit" -> WITHDRAW_FROM_DEPOSIT
                        else -> throw Exception("Error parsing type, passed type: $typeString")
                    }
                }
            }

            fun getTypeText(context: Context): String {
                return when (this) {
                    DEPOSIT_PROFIT -> context.getString(R.string.transaction_type_deposit_profit)
                    OPEN_DEPOSIT -> context.getString(R.string.transaction_type_open_deposit)
                    REINVEST -> context.getString(R.string.transaction_type_reinvest)
                    WITHDRAW_FROM_DEPOSIT -> context.getString(R.string.transaction_type_withdraw_from_deposit)
                }
            }
        }
    }
}
