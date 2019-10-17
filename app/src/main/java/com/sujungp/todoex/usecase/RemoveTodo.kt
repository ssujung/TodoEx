package com.sujungp.todoex.usecase

import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.data.TodoRepository
import io.reactivex.Completable

/**
 * Created by sujung26 on 2019-10-17.
 */
class RemoveTodo(private val repository: TodoRepository) : UseCase<TodoItem, Completable> {
    override fun execute(params: TodoItem?): Completable {
        return params?.let {
            repository.removeTodoItem(params)
        } ?: Completable.error(Throwable("RemoveTodo: parameter must not be null!!"))
    }
}