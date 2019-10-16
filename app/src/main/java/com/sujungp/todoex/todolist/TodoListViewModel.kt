package com.sujungp.todoex.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sujungp.todoex.TodoStatus
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.reverse
import com.sujungp.todoex.usecase.GetTodoList
import com.sujungp.todoex.usecase.UpdateTodoStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by sujung26 on 2019-10-11.
 */
class TodoListViewModel(
    private val getTodoList: GetTodoList,
    private val updateStatus: UpdateTodoStatus
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private var _todoList: MutableLiveData<List<TodoItem>> = MutableLiveData()
    val todoList: LiveData<List<TodoItem>>
        get() = _todoList

    private var _updateResult: MutableLiveData<Triple<Int, Boolean, TodoStatus>> = MutableLiveData()
    val updateResult: LiveData<Triple<Int, Boolean, TodoStatus>>
        get() = _updateResult

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
                    _todoList.value = null
                }
            ).also { disposable.add(it) }
    }

    fun updateTodoStatus(pair: Pair<Int, TodoStatus>) {
        updateStatus.execute(pair)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _updateResult.value = Triple(pair.first, true, pair.second)
                },
                onError = { e ->
                    _updateResult.value = Triple(pair.first, false, pair.second.reverse())
                    e.printStackTrace()
                }
            ).also { disposable.add(it) }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}