package com.sujungp.todoex.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sujungp.todoex.TodoStatus
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.reverse
import com.sujungp.todoex.usecase.GetTodoList
import com.sujungp.todoex.usecase.RemoveTodo
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
    private val updateStatus: UpdateTodoStatus,
    private val removeTodo: RemoveTodo
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private var _todoList: MutableLiveData<List<TodoItem>> = MutableLiveData()
    val todoList: LiveData<List<TodoItem>>
        get() = _todoList

    private var _updateStatus: MutableLiveData<TodoItem> = MutableLiveData()
    val updateItem: LiveData<TodoItem>
        get() = _updateStatus

    private var _removeResult: MutableLiveData<Boolean> = MutableLiveData()
    val removeResult: LiveData<Boolean>
        get() = _removeResult

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

    fun updateTodoStatus(id: Int, oldStatus: TodoStatus) {
        val newStatus = oldStatus.reverse()
        this.updateStatus.execute(Pair(id, newStatus))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    val item: TodoItem?  = _todoList.value?.find { it.id == id }
                    item?.todoStatus = newStatus
                    _updateStatus.value = item
                },
                onError = { e ->
                    e.printStackTrace()
                }
            ).also { disposable.add(it) }
    }

    fun removeTodoItem(todoItem: TodoItem?) {
        removeTodo.execute(todoItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _removeResult.value = true
                },
                onError = { e ->
                    _removeResult.value = false
                    e.printStackTrace()
                }
            ).also { disposable.add(it) }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}