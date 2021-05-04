package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetReferralProfitsHistoryResponse
import com.devcraft.tores.entities.ReferralProfitsHistoryData

class GetReferralProfitsHistoryMapper :
    BaseRepositoryMapper<GetReferralProfitsHistoryResponse, ReferralProfitsHistoryData>() {

    override fun mapDtoToEntity(dto: GetReferralProfitsHistoryResponse): ReferralProfitsHistoryData {
        dto.let {
            val profits = dto.data.referralProfits.map { p ->
                ReferralProfitsHistoryData.ReferralProfit(
                    p.id,
                    p.createdAt,
                    p.login,
                    p.level,
                    p.partnerProfit,
                    p.percent,
                    p.amount
                )
            }
            return ReferralProfitsHistoryData(
                profits,
                it.data.referralProfitsTotal
            )
        }
    }
}
