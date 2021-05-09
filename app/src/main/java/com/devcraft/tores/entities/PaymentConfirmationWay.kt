package com.devcraft.tores.entities

enum class PaymentConfirmationWay {
    EMAIL, PAYMENT_PASSWORD;

    companion object {
        fun parse(str: String): PaymentConfirmationWay {
            return when (str) {
                "email" -> PaymentConfirmationWay.EMAIL
                "pp" -> PaymentConfirmationWay.PAYMENT_PASSWORD
                else -> throw Exception("Error parsing Payment confirmation way, passed way: $str")
            }
        }
    }
}