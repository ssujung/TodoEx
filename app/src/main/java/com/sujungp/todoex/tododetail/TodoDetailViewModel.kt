package com.sujungp.todoex.tododetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.usecase.UpdateTodo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by sujung26 on 2019-10-15.
 */
class TodoDetailViewModel(
    private val update: UpdateTodo
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private var _updateResult: MutableLiveData<Boolean> = MutableLiveData()
    val updateResult: LiveData<Boolean>
        get() = _updateResult

    fun updateTodo(todoItem: TodoItem) {
        update.execute(todoItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _updateResult.value = true
                },
                onError = { e ->
                    _updateResult.value = false
                    e.printStackTrace()
                }
            ).also { disposable.add(it) }
    }
}