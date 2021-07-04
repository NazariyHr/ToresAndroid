package com.devcraft.tores.data.repositories.impl.net.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ContentTypeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        try {
            val originalHttpUrl = original.url()
            val requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(originalHttpUrl)
            val request = requestBuilder.build()
            return chain.proceed(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return chain.proceed(original)
    }
}