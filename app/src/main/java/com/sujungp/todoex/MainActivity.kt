package com.sujungp.todoex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sujungp.todoex.api.ApiService
import com.sujungp.todoex.db.TodoDB
import com.sujungp.todoex.db.TodoDao
import com.sujungp.todoex.todolist.TodoListFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val api: ApiService by inject()

    private val todoDB: TodoDB by inject()
    val todoDao: TodoDao by inject()

    companion object {
        const val TODO_ITEM = "todo_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment_container, TodoListFragment())
            commit()
        }
    }

    override fun onDestroy() {
        todoDB.close()
        super.onDestroy()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commitAllowingStateLoss()
        }
    }
}
