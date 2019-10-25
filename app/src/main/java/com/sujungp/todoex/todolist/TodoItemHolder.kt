package com.sujungp.todoex.todolist

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.jakewharton.rxbinding2.view.clicks
import com.sujungp.todoex.TodoStatus
import com.sujungp.todoex.base.BaseViewHolder
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.databinding.ItemTodoListBinding
import com.sujungp.todoex.setStatusView
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.Subject

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoItemHolder(
    binding: ItemTodoListBinding,
    private val onClickTodoSubject: Subject<Pair<View, TodoItem?>>
) : BaseViewHolder<TodoItem>(binding) {

    @SuppressLint("CheckResult")
    override fun bind(item: TodoItem?) {
        super.bind(item)

        itemView.clicks()
            .subscribeBy {
                onClickTodoSubject.onNext(Pair(itemView, item))
            }
    }
}

@BindingAdapter("setStatus")
fun setStatus(view: LottieAnimationView, todoStatus: TodoStatus?) {
    view.setStatusView(todoStatus, false)
}