package com.sujungp.todoex

import android.animation.ValueAnimator
import com.airbnb.lottie.LottieAnimationView

/**
 * Created by sujung26 on 2019-10-14.
 */
enum class TodoStatus {
    ACTIVE,
    COMPLETE;
}

fun TodoStatus?.isCompleted(): Boolean {
    return this == TodoStatus.COMPLETE
}

fun TodoStatus?.reverse(): TodoStatus {
    return if (this == TodoStatus.ACTIVE) TodoStatus.COMPLETE else TodoStatus.ACTIVE
}

fun LottieAnimationView.setStatusView(todoStatus: TodoStatus?, needAnimation: Boolean) {
    if (needAnimation) {
        if (todoStatus?.isCompleted() == true) {
            ValueAnimator.ofFloat(0f, 1f).apply {
                duration = SUBSCRIBE_ANIM_DURATION
                addUpdateListener { animation ->
                    this@setStatusView.progress = animation.animatedValue as Float
                }
            }.start()
        } else {
            ValueAnimator.ofFloat(1f, 0f).apply {
                duration = SUBSCRIBE_ANIM_DURATION
                addUpdateListener { animation ->
                    this@setStatusView.progress = animation.animatedValue as Float
                }
            }.start()
        }
    } else {
        this.progress = if (todoStatus?.isCompleted() == true) 1f else 0f
    }
}

const val SUBSCRIBE_ANIM_DURATION: Long = 700