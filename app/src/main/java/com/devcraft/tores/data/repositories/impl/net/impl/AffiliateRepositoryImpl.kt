package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.AffiliateRepository
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.GetAffiliateResponse
import com.devcraft.tores.data.repositories.impl.net.dto.GetAffiliateTreeFirstLineResponse
import com.devcraft.tores.data.repositories.impl.net.dto.GetAffiliateTreeSpecificLineResponse
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetAffiliateMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.GetAffiliateTreeFirstLineMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.GetAffiliateTreeSpecificLineMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.AffiliateApi
import com.devcraft.tores.entities.AffiliateInfo
import com.devcraft.tores.entities.AffiliateTreeUser
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AffiliateRepositoryImpl(
    private val affiliateApi: AffiliateApi,
    private val tokenRepository: TokenRepository,
    private val getAffiliateMapper: GetAffiliateMapper,
    private val getAffiliateTreeFirstLineMapper: GetAffiliateTreeFirstLineMapper,
    private val getAffiliateTreeSpecificLineMapper: GetAffiliateTreeSpecificLineMapper
) : BaseNetRepository(), AffiliateRepository {

    override suspend fun getAffiliateInfo(): ResultWithStatus<AffiliateInfo> {
        return suspendCoroutine { continuation ->
            affiliateApi
                .getAffiliate(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetAffiliateResponse> {
                    override fun onResponse(
                        call: Call<GetAffiliateResponse>,
                        response: Response<GetAffiliateResponse>
                    ) {
                        val result = parseResult(response, getAffiliateMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(call: Call<GetAffiliateResponse>, t: Throwable) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }

    override suspend fun getAffiliateTreeFirstLine(): ResultWithStatus<MutableList<AffiliateTreeUser>> {
        val fakeData = generateFakeTreeFirstLineResponse()
        return suspendCoroutine { continuation ->
            val resultData = getAffiliateTreeFirstLineMapper.mapDtoToEntity(fakeData)
            continuation.resume(ResultWithStatus(resultData, ResultStatus.success()))
        }
    }

    override suspend fun getAffiliateTreeSpecificLine(
        userId: Int,
        line: Int
    ): ResultWithStatus<MutableList<AffiliateTreeUser>> {
        val fakeData = generateFakeTreeSpecificLineResponse(userId, line)
        return suspendCoroutine { continuation ->
            val resultData = getAffiliateTreeSpecificLineMapper.mapDtoToEntity(fakeData)
            continuation.resume(ResultWithStatus(resultData, ResultStatus.success()))
        }
    }

    private suspend fun generateFakeTreeFirstLineResponse(): GetAffiliateTreeFirstLineResponse {
        delay(1000)
        val resultUsers = mutableListOf<GetAffiliateTreeFirstLineResponse.Data.User>()
        resultUsers.addAll(
            mutableListOf(
                GetAffiliateTreeFirstLineResponse.Data.User(
                    6245,
                    "Fake user 6245",
                    1,
                    "26.04.2021",
                    "11:52",
                    10,
                    "green",
                    true,
                    100.0,
                    0.10
                ),
                GetAffiliateTreeFirstLineResponse.Data.User(
                    6246,
                    "Fake user 6246",
                    1,
                    "26.04.2021",
                    "11:53",
                    3,
                    "green",
                    true,
                    30.0,
                    0.03
                ),
                GetAffiliateTreeFirstLineResponse.Data.User(
                    6247,
                    "Fake user 6247",
                    1,
                    "26.04.2021",
                    "11:54",
                    2,
                    "red",
                    false,
                    20.0,
                    0.02
                )
            )
        )
        return GetAffiliateTreeFirstLineResponse(
            GetAffiliateTreeFirstLineResponse.Data(resultUsers)
        )
    }

    private suspend fun generateFakeTreeSpecificLineResponse(
        userId: Int,
        line: Int
    ): GetAffiliateTreeSpecificLineResponse {
        delay(if (line < 3) 1000 else if (line < 5) 500 else 200)
        val resultUsers = mutableListOf<GetAffiliateTreeSpecificLineResponse.Data.User>()
        if (line <= 10) {
            resultUsers.addAll(
                mutableListOf(
                    GetAffiliateTreeSpecificLineResponse.Data.User(
                        userId + 1034 + 1 + line,
                        "Fake user ${userId + 1034 + 1 + line}",
                        line,
                        "26.04.2021",
                        "11:52",
                        10,
                        "green",
                        line < 10,
                        200.0,
                        0.2
                    ),
                    GetAffiliateTreeSpecificLineResponse.Data.User(
                        userId + 1034 + 2 + line,
                        "Fake user ${userId + 1034 + 2 + line}",
                        line,
                        "26.04.2021",
                        "11:53",
                        5,
                        "green",
                        line < 5,
                        100.0,
                        0.1
                    )
                )
            )
        }
        return GetAffiliateTreeSpecificLineResponse(
            GetAffiliateTreeSpecificLineResponse.Data(resultUsers)
        )
    }
}
