package com.sujungp.todoex.addtodo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.sujungp.todoex.MainActivity
import com.sujungp.todoex.R
import com.sujungp.todoex.data.TodoItem
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_todo.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * Created by sujung26 on 2019-09-03.
 */
class AddTodoFragment : Fragment() {

    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTodoDate.onClick {
            val calendar = Calendar.getInstance()
            val dp = DatePickerDialog(activity,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        addTodoDate.text = "$year/${month + 1}/$dayOfMonth"
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dp.datePicker.minDate = calendar.timeInMillis
            dp.show()
        }

        btnAddTodo.onClick {
            val desc = addTodoDesc.text.toString()
            val date = addTodoDate.text.toString()

            if (desc.isBlank() || date.isBlank()) {
                longToast("Todo Description and Date are Mandatory")
            } else {
//                val todoItem = TodoItem(0, desc, date, status)
//                addTodoToServer(todoItem)
                val todoItem = TodoItem().apply {
                    this.todoDesc = desc
                    this.todoTargetDate = date
                }
                addTodoToDB(todoItem)
            }
        }
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    private fun addTodoToServer(todoItem: TodoItem) {
        val jsonTodo = Gson().toJson(todoItem)
        val activity = activity as MainActivity

        activity.api.addTodo(jsonTodo)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { response ->
                    if (response.errorCode == 0) {
                        toast("Todo successfully saved")
                        activity.setResult(Activity.RESULT_OK)
                        activity.supportFragmentManager.popBackStack()
                    } else {
                        toast("Couldn't save Todo, please try again!!")
                    }
                },
                onError = { e ->
                    e.printStackTrace()
                }
            )
            .also { disposable.add(it) }
    }

    private fun addTodoToDB(todoItem: TodoItem) {
        val activity = activity as MainActivity

        Completable.fromAction { activity.todoDao.addTodo(todoItem) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    toast("Todo successfully saved")
                    activity.supportFragmentManager.popBackStack()
                },
                onError = { e ->
                    e.printStackTrace()
                    toast("Couldn't save Todo, please try again!!")
                }
            )
            .also { disposable.add(it) }
    }
}