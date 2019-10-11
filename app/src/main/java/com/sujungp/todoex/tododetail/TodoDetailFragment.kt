package com.sujungp.todoex.tododetail

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.view.clicks
import com.sujungp.todoex.MainActivity
import com.sujungp.todoex.MainActivity.Companion.TODO_ITEM
import com.sujungp.todoex.R
import com.sujungp.todoex.data.TodoItem
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_todo_detail.*
import kotlinx.android.synthetic.main.item_todo_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * Created by sujung26 on 2019-09-09.
 */
class TodoDetailFragment : Fragment() {

    private val disposable = CompositeDisposable()
    private var todoItem: TodoItem? = null
    private var isEditing = false

    companion object {
        fun newInstance(todoItem: TodoItem?): TodoDetailFragment {
            val args = Bundle().apply {
                putSerializable(TODO_ITEM, todoItem)
            }
            return TodoDetailFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            todoItem = it.getSerializable(TODO_ITEM) as? TodoItem
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).setSupportActionBar(toolbar)
        return inflater.inflate(R.layout.fragment_todo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData(todoItem)

        fabEditTodo.clicks().subscribeBy {
            if (isEditing) {
                saveTodoToDB()
            } else {
                startEdit()
            }
        }.also { disposable.add(it) }
    }

    private fun setData(item: TodoItem?) {
        item?.let {
            txtID.text = it.id.toString()
            txtDesc.setText(it.todoDesc)
            txtDate.text = it.todoTargetDate
            todoStatus.progress = if (it.status) 1f else 0f
        }
    }

    private fun saveTodoToDB() {
        todoItem?.todoDesc = txtDesc.text.toString()
        todoItem?.status = todoStatus.progress == 1f
        todoItem?.todoTargetDate = txtDate.text.toString()

        val activity = activity as MainActivity

        Completable.fromAction { activity.todoDao.editTodo(todoItem) }
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

    @SuppressLint("SetTextI18n")
    private fun startEdit() {
        isEditing = true
        txtDesc.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT

        txtDate.onClick {
            val calendar = Calendar.getInstance()
            val dp = DatePickerDialog(activity,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    txtDate.text = "$year/${month + 1}/$dayOfMonth"
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            dp.datePicker.minDate = calendar.timeInMillis
            dp.show()
        }

        txtDesc.requestFocus()

        fabEditTodo.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_save))
    }
}