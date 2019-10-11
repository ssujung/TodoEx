package com.sujungp.todoex.usecase

/**
 * Created by sujung26 on 2019-10-11.
 */
interface UseCase<in Params, Result> {
    fun execute(params: Params? = null): Result
}