package com.sujungp.todoex.api

import com.sujungp.todoex.base.BaseApiResponse
import com.sujungp.todoex.data.TodoItem

/**
 * Created by sujung26 on 2019-08-29.
 */
open class GetTodoListApiResponse(
        errorCode: Int,
        errorMessage: String,
        val data: List<TodoItem>?
): BaseApiResponse(errorCode, errorMessage)