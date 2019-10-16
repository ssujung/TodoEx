package com.sujungp.todoex.data

import com.sujungp.todoex.TodoStatus
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by sujung26 on 2019-10-11.
 */
interface TodoRepository {
    fun getTodoList(): Single<List<TodoItem>>
    fun addTodoItem(todoItem: TodoItem?): Completable
    fun updateTodoItem(todoItem: TodoItem?): Completable
    fun updateTodoStatus(id: Int, status: TodoStatus): Completable
    fun removeTodoItem(todoItem: TodoItem?): Completable
}