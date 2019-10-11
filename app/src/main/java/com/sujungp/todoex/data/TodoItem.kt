package com.sujungp.todoex.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by sujung26 on 2019-08-29.
 */
@Entity(tableName = "todo")
data class TodoItem(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int,

        @ColumnInfo(name = "desc")
        var todoDesc: String,

        @ColumnInfo(name = "date")
        var todoTargetDate: String,

        @ColumnInfo(name = "status")
        var status: Boolean
): Serializable {
        constructor(): this(0, "", "", false)
}