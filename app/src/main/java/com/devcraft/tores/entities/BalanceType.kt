package com.devcraft.tores.entities

enum class BalanceType {
    BALANCE, RANK_BALANCE, NOT_SPECIFIED;

    companion object {
        fun parse(balanceTypeString: String): BalanceType {
            return when (balanceTypeString) {
                "balance" -> BALANCE
                "rank_balance" -> RANK_BALANCE
                "-" -> NOT_SPECIFIED
                else -> throw Exception("Error parsing balance type, passed type: $balanceTypeString")
            }
        }
    }
}