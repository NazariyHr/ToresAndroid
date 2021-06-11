package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetUserResponse
import com.devcraft.tores.entities.PaymentConfirmationWay
import com.devcraft.tores.entities.Rank
import com.devcraft.tores.entities.RankLevel
import com.devcraft.tores.entities.User

class GetUserMapper : BaseRepositoryMapper<GetUserResponse, User>() {
    override fun mapDtoToEntity(dto: GetUserResponse): User {
        return with(dto) {
            User(
                email = data.me.email,
                login = data.me.login,
                balance = data.me.balance,
                rankBalance = data.me.rankBalance,
                totalProfit = data.me.totalProfit,
                partnerProfit = data.me.partnerProfit,
                rankProfit = data.me.rankProfit,
                paymentConfirmationWay = PaymentConfirmationWay.parse(data.me.paymentConfirmationWay),
                rankLevel = RankLevel(data.me.level.level, data.me.level.gotAt),
                currentRank = Rank.parseRank(data.me.currentRank),
                nextRank = if (data.me.nextRank != null) Rank.parseRank(data.me.nextRank) else null,
                referralCode = data.me.refCode,
                registeredAt = data.me.registeredAt,
                lastEntrance = data.me.lastEntrance,
                ip = data.me.ip,
                financePasswordSet = data.me.ppSet
            )
        }
    }
}