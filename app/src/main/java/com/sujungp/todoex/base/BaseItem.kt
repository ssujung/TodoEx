package com.sujungp.todoex.base

/**
 * Created by sujung26 on 2019-10-10.
 */
data class BaseItem<T>(
    var data: T?,
    var viewType: Int = 0
)