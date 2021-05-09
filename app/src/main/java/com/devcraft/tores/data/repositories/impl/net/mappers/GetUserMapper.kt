package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetUserResponse
import com.devcraft.tores.entities.PaymentConfirmationWay
import com.devcraft.tores.entities.User

class GetUserMapper : BaseRepositoryMapper<GetUserResponse, User>() {
    override fun mapDtoToEntity(dto: GetUserResponse): User {
        return with(dto) {
            User(
                balance = data.me.balance,
                rankBalance = data.me.rankBalance,
                totalProfit = data.me.totalProfit,
                partnerProfit = data.me.partnerProfit,
                rankProfit = data.me.rankProfit,
                paymentConfirmationWay = PaymentConfirmationWay.parse(data.me.paymentConfirmationWay)
            )
        }
    }
}