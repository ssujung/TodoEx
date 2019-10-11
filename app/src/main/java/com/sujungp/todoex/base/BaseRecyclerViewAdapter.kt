package com.sujungp.todoex.base

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by sujung26 on 2019-08-29.
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var items: MutableList<BaseItem<T>> = mutableListOf()
    protected val rawItems: List<BaseItem<T>>
        get() = items

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(items[position].data)
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    protected fun setItemList(list: List<T>, viewType: Int = 0) {
        removeAll()
        items.addAll(map(list, viewType))
    }

    protected fun addItemList(list: List<T>, viewType: Int = 0) {
        items.addAll(map(list, viewType))
    }

    protected fun addItem(item: BaseItem<T>) {
        items.add(item)
    }

    protected fun removeItem(position: Int) {
        items.removeAt(position)
    }

    protected fun removeItem(item: T) {
        val index = items.indexOfFirst { it == item }

        if (index > -1) removeItem(index)
    }

    protected fun removeAll() {
        items.clear()
    }

    protected fun modifyItem(item: BaseItem<T>, position: Int) {
        items[position] = item
    }

    protected fun map(list: List<T>, viewType: Int): List<BaseItem<T>> {
        return list.map { mapToBaseItem(it, viewType) }
    }

    protected fun mapToBaseItem(item: T, viewType: Int): BaseItem<T> {
        return BaseItem(item, viewType)
    }
}