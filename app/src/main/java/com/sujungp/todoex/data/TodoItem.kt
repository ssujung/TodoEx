package com.sujungp.todoex.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujungp.todoex.TodoStatus
import java.io.Serializable

/**
 * Created by sujung26 on 2019-08-29.
 */
@Entity(tableName = TODO_TABLE_NAME)
data class TodoItem(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int?,

        @ColumnInfo(name = "title")
        var todoTitle: String?,

        @ColumnInfo(name = "desc")
        var todoDesc: String?,

        @ColumnInfo(name = "date")
        var todoTargetDate: String?,

        @ColumnInfo(name = "status")
        var todoStatus: TodoStatus?

): Serializable {
        constructor(): this(0, "", "", "", TodoStatus.ACTIVE)
}

const val TODO_TABLE_NAME = "todo_table"