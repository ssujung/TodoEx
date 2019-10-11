package com.sujungp.todoex.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by sujung26 on 2019-08-29.
 */
open class BaseApiResponse(
        @SerializedName("error_code")
        val errorCode: Int,
        @SerializedName("error_message")
        val errorMessage: String
): Serializable