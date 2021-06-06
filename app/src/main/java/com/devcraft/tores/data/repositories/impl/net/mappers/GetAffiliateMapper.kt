package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetAffiliateResponse
import com.devcraft.tores.entities.AffiliateInfo

class GetAffiliateMapper : BaseRepositoryMapper<GetAffiliateResponse, AffiliateInfo>() {
    override fun mapDtoToEntity(dto: GetAffiliateResponse): AffiliateInfo {
        dto.let {
            return AffiliateInfo(
                it.data.sponsor.login,
                it.data.sponsor.email,
                it.data.sponsor.phone,
                it.data.totalPartners,
                it.data.totalDepositsAmount,
                it.data.activePartners,
                it.data.inactivePartners,
                it.data.refLinkVisits,
                it.data.overallProfit,
                it.data.overallProfit24h
            )
        }
    }
}
