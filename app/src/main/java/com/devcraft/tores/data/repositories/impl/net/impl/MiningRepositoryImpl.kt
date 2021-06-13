package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.MiningRepository
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.AddToMiningRequest
import com.devcraft.tores.data.repositories.impl.net.dto.WithdrawFromMiningRequest
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetMiningInfoMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.MiningApi
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.entities.MiningInfo

class MiningRepositoryImpl(
    private val miningApi: MiningApi,
    private val tokenRepository: TokenRepository,
    private val getMiningInfoMapper: GetMiningInfoMapper
) : BaseNetRepository(), MiningRepository {

    override suspend fun getMiningInfo(): ResultWithStatus<MiningInfo> {
        return enqueueCallResultWithStatusSuspended(
            miningApi.getMiningInfo(tokenRepository.getToken().bearerToken),
            getMiningInfoMapper
        )
    }

    override suspend fun addToMining(amount: Double, balanceType: BalanceType): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            miningApi.addToMining(
                tokenRepository.getToken().bearerToken,
                AddToMiningRequest(amount, balanceType)
            )
        )
    }

    override suspend fun withdrawFromMining(amount: Double): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            miningApi.withdrawFromMining(
                tokenRepository.getToken().bearerToken,
                WithdrawFromMiningRequest(amount)
            )
        )
    }
}
