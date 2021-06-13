package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetMiningInfoResponse
import com.devcraft.tores.entities.MiningInfo

class GetMiningInfoMapper : BaseRepositoryMapper<GetMiningInfoResponse, MiningInfo>() {
    override fun mapDtoToEntity(dto: GetMiningInfoResponse): MiningInfo {
        dto.let {
            return MiningInfo(
                it.data.myDeposit,
                it.data.rankBalance,
                it.data.myBalance.toBigDecimal(),
                it.data.myRankBalance.toBigDecimal(),
                it.data.totalTopUps,
                it.data.availableToMining
            )
        }
    }
}
