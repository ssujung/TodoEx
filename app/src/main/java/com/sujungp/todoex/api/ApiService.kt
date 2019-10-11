package com.sujungp.todoex.api

import com.sujungp.todoex.Constants
import com.sujungp.todoex.base.BaseApiResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by sujung26 on 2019-08-29.
 */
interface ApiService {
    @POST(Constants.GET_TODO_LIST)
    fun getTodoList(): Observable<GetTodoListApiResponse>

    @POST(Constants.ADD_TODO)
    fun addTodo(@Body todo: String): Observable<BaseApiResponse>

    @POST(Constants.EDIT_TODO)
    fun editTodo(@Body todo: String): Observable<BaseApiResponse>
}