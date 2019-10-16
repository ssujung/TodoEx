package com.sujungp.todoex.di

import androidx.room.Room
import com.sujungp.todoex.addtodo.AddTodoViewModel
import com.sujungp.todoex.api.LogLevelInterceptor
import com.sujungp.todoex.api.OkHttpClientBuilder
import com.sujungp.todoex.api.RetrofitBuilder
import com.sujungp.todoex.data.TodoRepository
import com.sujungp.todoex.data.TodoRepositoryImpl
import com.sujungp.todoex.db.TodoDB
import com.sujungp.todoex.tododetail.TodoDetailViewModel
import com.sujungp.todoex.todolist.TodoListViewModel
import com.sujungp.todoex.usecase.AddTodo
import com.sujungp.todoex.usecase.GetTodoList
import com.sujungp.todoex.usecase.UpdateTodo
import com.sujungp.todoex.usecase.UpdateTodoStatus
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
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

val todoModule = module {
    // Repository
    factory { TodoRepositoryImpl(get()) as TodoRepository }

    // Usecase
    factory { GetTodoList(get()) }
    factory { AddTodo(get()) }
    factory { UpdateTodo(get()) }
    factory { UpdateTodoStatus(get()) }

    // ViewModel
    viewModel { TodoListViewModel(get(), get()) }
    viewModel { AddTodoViewModel(get()) }
    viewModel { TodoDetailViewModel(get()) }
}

val appModule= listOf(networkModule, dbModule, todoModule)