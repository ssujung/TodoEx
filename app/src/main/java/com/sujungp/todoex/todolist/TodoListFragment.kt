package com.sujungp.todoex.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.sujungp.todoex.MainActivity
import com.sujungp.todoex.R
import com.sujungp.todoex.addtodo.AddTodoFragment
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.isCompleted
import com.sujungp.todoex.setStatus
import com.sujungp.todoex.tododetail.TodoDetailFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_todo_list.*
import kotlinx.android.synthetic.main.item_todo_list.view.*
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoListFragment : Fragment() {

    private lateinit var adapter: TodoListAdapter
    private val viewModel: TodoListViewModel by viewModel()

    private val disposable = CompositeDisposable()
    private val onClickTodoSubject= PublishSubject.create<Pair<View, TodoItem?>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickTodoSubject.filter {
            it.second != null
        }.subscribeBy {
            (activity as MainActivity).replaceFragment(TodoDetailFragment.newInstance(it.second))
        }.also { disposable.add(it) }

        addTodo.clicks().subscribeBy {
            (activity as MainActivity).replaceFragment(AddTodoFragment())
        }

        initRecyclerView()
        observeFromViewModel()

        viewModel.getTodoList()

//        fetchTodoListFromServer()
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        adapter = TodoListAdapter(onClickTodoSubject)
            .apply {
                onClickStatus = {
                    viewModel.updateTodoStatus(it)
                }
            }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun observeFromViewModel() {
        viewModel.todoList.observe(viewLifecycleOwner, Observer { itemList ->
            itemList?.let {
                adapter.setList(it)
            } ?: run {
                toast(R.string.error_load)
            }
        })

        viewModel.updateResult.observe(viewLifecycleOwner, Observer {
            val id = it.first
            val isSuccess = it.second
            val status = it.third

            if (isSuccess) {
                adapter.items.find { item -> item.id == id }?.todoStatus = status
            } else {
                val holder = recyclerView.findViewHolderForAdapterPosition(id - 1)
                if (holder is TodoItemHolder) {
                    holder.itemView.todoStatus.setStatus(status.isCompleted(), needAnimation = true)
                }
                toast(R.string.error_status_update)
            }
        })
    }

    private fun fetchTodoListFromServer() {
//        val activity = activity as MainActivity
//        activity.api.getTodoList()
//            .subscribeOn(Schedulers.single())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onNext = { response ->
//                    response.data?.let { adapter.setList(it) }
//                },
//                onError = { e ->
//                    e.printStackTrace()
//                }
//            )
//            .also { disposable.add(it) }
    }
}