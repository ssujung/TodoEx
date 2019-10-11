package com.sujungp.todoex.data

import com.sujungp.todoex.db.TodoDao
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by sujung26 on 2019-10-11.
 */
class TodoRepositoryImpl(
    private val dbDao: TodoDao
) : TodoRepository {
    override fun getTodoList(): Single<List<TodoItem>> {
        return dbDao.getTodoList()
    }

    override fun addTodoItem(todoItem: TodoItem?): Completable {
        return Completable.fromAction { dbDao.addTodo(todoItem) }
    }

    override fun updateTodoStatus(status: Boolean): Completable {
        return Completable.fromAction { /*dbDao.updateTodoStatus(status) */}
    }

    override fun removeTodoItem(todoItem: TodoItem?): Completable {
        return Completable.fromAction { }
    }
}