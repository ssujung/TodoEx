package com.sujungp.todoex.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sujungp.todoex.TodoStatus
import com.sujungp.todoex.data.TODO_TABLE_NAME
import com.sujungp.todoex.data.TodoItem
import io.reactivex.Single

/**
 * Created by sujung26 on 2019-09-06.
 */
@Dao
interface TodoDao {
    @Query("SELECT * FROM $TODO_TABLE_NAME")
    fun getTodoList(): Single<List<TodoItem>>

    @Insert(onConflict = REPLACE)
    fun addTodo(todo: TodoItem?)

    @Update
    fun updateTodo(todo: TodoItem?)

    @Query("UPDATE $TODO_TABLE_NAME SET status = :status WHERE id = :id")
    fun updateTodoStatus(id: Int, status: TodoStatus)

    @Delete
    fun deleteTodo(todo: TodoItem?)
}