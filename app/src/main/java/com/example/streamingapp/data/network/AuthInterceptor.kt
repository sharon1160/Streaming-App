package com.example.streamingapp.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Token $TOKEN")
        return chain.proceed(request.build())
    }

    companion object {
        const val TOKEN = "Frviaa2Vj7h8B2e13d4gZ5bOPr6jTljzJL34dbml"
    }
}
