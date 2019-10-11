package com.sujungp.todoex.data

import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by sujung26 on 2019-10-11.
 */
interface TodoRepository {
    fun getTodoList(): Single<List<TodoItem>>
    fun addTodoItem(todoItem: TodoItem?): Completable
    fun updateTodoStatus(status: Boolean = false): Completable
    fun removeTodoItem(todoItem: TodoItem?): Completable
}