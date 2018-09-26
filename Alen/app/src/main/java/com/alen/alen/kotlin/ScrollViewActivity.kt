package com.alen.alen.kotlin

import com.alen.alen.R
import com.alen.alen.activity.BaseActivity
import com.alen.alen.widget.StretchLayout
import com.example.libframework.FrameActivity
import kotlinx.android.synthetic.main.act_scorllview.*

/**
 * Created by Alen on 2018/7/12.
 */
class ScrollViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.act_scorllview
    }

    override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    override fun getMenuId(): Int {
        return FrameActivity.DEFAULT
    }

    var mStretchLayout : StretchLayout ?= null
    override fun initView() {
        mStretchLayout = findViewById(R.id.stretchLayout)
        mStretchLayout!!.setHeader(ivHeader)
    }

}
