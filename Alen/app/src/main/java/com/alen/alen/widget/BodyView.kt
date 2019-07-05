package com.alen.alen.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.alen.alen.bean.ListBean
import kotlinx.android.synthetic.main.view_body.view.*

class BodyView : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs)
    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(mContext, attrs, defStyleAttr)
    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(mContext, attrs, defStyleAttr, defStyleRes)

    fun setData(data: ListBean.Info) {
        tvTop.text = data.top
        tvCenter.text = data.center
        tvBottom.text = data.bottom
    }
}