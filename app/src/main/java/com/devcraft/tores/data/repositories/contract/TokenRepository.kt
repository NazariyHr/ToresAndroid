package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.entities.Token

interface TokenRepository {
    fun saveToken(token: Token)
    fun getToken(): Token
    fun deleteToken()
}