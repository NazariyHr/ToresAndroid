package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetMiningHistoryResponse
import com.devcraft.tores.entities.BalanceType
import com.devcraft.tores.entities.MiningHistoryData
import com.devcraft.tores.entities.TransactionStatus

class GetMiningHistoryMapper : BaseRepositoryMapper<GetMiningHistoryResponse, MiningHistoryData>() {
    override fun mapDtoToEntity(dto: GetMiningHistoryResponse): MiningHistoryData {
        dto.let {
            val transactions = dto.data.transactions.map { t ->
                MiningHistoryData.Transaction(
                    t.id,
                    t.createdAt,
                    MiningHistoryData.Transaction.Type.parse(t.type),
                    BalanceType.parse(t.balance),
                    t.amount,
                    t.timeLeft,
                    TransactionStatus.parseStatus(t.status)
                )
            }
            return MiningHistoryData(
                transactions,
                it.data.myDeposit
            )
        }
    }
}
