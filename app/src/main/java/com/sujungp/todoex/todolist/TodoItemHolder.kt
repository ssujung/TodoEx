package com.sujungp.todoex.todolist

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.sujungp.todoex.R
import com.sujungp.todoex.base.BaseViewHolder
import com.sujungp.todoex.data.TodoItem
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.item_todo_list.view.*

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoItemHolder(
    parent: ViewGroup,
    private val onClickTodoSubject: Subject<Pair<View, TodoItem?>>
) : BaseViewHolder<TodoItem>(LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list, parent, false)) {

    override fun onBind(item: TodoItem) {
        super.onBind(item)

        with(itemView) {
            txtID.text = item.id.toString()
            txtDesc.text = item.todoDesc
            txtDate.text = item.todoTargetDate
            todoStatus.progress = if (item.status) 1f else 0f
//            setTodoStatus(item.status)

            this.clicks()
                .subscribeBy {
                    onClickTodoSubject.onNext(Pair(this, item))
                }
        }
    }

    private fun setTodoStatus(status: Boolean) {
        if (status) {
            ValueAnimator.ofFloat(0f, 0.6f).apply {
                duration = SUBSCRIBE_ANIM_DURATION
                addUpdateListener { animation ->
                    itemView.todoStatus.progress = animation.animatedValue as Float
                }
            }.start()
        } else {
            ValueAnimator.ofFloat(0.6f, 1f).apply {
                duration = SUBSCRIBE_ANIM_DURATION
                addUpdateListener { animation ->
                    itemView.todoStatus.progress = animation.animatedValue as Float
                }
            }.start()
        }
    }

    companion object {
        const val SUBSCRIBE_ANIM_DURATION: Long = 700
    }
}