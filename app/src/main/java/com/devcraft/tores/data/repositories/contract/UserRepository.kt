package com.devcraft.tores.data.repositories.contract

import com.devcraft.tores.data.repositories.contract.common_results.ResultStatus
import com.devcraft.tores.data.repositories.contract.common_results.ResultWithStatus

interface UserRepository {
    suspend fun login(
        email: String,
        password: String,
        token: String
    ): ResultStatus

    //suspend fun getUser(): ResultWithStatus<Any>
}