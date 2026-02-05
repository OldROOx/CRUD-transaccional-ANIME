package com.example.gael_somer_anime.core.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        SessionManager.fetchToken()?.let {
            requestBuilder.addHeader("Authorization", it)
        }

        return chain.proceed(requestBuilder.build())
    }
}