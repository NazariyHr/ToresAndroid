package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.RankRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetRankInfoMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.RanksApi
import com.devcraft.tores.entities.RankInfo

class RankRepositoryImpl(
    private val ranksApi: RanksApi,
    private val getRankInfoMapper: GetRankInfoMapper
) : BaseNetRepository(), RankRepository {

    override suspend fun getRankInfo(): ResultWithStatus<RankInfo> {
        return enqueueCallResultWithStatusSuspended(
            ranksApi.getRankInfo(),
            getRankInfoMapper
        )
    }
}
