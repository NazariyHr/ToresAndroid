package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetCurrencyRatesResponse
import com.devcraft.tores.entities.Currency
import com.devcraft.tores.entities.CurrencyRatesInfo

class GetCurrencyRatesMapper : BaseRepositoryMapper<GetCurrencyRatesResponse, CurrencyRatesInfo>() {
    override fun mapDtoToEntity(dto: GetCurrencyRatesResponse): CurrencyRatesInfo {
        dto.let {
            return CurrencyRatesInfo(
                mutableMapOf(
                    Currency.BITCOIN to it.data.btcRate,
                    Currency.ETHEREUM to it.data.ethRate,
                    Currency.TRON to it.data.trxRate,
                    Currency.LITECOIN to it.data.ltcRate
                )
            )
        }
    }
}
