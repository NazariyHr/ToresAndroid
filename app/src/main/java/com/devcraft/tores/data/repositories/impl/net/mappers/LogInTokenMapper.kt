package com.devcraft.tores.data.repositories.impl.net.mappers

import com.devcraft.tores.data.repositories.impl.BaseRepositoryMapper
import com.devcraft.tores.data.repositories.impl.net.dto.LogInResponse
import com.devcraft.tores.entities.Token

class LogInTokenMapper : BaseRepositoryMapper<LogInResponse, Token>() {
    override fun mapDtoToEntity(dto: LogInResponse): Token {
        return with(dto) {
            Token(
                token = token
            )
        }
    }
}