package com.devcraft.tores.entities

data class ProfitsAndRegisters(
    val profits: MutableList<Profit>,
    val registers: MutableList<Registered>
) {
    class Profit(
        val createdAtDate: String,
        val createdAtTime: String,
        val profit: String
    )

    class Registered(
        val login: String,
        val createdAtDate: String,
        val createdAtTime: String
    )
}
