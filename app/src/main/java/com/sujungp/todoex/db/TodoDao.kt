package com.sujungp.todoex.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sujungp.todoex.data.TodoItem
import io.reactivex.Single

/**
 * Created by sujung26 on 2019-09-06.
 */
@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getTodoList(): Single<List<TodoItem>>

    @Insert(onConflict = REPLACE)
    fun addTodo(todo: TodoItem?)

    @Update
    fun updateTodo(todo: TodoItem?)

//    @Update
//    fun updateTodoStatus(status: Boolean)

    @Delete
    fun deleteTodo(todo: TodoItem?)
}