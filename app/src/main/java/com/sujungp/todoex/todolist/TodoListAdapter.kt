package com.sujungp.todoex.todolist

import android.view.View
import android.view.ViewGroup
import com.sujungp.todoex.TodoStatus
import com.sujungp.todoex.base.BaseRecyclerViewAdapter
import com.sujungp.todoex.base.BaseViewHolder
import com.sujungp.todoex.data.TodoItem
import io.reactivex.subjects.Subject

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoListAdapter(private val onClickTodoSubject: Subject<Pair<View, TodoItem?>>) : BaseRecyclerViewAdapter<TodoItem>() {

    var onClickStatus: ((Pair<Int, TodoStatus>) -> Unit)? = null
    val items: List<TodoItem>
        get() = rawItems.map { it.data }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TodoItem> {
        return TodoItemHolder(parent, onClickTodoSubject, onClickStatus)
    }

    fun setList(list: List<TodoItem>) {
        setItemList(list, VIEW_TYPE_TODO)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        removeItem(position)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_TODO = 0
    }
}