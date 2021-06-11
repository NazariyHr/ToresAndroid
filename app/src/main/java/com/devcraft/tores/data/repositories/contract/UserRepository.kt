package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.entities.User

interface UserRepository {
    suspend fun login(
        email: String,
        password: String,
        token: String
    ): ResultStatus

    suspend fun getUser(): ResultWithStatus<User>

    suspend fun changePassword(
        oldPass: String,
        newPass: String,
        newPassConfirm: String
    ): ResultWithStatus<String>

    suspend fun setFinancePassword(pass: String, passConfirm: String): ResultStatus

    suspend fun removeFinancePassword(): ResultStatus
}