package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.entities.RankInfo
import com.devcraft.tores.entities.RankSystemLevel
import com.devcraft.tores.entities.UserRankSystemInfo

interface RankRepository {
    suspend fun getRankInfo(): ResultWithStatus<RankInfo>

    suspend fun getUserRankSystemInfo(): ResultWithStatus<UserRankSystemInfo>
}
