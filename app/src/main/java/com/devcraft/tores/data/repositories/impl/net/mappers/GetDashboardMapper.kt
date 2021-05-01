package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.GetDashboardResponse
import com.devcraft.tores.entities.ProfitsAndRegisters

class GetDashboardMapper : BaseRepositoryMapper<GetDashboardResponse, ProfitsAndRegisters>() {
    override fun mapDtoToEntity(dto: GetDashboardResponse): ProfitsAndRegisters {
        dto.let {
            val profits = mutableListOf<ProfitsAndRegisters.Profit>()
            val registers = mutableListOf<ProfitsAndRegisters.Registered>()

            it.data.lastProfits.forEach { p ->
                profits.add(
                    ProfitsAndRegisters.Profit(
                        p.createdAtDate,
                        p.createdAtTime,
                        p.profit
                    )
                )
            }

            it.data.lastRegisters.forEach { r ->
                registers.add(
                    ProfitsAndRegisters.Registered(
                        r.login,
                        r.createdAtDate,
                        r.createdAtTime
                    )
                )
            }

            return ProfitsAndRegisters(profits, registers)
        }
    }
}