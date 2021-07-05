package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.entities.BecomePartnerRequest
import com.devcraft.tores.entities.Partner

interface PartnersRepository {
    suspend fun getPartnersAndUserRequests(): ResultWithStatus<Pair<MutableList<Partner>, MutableList<BecomePartnerRequest>>>
    suspend fun becomePartner(
        companyName: String,
        url: String,
        contacts: String,
        percent: String?,
        type: String?,
        additional: String?
    ): ResultStatus
}
