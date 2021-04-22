package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetUserNetResponse
import com.devcraft.tores.entities.User

class GetUserNetMapper : BaseRepositoryMapper<GetUserNetResponse, User>() {
    override fun mapDtoToEntity(dto: GetUserNetResponse): User {
        return with(dto) {
            User(
                balance = data.me.balance,
                rankBalance = data.me.rankBalance,
                totalProfit = data.me.totalProfit,
                partnerProfit = data.me.partnerProfit,
                rankProfit = data.me.rankProfit
            )
        }
    }
}