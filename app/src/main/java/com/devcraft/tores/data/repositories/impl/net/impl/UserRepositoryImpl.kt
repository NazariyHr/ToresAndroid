package com.devcraft.tores.data.repositories.impl.net.impl

import com.devcraft.tores.R
import com.devcraft.tores.app.App
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.contract.commonResults.ResultStatus
import com.devcraft.tores.data.repositories.contract.commonResults.ResultWithStatus
import com.devcraft.tores.data.repositories.impl.net.dto.ChangePasswordRequest
import com.devcraft.tores.data.repositories.impl.net.dto.LogInRequest
import com.devcraft.tores.data.repositories.impl.net.dto.SetFinancePasswordRequest
import com.devcraft.tores.data.repositories.impl.net.impl.base.BaseNetRepository
import com.devcraft.tores.data.repositories.impl.net.mappers.ChangePasswordResponseMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.GetUserMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.LogInTokenMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.UserApi
import com.devcraft.tores.entities.User

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val tokenRepository: TokenRepository,
    private val logInTokenMapper: LogInTokenMapper,
    private val getUserMapper: GetUserMapper,
    private val changePasswordResponseMapper: ChangePasswordResponseMapper
) : BaseNetRepository(), UserRepository {

    override suspend fun login(
        email: String,
        password: String,
        token: String
    ): ResultStatus {
        return enqueueCallResultWithStatusSuspended(
            userApi.login(LogInRequest(email, password, token)),
            logInTokenMapper,
            true,
            App.instance.getString(R.string.error_token_is_empty)
        ).changeStatusToErrorIf(App.instance.getString(R.string.error_token_is_empty)) {
            it == null || it.token.isEmpty()
        }
            .apply {
                val t = data?.token
                if (!t.isNullOrEmpty()) {
                    tokenRepository.saveToken(data!!)
                }
            }.status
    }

    override suspend fun getUser(): ResultWithStatus<User> {
        return enqueueCallResultWithStatusSuspended(
            userApi.getUser(),
            getUserMapper,
            true,
            App.instance.getString(R.string.error_user_is_null)
        )
    }

    override suspend fun changePassword(
        oldPass: String,
        newPass: String,
        newPassConfirm: String
    ): ResultWithStatus<String> {
        return enqueueCallResultWithStatusSuspended(
            userApi.changePassword(
                ChangePasswordRequest(oldPass, newPass, newPassConfirm)
            ),
            changePasswordResponseMapper
        )
    }

    override suspend fun setFinancePassword(pass: String, passConfirm: String): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            userApi.setFinancePassword(
                SetFinancePasswordRequest(pass, passConfirm)
            )
        )
    }

    override suspend fun removeFinancePassword(): ResultStatus {
        return enqueueCallOnlyStatusSuspended(
            userApi.removeFinancePassword()
        )
    }
}
