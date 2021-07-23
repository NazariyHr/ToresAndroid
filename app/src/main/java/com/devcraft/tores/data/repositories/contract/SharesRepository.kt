package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.entities.ShareTransfer
import com.devcraft.tores.entities.SharesTotalInfo

interface SharesRepository {
    suspend fun getSharesTotalInfo(): ResultWithStatus<SharesTotalInfo>
    suspend fun getSharesHistory(): ResultWithStatus<MutableList<ShareTransfer>>
    suspend fun buyShares(amount: Int): ResultStatus
    suspend fun transferSharesToUser(amount: Int, login: String): ResultStatus
}
