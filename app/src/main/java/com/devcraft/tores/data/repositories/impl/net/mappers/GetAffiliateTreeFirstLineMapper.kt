package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetAffiliateTreeFirstLineResponse
import com.devcraft.tores.entities.AffiliateTreeUser

class GetAffiliateTreeFirstLineMapper :
    BaseRepositoryMapper<GetAffiliateTreeFirstLineResponse, MutableList<AffiliateTreeUser>>() {
    override fun mapDtoToEntity(dto: GetAffiliateTreeFirstLineResponse): MutableList<AffiliateTreeUser> {
        dto.let {
            val result = mutableListOf<AffiliateTreeUser>()

            it.data.users.forEach {
                result.add(
                    AffiliateTreeUser(
                        it.id,
                        it.login,
                        it.line,
                        it.registeredAtDate,
                        it.registeredAtTime,
                        it.percent,
                        it.colorClass,
                        it.firstLevelExists,
                        it.deposit,
                        it.profit
                    )
                )
            }

            return result
        }
    }
}
