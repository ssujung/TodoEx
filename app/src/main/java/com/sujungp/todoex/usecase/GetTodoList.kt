package com.sujungp.todoex.usecase

import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.data.TodoRepository
import io.reactivex.Single

/**
 * Created by sujung26 on 2019-10-11.
 */
class GetTodoList(private val todoRepository: TodoRepository) : UseCase<Unit, Single<List<TodoItem>>> {
    override fun execute(params: Unit?): Single<List<TodoItem>> {
        return todoRepository.getTodoList()
    }
}