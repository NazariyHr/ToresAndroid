package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetUserRankSystemInfoResponse
import com.devcraft.tores.entities.UserRankSystemInfo

class GetUserRankSystemInfoResponseMapper :
    BaseRepositoryMapper<GetUserRankSystemInfoResponse, UserRankSystemInfo>() {
    override fun mapDtoToEntity(dto: GetUserRankSystemInfoResponse): UserRankSystemInfo {
        dto.let {
            return UserRankSystemInfo(
                it.data.myDeposit,
                it.data.myLevel,
                it.data.levels,
                it.data.volume,
                it.data.nextVolume
            )
        }
    }
}
