package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.data.repositories.contract.SharesRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.BuySharesRequest
import com.devcraft.tores.data.repositories.impl.net.dto.TransferSharesRequest
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetSharesHistoryMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.GetSharesTotalInfoMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.SharesApi
import com.devcraft.tores.entities.ShareTransfer
import com.devcraft.tores.entities.SharesTotalInfo

class SharesRepositoryImpl(
    private val sharesApi: SharesApi,
    private val getSharesTotalInfoMapper: GetSharesTotalInfoMapper,
    private val getSharesHistoryMapper: GetSharesHistoryMapper
) : BaseNetRepository(), SharesRepository {

    override suspend fun getSharesTotalInfo(): ResultWithStatus<SharesTotalInfo> {
        return enqueueCallResultWithStatusSuspended(
            sharesApi.getSharesTotalInfo(),
            getSharesTotalInfoMapper
        )
    }

    override suspend fun getSharesHistory(): ResultWithStatus<MutableList<ShareTransfer>> {
        return enqueueCallResultWithStatusSuspended(
            sharesApi.getSharesHistory(),
            getSharesHistoryMapper
        )
    }

    override suspend fun buyShares(amount: Int): ResultStatus {
        return enqueueCallOnlyStatusSuspended(sharesApi.buyShares(BuySharesRequest(amount)))
    }

    override suspend fun transferSharesToUser(amount: Int, login: String): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            sharesApi.transferShares(
                TransferSharesRequest(
                    amount,
                    login
                )
            )
        )
    }
}
