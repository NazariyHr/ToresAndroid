package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetTopupsAndWithdrawalsResponse
import com.devcraft.tores.entities.Currency
import com.devcraft.tores.entities.TopupsAndWithdrawalsData
import com.devcraft.tores.entities.TransactionStatus

class GetTopupsAndWithdrawalsMapper :
    BaseRepositoryMapper<GetTopupsAndWithdrawalsResponse, TopupsAndWithdrawalsData>() {
    override fun mapDtoToEntity(dto: GetTopupsAndWithdrawalsResponse): TopupsAndWithdrawalsData {
        dto.let {
            val transactions = dto.data.transactions.map { t ->
                TopupsAndWithdrawalsData.Transaction(
                    t.id,
                    t.createdAt,
                    TopupsAndWithdrawalsData.Transaction.Type.parse(t.type),
                    Currency.parseCurrency(t.currency),
                    t.amountInCurrency.toBigDecimal(),
                    t.amount,
                    TransactionStatus.parseStatus(t.status),
                    t.wallet,
                    t.remaining
                )
            }
            return TopupsAndWithdrawalsData(
                transactions,
                it.data.totalTopUps
            )
        }
    }
}
