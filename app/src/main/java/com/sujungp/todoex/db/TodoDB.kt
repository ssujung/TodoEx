package com.sujungp.todoex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sujungp.todoex.data.TodoItem

/**
 * Created by sujung26 on 2019-09-06.
 */
@Database(version = 1, entities = [TodoItem::class], exportSchema = true)
abstract class TodoDB : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}