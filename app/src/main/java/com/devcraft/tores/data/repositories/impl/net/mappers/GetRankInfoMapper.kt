package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetRankInfoResponse
import com.devcraft.tores.entities.Rank
import com.devcraft.tores.entities.RankInfo

class GetRankInfoMapper : BaseRepositoryMapper<GetRankInfoResponse, RankInfo>() {
    override fun mapDtoToEntity(dto: GetRankInfoResponse): RankInfo {
        dto.let {
            return RankInfo(
                Rank.parseRank(it.data.currentRankRU),
                Rank.parseRank(it.data.nextRankRU),
                it.data.myLevel
            )
        }
    }
}
