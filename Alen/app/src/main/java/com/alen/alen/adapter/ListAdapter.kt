package com.alen.alen.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alen.alen.R
import com.alen.alen.bean.ListBean
import com.alen.alen.widget.TestItemView

class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mData = ArrayList<ListBean>()

    fun setData(data: ArrayList<ListBean>) {
        mData.clear()
        mData.addAll(data)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_list_test, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mData[position].data.size > 1) 1 else 2
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val data = mData[p1]
        with(p0.itemView as TestItemView) {
            setData(data)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager as GridLayoutManager
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                return if (getItemViewType(p0) == 1) 1 else manager.spanCount
            }
             }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}