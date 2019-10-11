package com.sujungp.todoex.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by sujung26 on 2019-08-29.
 */
open class BaseViewHolder<T>(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    protected var item: T? = null

    open fun onBind(item: T) {
        this.item = item
    }

    open fun onRecycled() {}
}