package com.sujungp.todoex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sujungp.todoex.todolist.TodoListFragment

class MainActivity : AppCompatActivity() {

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

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().run {
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commitAllowingStateLoss()
        }
    }
}
