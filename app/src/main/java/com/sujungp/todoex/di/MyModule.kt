package com.sujungp.todoex.di

import androidx.room.Room
import com.sujungp.todoex.api.LogLevelInterceptor
import com.sujungp.todoex.api.OkHttpClientBuilder
import com.sujungp.todoex.api.RetrofitBuilder
import com.sujungp.todoex.db.TodoDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by sujung26 on 2019-08-30.
 */

val networkModule = module {
    single { LogLevelInterceptor() }
    single { OkHttpClientBuilder(get()).build() }
    single { RetrofitBuilder(get()).getApiService() }
}

val dbModule = module {
    single { Room.databaseBuilder(androidApplication(), TodoDB::class.java, "todo_db").build() }
    single { get<TodoDB>().todoDao() }
}

val appModule= listOf(networkModule, dbModule)