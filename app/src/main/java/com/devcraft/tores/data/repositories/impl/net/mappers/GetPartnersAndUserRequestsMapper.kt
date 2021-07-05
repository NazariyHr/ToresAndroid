package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetPartnersAndUserRequestsResponse
import com.devcraft.tores.entities.BecomePartnerRequest
import com.devcraft.tores.entities.Partner
import com.devcraft.tores.entities.PartnerStatus

class GetPartnersAndUserRequestsMapper :
    BaseRepositoryMapper<GetPartnersAndUserRequestsResponse, Pair<MutableList<Partner>, MutableList<BecomePartnerRequest>>>() {
    override fun mapDtoToEntity(dto: GetPartnersAndUserRequestsResponse): Pair<MutableList<Partner>, MutableList<BecomePartnerRequest>> {
        val partners = mutableListOf<Partner>()
        val requests = mutableListOf<BecomePartnerRequest>()
        dto.let {
            it.partners.forEach { p ->
                partners.add(
                    Partner(
                        p.companyName,
                        p.percent,
                        p.url,
                        PartnerStatus.parseStatus(p.status)
                    )
                )
            }
            it.requests.forEach { r ->
                requests.add(
                    BecomePartnerRequest(
                        r.createdAt,
                        r.companyName,
                        r.url,
                        PartnerStatus.parseStatus(r.status)
                    )
                )
            }
        }
        return partners to requests
    }
}
