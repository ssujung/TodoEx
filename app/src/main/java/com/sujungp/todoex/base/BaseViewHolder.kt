package com.sujungp.todoex.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sujungp.todoex.BR

/**
 * Created by sujung26 on 2019-08-29.
 */
abstract class BaseViewHolder<T>(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    open fun bind(item: T?) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }

    open fun onRecycled() {}
}