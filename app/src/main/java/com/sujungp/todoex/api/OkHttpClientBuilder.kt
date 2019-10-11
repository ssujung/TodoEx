package com.sujungp.todoex.api

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by sujung26 on 2019-09-05.
 */
class OkHttpClientBuilder(private val interceptor: LogLevelInterceptor) {

    fun build(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(interceptor.getInterceptor())
            .build()
    }
}