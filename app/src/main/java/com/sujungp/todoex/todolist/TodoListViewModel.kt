package com.sujungp.todoex.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.usecase.GetTodoList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by sujung26 on 2019-10-11.
 */
class TodoListViewModel(
    private val getTodoList: GetTodoList
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private var _todoList: MutableLiveData<List<TodoItem>> = MutableLiveData()
    val todoList: LiveData<List<TodoItem>>
        get() = _todoList

    fun getTodoList() {
        getTodoList.execute()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { itemList ->
                    itemList?.let {
                        _todoList.value = it
                    }
                },
                onError = { e ->
                    e.printStackTrace()
                }
            ).also { disposable.add(it) }
    }

    fun updateTodoStatus() {

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}