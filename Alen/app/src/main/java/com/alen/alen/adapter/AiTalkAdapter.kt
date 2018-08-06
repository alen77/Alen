package com.alen.alen.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alen.alen.R
import com.alen.alen.module.Chat
import kotlinx.android.synthetic.main.item_ai_talk.view.*

@Suppress("UNREACHABLE_CODE")
/**
 * Created by Alen on 2018/7/13.
 */
class AiTalkAdapter : BaseRecyclerAdapter<Chat>() {

    override fun onCreate(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent!!.context).inflate(
                R.layout.item_ai_talk,parent, false))
    }

    override fun onBind(viewHolder: RecyclerView.ViewHolder?, realPosition: Int, chat: Chat) {
        with(viewHolder?.itemView!!) {
            if (chat.type == 1) {
                llLeft.visibility = View.VISIBLE
                llRight.visibility = View.GONE
                tvContentL.text = chat.string
            } else {
                llLeft.visibility = View.GONE
                llRight.visibility = View.VISIBLE
                tvContentR.text = chat.string
            }

        }
    }

    class ItemViewHolder(item : View) : RecyclerView.ViewHolder(item)
}