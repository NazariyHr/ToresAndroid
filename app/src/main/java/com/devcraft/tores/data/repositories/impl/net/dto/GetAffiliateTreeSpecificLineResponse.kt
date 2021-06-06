package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse

class GetAffiliateTreeSpecificLineResponse(
    val data: Data
) : NetworkBaseResponse() {

    class Data(
        val users: MutableList<User>
    ) {
        class User(
            val id: Int,
            val login: String,
            val line: Int,
            val registeredAtDate: String,
            val registeredAtTime: String,
            val percent: Int,
            val colorClass: String,
            val firstLevelExists: Boolean,
            val deposit: Double,
            val profit: Double
        )
    }
}
