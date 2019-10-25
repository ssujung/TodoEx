package com.sujungp.todoex.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sujungp.todoex.R
import com.sujungp.todoex.base.BaseRecyclerViewAdapter
import com.sujungp.todoex.base.BaseViewHolder
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.databinding.ItemTodoListBinding
import io.reactivex.subjects.Subject

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoListAdapter(
    private val viewModel: TodoListViewModel,
    private val onClickTodoSubject: Subject<Pair<View, TodoItem?>>
) : BaseRecyclerViewAdapter<TodoItem>(DiffCallback()) {

    val items: List<TodoItem?>
        get() = rawItems.map { it.data }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TodoItem> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTodoListBinding>(layoutInflater, R.layout.item_todo_list, parent, false)
        binding.viewModel = viewModel
        return TodoItemHolder(binding, onClickTodoSubject)
    }

    fun setList(list: List<TodoItem>?) {
        setItemList(list, VIEW_TYPE_TODO)
        notifyDataSetChanged()
    }

    fun update(item: TodoItem?) {
        updateItem(mapToBaseItem(item, VIEW_TYPE_TODO))
    }

    fun remove(position: Int) {
        removeItem(position)
        notifyDataSetChanged()
    }

    class DiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
            oldItem == newItem
    }

    companion object {
        const val VIEW_TYPE_TODO = 0

        @JvmStatic
        @BindingAdapter("app:items")
        fun RecyclerView.bindItems(items: List<TodoItem>?) {
            val adapter = adapter as TodoListAdapter
            adapter.setList(items)
        }

        @JvmStatic
        @BindingAdapter("app:updateItem")
        fun RecyclerView.updateItem(item: TodoItem?) {
            val adapter = adapter as TodoListAdapter
            adapter.update(item)
            adapter.submitList(adapter.items)
        }
    }
}