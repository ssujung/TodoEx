package com.sujungp.todoex.db

import androidx.room.TypeConverter
import com.sujungp.todoex.TodoStatus

/**
 * Created by sujung26 on 2019-10-14.
 */
class Converter {
    @TypeConverter
    fun statusToString(status: TodoStatus): String {
        return if (status == TodoStatus.ACTIVE) "ACTIVE" else "COMPLETE"
    }

    @TypeConverter
    fun stringToStatus(status: String): TodoStatus {
        return if (status == "ACTIVE") TodoStatus.ACTIVE else TodoStatus.COMPLETE
    }
}