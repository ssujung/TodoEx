package com.sujungp.todoex.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujungp.todoex.TodoStatus
import kotlinx.android.parcel.Parcelize

/**
 * Created by sujung26 on 2019-08-29.
 */
@Entity(tableName = TODO_TABLE_NAME)
@Parcelize
data class TodoItem(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int,

        @ColumnInfo(name = "title")
        var todoTitle: String?,

        @ColumnInfo(name = "desc")
        var todoDesc: String?,

        @ColumnInfo(name = "date")
        var todoTargetDate: String?,

        @ColumnInfo(name = "status")
        var todoStatus: TodoStatus?

) : Parcelable {
        constructor(): this(0, "", "", "", TodoStatus.ACTIVE)
}

const val TODO_TABLE_NAME = "todo_table"