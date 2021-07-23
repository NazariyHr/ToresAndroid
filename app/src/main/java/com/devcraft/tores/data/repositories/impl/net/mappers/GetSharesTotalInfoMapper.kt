package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetSharesTotalInfoResponse
import com.devcraft.tores.entities.SharesTotalInfo

class GetSharesTotalInfoMapper :
    BaseRepositoryMapper<GetSharesTotalInfoResponse, SharesTotalInfo>() {
    override fun mapDtoToEntity(dto: GetSharesTotalInfoResponse): SharesTotalInfo {
        dto.data.let {
            return SharesTotalInfo(
                it.totalShares,
                it.sharesLocked,
                it.myBalance,
                it.myRankBalance,
                it.oneSharePrice,
                it.totalProgressInPercent,
                it.availableShares
            )
        }
    }
}
