package com.sujungp.todoex.todolist

import android.view.View
import android.view.ViewGroup
import com.sujungp.todoex.base.BaseRecyclerViewAdapter
import com.sujungp.todoex.base.BaseViewHolder
import com.sujungp.todoex.data.TodoItem
import io.reactivex.subjects.Subject

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoListAdapter(private val onClickTodoSubject: Subject<Pair<View, TodoItem?>>) : BaseRecyclerViewAdapter<TodoItem>() {

    var onClickStatus: ((Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TodoItem> {
        return TodoItemHolder(parent, onClickTodoSubject, onClickStatus)
    }

    fun setList(list: List<TodoItem>) {
        setItemList(list, VIEW_TYPE_TODO)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_TODO = 0
    }
}