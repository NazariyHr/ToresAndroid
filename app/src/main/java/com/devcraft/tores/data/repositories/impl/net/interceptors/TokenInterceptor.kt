package com.devcraft.tores.data.repositories.impl.net.interceptors

import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenRepository.getToken().token
        val original = chain.request()
        val lastSegmentOfLogin = ApiConstants.API_ENDPOINT_LOGIN.split("/").last()
        if (original.url().encodedPath().contains(lastSegmentOfLogin) &&
            original.method() == "post"
        ) {
            return chain.proceed(original)
        }

        try {
            val originalHttpUrl = original.url()
            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .url(originalHttpUrl)
            val request = requestBuilder.build()
            return chain.proceed(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return chain.proceed(original)
    }
}