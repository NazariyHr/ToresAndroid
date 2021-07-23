package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetSharesHistoryResponse
import com.devcraft.tores.entities.ShareTransfer
import com.devcraft.tores.entities.ShareTransferStatus
import com.devcraft.tores.entities.ShareTransferType

class GetSharesHistoryMapper :
    BaseRepositoryMapper<GetSharesHistoryResponse, MutableList<ShareTransfer>>() {
    override fun mapDtoToEntity(dto: GetSharesHistoryResponse): MutableList<ShareTransfer> {
        val result = mutableListOf<ShareTransfer>()
        dto.data.transactions.forEach { t ->
            result.add(
                ShareTransfer(
                    t.id,
                    ShareTransferType.parse(t.type),
                    ShareTransferStatus.parseStatus(t.status),
                    t.createdAt,
                    t.login,
                    t.amount,
                    t.sharesAmount
                )
            )
        }
        return result
    }
}
