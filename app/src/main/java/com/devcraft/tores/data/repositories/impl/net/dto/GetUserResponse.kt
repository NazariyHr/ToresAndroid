package com.devcraft.tores.data.repositories.impl.net.dto

import com.devcraft.tores.data.repositories.impl.net.dto.base.NetworkBaseResponse
import com.google.gson.annotations.SerializedName

class GetUserResponse(
    val data: Data
) : NetworkBaseResponse() {
    class Data(
        val me: Me
    ) {
        class Me(
            val email: String,
            val login: String,
            val telegram: String?,
            val balance: Double,
            @SerializedName("rank_balance") val rankBalance: Double,
            val deposit: Boolean,
            @SerializedName("payment_confirmation_way") val paymentConfirmationWay: String,
            @SerializedName("pp_set") val ppSet: Boolean,
            val topups: Double,
            val partnerProfit: Double,
            val rankProfit: Double,
            val totalProfit: Double,
            val invitedBy: String,
            val level: Boolean,
            @SerializedName("ref_code") val refCode: String,
            val currentRank: String,
            val nextRank: String,
            @SerializedName("registered_at") val registeredAt: String,
            @SerializedName("last_entrance") val lastEntrance: String,
            val ip: String,
            val shouldVote: Boolean,
            val voteResults: MutableList<Int>
        )
    }
}
