package com.sujungp.todoex.usecase

import com.sujungp.todoex.TodoStatus
import com.sujungp.todoex.data.TodoRepository
import io.reactivex.Completable

/**
 * Created by sujung26 on 2019-10-14.
 */
class UpdateTodoStatus(private val todoRepository: TodoRepository) : UseCase<Pair<Int, TodoStatus>, Completable> {
    override fun execute(params: Pair<Int, TodoStatus>?): Completable {
        return params?.let {
            todoRepository.updateTodoStatus(it.first, it.second)
        } ?: Completable.error(Throwable("UpdateStatus: parameter must not be null!!"))
    }
}