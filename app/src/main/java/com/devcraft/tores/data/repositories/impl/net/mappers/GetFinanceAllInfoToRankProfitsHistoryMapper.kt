package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetFinanceAllInfoResponse
import com.devcraft.tores.entities.Rank
import com.devcraft.tores.entities.RankProfitsHistoryData

class GetFinanceAllInfoToRankProfitsHistoryMapper :
    BaseRepositoryMapper<GetFinanceAllInfoResponse, RankProfitsHistoryData>() {
    override fun mapDtoToEntity(dto: GetFinanceAllInfoResponse): RankProfitsHistoryData {
        dto.let {
            val profits = dto.history.rankProfits.map { p ->
                RankProfitsHistoryData.RankProfit(
                    p.id,
                    p.createdAt,
                    Rank.parseRank(p.rank),
                    p.amount
                )
            }
            return RankProfitsHistoryData(
                profits
            )
        }
    }
}
