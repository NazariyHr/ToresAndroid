package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.RankRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetRankInfoMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.GetUserRankSystemInfoResponseMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.RanksApi
import com.devcraft.tores.entities.RankInfo
import com.devcraft.tores.entities.UserRankSystemInfo

class RankRepositoryImpl(
    private val ranksApi: RanksApi,
    private val getRankInfoMapper: GetRankInfoMapper,
    private val getUserRankSystemInfoResponseMapper: GetUserRankSystemInfoResponseMapper
) : BaseNetRepository(), RankRepository {

    override suspend fun getRankInfo(): ResultWithStatus<RankInfo> {
        return enqueueCallResultWithStatusSuspended(
            ranksApi.getRankInfo(),
            getRankInfoMapper
        )
    }

    override suspend fun getUserRankSystemInfo(): ResultWithStatus<UserRankSystemInfo> {
        return enqueueCallResultWithStatusSuspended(
            ranksApi.getUserRankSystemInfo(),
            getUserRankSystemInfoResponseMapper
        )
    }
}
