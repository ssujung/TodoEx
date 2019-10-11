package com.sujungp.todoex.api

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by sujung26 on 2019-08-30.
 */
class LogLevelInterceptor(private val logLevel: LogLevel = LogLevel.LOG_REQ_RES_BODY_HEADERS) {

    fun getInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (logLevel) {
                LogLevel.LOG_NOT_NEEDED -> HttpLoggingInterceptor.Level.NONE
                LogLevel.LOG_REQ_RES -> HttpLoggingInterceptor.Level.BASIC
                LogLevel.LOG_REQ_RES_BODY_HEADERS -> HttpLoggingInterceptor.Level.BODY
                LogLevel.LOG_REQ_RES_HEADERS_ONLY -> HttpLoggingInterceptor.Level.HEADERS
            }
        }
    }
}