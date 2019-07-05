package com.alen.alen.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.alen.alen.R
import com.alen.alen.bean.ListBean
import kotlinx.android.synthetic.main.item_list_test.view.*

class TestItemView : FrameLayout {

    val mData = ListBean()

    constructor(context: Context) : super(context)
    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs)
    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(mContext, attrs, defStyleAttr)
    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(mContext, attrs, defStyleAttr, defStyleRes)

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setData(data: ListBean) {
        tvTitle.text = data.title
        setBody(data.data)
    }

    fun setBody(list: ArrayList<ListBean.Info>) {
        for (data in list) {
            val v = LayoutInflater.from(context).inflate(R.layout.view_body, this, false) as BodyView
            v.setData(data)
            llContainer.addView(v)
        }
    }
}