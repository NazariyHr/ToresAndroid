package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.R
import com.devcraft.tores.app.App
import com.devcraft.tores.data.repositories.contract.PartnersRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.BecomePartnerNetRequest
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.GetPartnersAndUserRequestsMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.PartnersApi
import com.devcraft.tores.entities.BecomePartnerRequest
import com.devcraft.tores.entities.Partner

class PartnersRepositoryImpl(
    private val partnersApi: PartnersApi,
    private val getPartnersAndUserRequestsMapper: GetPartnersAndUserRequestsMapper
) : BaseNetRepository(), PartnersRepository {

    override suspend fun getPartnersAndUserRequests(): ResultWithStatus<Pair<MutableList<Partner>, MutableList<BecomePartnerRequest>>> {
        return enqueueCallResultWithStatusSuspended(
            partnersApi.getPartnersAndUserRequests(),
            getPartnersAndUserRequestsMapper
        )
    }

    override suspend fun becomePartner(
        companyName: String,
        url: String,
        contacts: String,
        percent: String?,
        type: String?,
        additional: String?
    ): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            partnersApi.becomePartner(
                BecomePartnerNetRequest(
                    companyName, url, contacts, percent, type, additional
                )
            )
        ).changeStatusToErrorIf(App.instance.getString(R.string.error_already_have_unaccepted_partner_request)) {
            it.error?.message.orEmpty() == "exists"
        }
    }
}
