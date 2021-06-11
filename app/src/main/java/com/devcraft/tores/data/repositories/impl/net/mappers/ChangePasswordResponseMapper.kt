package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.ChangePasswordResponse

class ChangePasswordResponseMapper : BaseRepositoryMapper<ChangePasswordResponse, String>() {
    override fun mapDtoToEntity(dto: ChangePasswordResponse): String {
        return dto.message
    }
}
