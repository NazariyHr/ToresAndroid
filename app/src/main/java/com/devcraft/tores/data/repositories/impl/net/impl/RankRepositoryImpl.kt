package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.RankRepository
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.GetRankInfoResponse
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetRankInfoMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.RanksApi
import com.devcraft.tores.entities.RankInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RankRepositoryImpl(
    private val ranksApi: RanksApi,
    private val tokenRepository: TokenRepository,
    private val getRankInfoMapper: GetRankInfoMapper
) : BaseNetRepository(), RankRepository {

    override suspend fun getRankInfo(): ResultWithStatus<RankInfo> {
        return suspendCoroutine { continuation ->
            ranksApi
                .getRankInfo(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetRankInfoResponse> {
                    override fun onResponse(
                        call: Call<GetRankInfoResponse>,
                        response: Response<GetRankInfoResponse>
                    ) {
                        val result = parseResult(response, getRankInfoMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(call: Call<GetRankInfoResponse>, t: Throwable) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }
}
