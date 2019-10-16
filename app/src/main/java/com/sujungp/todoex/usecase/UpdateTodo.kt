package com.sujungp.todoex.usecase

import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.data.TodoRepository
import io.reactivex.Completable

/**
 * Created by sujung26 on 2019-10-15.
 */
class UpdateTodo(val repository: TodoRepository) : UseCase<TodoItem, Completable> {
    override fun execute(params: TodoItem?): Completable {
        return repository.updateTodoItem(params)
    }
}