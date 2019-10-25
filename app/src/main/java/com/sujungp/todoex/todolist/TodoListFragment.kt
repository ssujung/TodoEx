package com.sujungp.todoex.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.clicks
import com.sujungp.todoex.MainActivity
import com.sujungp.todoex.R
import com.sujungp.todoex.addtodo.AddTodoFragment
import com.sujungp.todoex.data.TodoItem
import com.sujungp.todoex.databinding.FragmentTodoListBinding
import com.sujungp.todoex.tododetail.TodoDetailFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_todo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by sujung26 on 2019-08-29.
 */
class TodoListFragment : Fragment() {

    private val vm: TodoListViewModel by viewModel()
    private lateinit var adapter: TodoListAdapter
    private lateinit var binding: FragmentTodoListBinding

    private val disposable = CompositeDisposable()
    private val onClickTodoSubject= PublishSubject.create<Pair<View, TodoItem?>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)
        binding.apply {
            this.viewModel = vm
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
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
        binding.viewModel?.getTodoList()

//        fetchTodoListFromServer()
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        adapter = TodoListAdapter(vm, onClickTodoSubject)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        // swipe to delete item
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.items[position]
                adapter.remove(position)
                binding.viewModel?.removeTodoItem(item)
                showTextIfEmptyList(adapter.itemCount < 1)
            }
        }).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    private fun showTextIfEmptyList(isEmptyList: Boolean) {
        if (isEmptyList) {
            emptyListTv.visibility = View.VISIBLE
        } else {
            emptyListTv.visibility = View.INVISIBLE
        }
    }

    private fun observeFromViewModel() {
//        viewModel.updateResult.observe(viewLifecycleOwner, Observer {
//            val id = it.first
//            val isSuccess = it.second
//            val status = it.third
//
//            if (isSuccess) {
//                adapter.items.find { item -> item.id == id }?.todoStatus = status
//            } else {
//                val holder = recyclerView.findViewHolderForAdapterPosition(id - 1)
//                if (holder is TodoItemHolder) {
//                    holder.itemView.todoStatus.setStatusView(status, needAnimation = true)
//                }
//                toast(R.string.error_status_update)
//            }
//        })
//
//        viewModel.removeResult.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                toast(R.string.success_remove)
//            } else {
//                toast(R.string.error_remove)
//            }
//        })
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