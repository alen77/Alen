package com.alen.alen.kotlin

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alen.alen.R
import com.alen.alen.activity.BaseActivity
import com.example.libframework.FrameActivity
import kotlinx.android.synthetic.main.activity_grid_list.*
import kotlinx.android.synthetic.main.item_grid.view.*

class GridListActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_grid_list
    }

    override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    override fun getMenuId(): Int {
        return FrameActivity.DEFAULT
    }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(this, 5)
        var adapter = ListAdapter()
        recyclerView.adapter = adapter
        var callback : ItemTouchHelper.Callback = OnItemCallbackHelp()
        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
    }

    class OnItemCallbackHelp : ItemTouchHelper.Callback() {
        override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {

            return makeMovementFlags(
                    ItemTouchHelper.LEFT or ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.RIGHT,
                    ItemTouchHelper.START or ItemTouchHelper.END)

        }

        override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        }

    }

    class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnItemCallbackListener {
        override fun onMove(fromPosition: Int, toPosition: Int) {
        }

        override fun onSwipe(position: Int) {
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            return ViewHolders(LayoutInflater.from(p0.context).inflate(
                    R.layout.item_grid, p0, false))
        }

        override fun getItemCount(): Int {
            return 100
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
            with(holder?.itemView!!) {
                holder.itemView.setOnClickListener {
                    var location = IntArray(2)
                    tvName.getLocationOnScreen(location)
                    val rect = Rect()
                    tvName.getGlobalVisibleRect(rect)
                    tvLocation.text = String.format("locationX = ", location[0])
                    tvDown.text = String.format("down = ", rect.centerX())

                }
            }
        }

        class ViewHolders(item: View) : RecyclerView.ViewHolder(item)
    }

    public interface OnItemCallbackListener {

        /**
         * @param fromPosition 起始位置
         * @param toPosition 移动的位置
         */
        fun onMove(fromPosition : Int, toPosition : Int)
        fun onSwipe(position : Int)
    }

}