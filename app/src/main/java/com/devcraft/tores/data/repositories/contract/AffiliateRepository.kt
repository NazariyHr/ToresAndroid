package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.entities.AffiliateInfo
import com.devcraft.tores.entities.AffiliateTreeUser

interface AffiliateRepository {
    suspend fun getAffiliateInfo(): ResultWithStatus<AffiliateInfo>
    suspend fun getAffiliateTreeFirstLine(): ResultWithStatus<MutableList<AffiliateTreeUser>>
    suspend fun getAffiliateTreeSpecificLine(
        userId: Int,
        line: Int
    ): ResultWithStatus<MutableList<AffiliateTreeUser>>
}