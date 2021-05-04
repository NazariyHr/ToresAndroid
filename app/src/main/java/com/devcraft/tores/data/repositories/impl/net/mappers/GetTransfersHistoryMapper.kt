package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetTransfersHistoryResponse
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.entities.TransactionStatus
import com.devcraft.tores.entities.TransfersHistoryData

class GetTransfersHistoryMapper :
    BaseRepositoryMapper<GetTransfersHistoryResponse, TransfersHistoryData>() {
    override fun mapDtoToEntity(dto: GetTransfersHistoryResponse): TransfersHistoryData {
        dto.let {
            val transactions = dto.data.transactions.map { t ->
                TransfersHistoryData.Transaction(
                    t.id,
                    t.amount,
                    t.login,
                    t.wallet,
                    BalanceType.parse(t.balance),
                    TransfersHistoryData.Transaction.Type.parse(t.type),
                    t.createdAt,
                    TransactionStatus.parseStatus(t.status)
                )
            }
            return TransfersHistoryData(
                transactions
            )
        }
    }
}
