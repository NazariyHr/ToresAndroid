package com.devcraft.tores.data.repositories.impl.prefs.impl

import android.content.SharedPreferences
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.entities.Token

class TokenRepositoryPrefsImpl(private val sp: SharedPreferences) : TokenRepository {
    override fun saveToken(token: Token) {
        sp.edit().putString("token", token.token).apply()
    }

    override fun getToken(): Token {
        return Token(sp.getString("token", "") ?: "")
    }

    override fun deleteToken() {
        sp.edit().remove("token").apply()
    }
}