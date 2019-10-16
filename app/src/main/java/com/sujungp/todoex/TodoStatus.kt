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

fun TodoStatus.isCompleted(): Boolean {
    return this == TodoStatus.COMPLETE
}

fun TodoStatus.reverse(): TodoStatus {
    return if (this == TodoStatus.ACTIVE) TodoStatus.COMPLETE else TodoStatus.ACTIVE
}

fun LottieAnimationView.setStatus(isCompleted: Boolean, needAnimation: Boolean) {
    if (needAnimation) {
        if (isCompleted) {
            ValueAnimator.ofFloat(0f, 1f).apply {
                duration = SUBSCRIBE_ANIM_DURATION
                addUpdateListener { animation ->
                    this@setStatus.progress = animation.animatedValue as Float
                }
            }.start()
        } else {
            ValueAnimator.ofFloat(1f, 0f).apply {
                duration = SUBSCRIBE_ANIM_DURATION
                addUpdateListener { animation ->
                    this@setStatus.progress = animation.animatedValue as Float
                }
            }.start()
        }
    } else {
        this.progress = if (isCompleted) 1f else 0f
    }
}

fun LottieAnimationView.getStatus(): TodoStatus {
    return if (this.progress == 1f) TodoStatus.COMPLETE else TodoStatus.ACTIVE
}

const val SUBSCRIBE_ANIM_DURATION: Long = 700