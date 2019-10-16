package com.sujungp.todoex.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.sujungp.todoex.*
import com.sujungp.todoex.base.BaseViewHolder
import com.sujungp.todoex.data.TodoItem
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.item_todo_list.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoItemHolder(
    parent: ViewGroup,
    private val onClickTodoSubject: Subject<Pair<View, TodoItem?>>,
    private val onClickStatus: ((Pair<Int, TodoStatus>) -> Unit)?
) : BaseViewHolder<TodoItem>(LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list, parent, false)) {

    override fun onBind(item: TodoItem) {
        super.onBind(item)

        with(itemView) {
            todoTitle.text = item.todoTitle
            todoDesc.text = item.todoDesc
            todoDate.text = item.todoTargetDate
            todoStatus.setStatus(item.todoStatus.isCompleted(), needAnimation = false)

            todoStatus.onClick {
                val check = item.todoStatus.reverse()
                todoStatus.setStatus(check.isCompleted(), needAnimation = true)
                onClickStatus?.invoke(Pair(item.id, check))
            }

            this.clicks()
                .subscribeBy {
                    onClickTodoSubject.onNext(Pair(this, item))
                }
        }
    }
}