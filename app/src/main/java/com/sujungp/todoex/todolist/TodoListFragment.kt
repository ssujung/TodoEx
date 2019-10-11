package com.sujungp.todoex.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.sujungp.todoex.MainActivity
import com.sujungp.todoex.R
import com.sujungp.todoex.addtodo.AddTodoFragment
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.tododetail.TodoDetailFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_todo_list.*

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoListFragment : Fragment() {

    private lateinit var adapter: TodoListAdapter

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

        if (!::adapter.isInitialized) {
            adapter = TodoListAdapter(onClickTodoSubject)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        addTodo.clicks().subscribeBy {
            (activity as MainActivity).replaceFragment(AddTodoFragment())
        }

//        fetchTodoListFromServer()
        fetchTodoListFromDB()
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    private fun fetchTodoListFromServer() {
        val activity = activity as MainActivity
        activity.api.getTodoList()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { response ->
                    response.data?.let { adapter.setList(it) }
                },
                onError = { e ->
                    e.printStackTrace()
                }
            )
            .also { disposable.add(it) }
    }

    private fun fetchTodoListFromDB() {
        val activity = activity as MainActivity
        activity.todoDao.getTodoList()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess =  { itemList ->
                    itemList?.let { adapter.setList(it) }
                },
                onError =  { e ->
                    e.printStackTrace()
                }
            )
            .also { disposable.add(it) }
    }
}