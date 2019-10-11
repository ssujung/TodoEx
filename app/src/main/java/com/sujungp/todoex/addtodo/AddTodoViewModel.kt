package com.sujungp.todoex.addtodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.usecase.AddTodo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by sujung26 on 2019-10-11.
 */
class AddTodoViewModel(
    private val addTodo: AddTodo
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private var _addSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val addSuccess: LiveData<Boolean>
        get() = _addSuccess

    fun addTodoItem(todoItem: TodoItem) {
        addTodo.execute(todoItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _addSuccess.value = true
                },
                onError = { e ->
                    _addSuccess.value = false
                    e.printStackTrace()
                }
            ).also { disposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}