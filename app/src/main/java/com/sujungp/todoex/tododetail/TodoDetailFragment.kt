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
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding2.view.clicks
import com.sujungp.todoex.*
import com.sujungp.todoex.MainActivity.Companion.TODO_ITEM
import com.sujungp.todoex.data.TodoItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_todo_detail.*
import kotlinx.android.synthetic.main.item_todo_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Created by sujung26 on 2019-09-09.
 */
class TodoDetailFragment : Fragment() {

    private val viewModel: TodoDetailViewModel by viewModel()
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

        setData()
        observeFromViewModel()

        fabEditTodo.clicks().subscribeBy {
            if (isEditing) {
                saveTodoToDB()
            } else {
                startEdit()
            }
        }.also { disposable.add(it) }
    }

    private fun setData() {
        todoItem?.let {
            todoTitle.setText(it.todoTitle)
            todoDesc.setText(it.todoDesc)
            todoDate.text = it.todoTargetDate
            todoStatus.setStatusView(it.todoStatus, needAnimation = false)
        }
    }

    private fun observeFromViewModel() {
        viewModel.updateResult.observe(viewLifecycleOwner, Observer {
            if (it) {
                toast(R.string.success_update)
                activity?.supportFragmentManager?.popBackStack()
            } else {
                toast(R.string.error_status_update)
            }
        })
    }

    private fun saveTodoToDB() {
        val item = TodoItem(
            todoItem?.id ?: 1,
            todoTitle.text.toString(),
            todoDesc.text.toString(),
            todoDate.text.toString(),
            todoItem?.todoStatus ?: TodoStatus.ACTIVE
        )
        viewModel.updateTodo(item)
    }

    @SuppressLint("SetTextI18n")
    private fun startEdit() {
        isEditing = true
        todoTitle.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT
        todoDesc.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT

        todoStatus.onClick {
            todoItem?.todoStatus = todoItem?.todoStatus?.reverse()
            todoStatus.setStatusView(todoItem?.todoStatus, needAnimation = true)
        }

        todoDate.onClick {
            val calendar = Calendar.getInstance()
            val dp = DatePickerDialog(activity,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    todoDate.text = "$year/${month + 1}/$dayOfMonth"
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            )
            dp.datePicker.minDate = calendar.timeInMillis
            dp.show()
        }

        todoTitle.run {
            isFocusableInTouchMode = true
            requestFocus()
        }
        todoDesc.run {
            isFocusableInTouchMode = true
            requestFocus()
        }

        fabEditTodo.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_save))
    }
}