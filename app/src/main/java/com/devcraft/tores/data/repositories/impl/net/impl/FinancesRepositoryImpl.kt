package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.GetTopupsAndWithdrawalsResponse
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetTopupsAndWithdrawalsMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.FinancesApi
import com.devcraft.tores.entities.TopupsAndWithdrawalsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FinancesRepositoryImpl(
    private val financesApi: FinancesApi,
    private val tokenRepository: TokenRepository,
    private val getTopupsAndWithdrawalsMapper: GetTopupsAndWithdrawalsMapper
) : BaseNetRepository(), FinancesRepository {
    override suspend fun getTopupsAndWithdrawalsData(): ResultWithStatus<TopupsAndWithdrawalsData> {
        return suspendCoroutine { continuation ->
            financesApi
                .getTopupsAndWithdrawals(tokenRepository.getToken().bearerToken)
                .enqueue(object : Callback<GetTopupsAndWithdrawalsResponse> {
                    override fun onResponse(
                        call: Call<GetTopupsAndWithdrawalsResponse>,
                        response: Response<GetTopupsAndWithdrawalsResponse>
                    ) {
                        val result = parseResult(response, getTopupsAndWithdrawalsMapper)
                        continuation.resume(result)
                    }

                    override fun onFailure(
                        call: Call<GetTopupsAndWithdrawalsResponse>,
                        t: Throwable
                    ) {
                        continuation.resume(ResultWithStatus(null, ResultStatus.failure(t)))
                    }
                })
        }
    }
}