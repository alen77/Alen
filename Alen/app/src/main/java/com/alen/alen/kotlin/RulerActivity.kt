package com.alen.alen.kotlin

import android.annotation.SuppressLint
import com.alen.alen.R
import com.alen.alen.activity.BaseActivity
import com.alen.alen.widget.RulersView
import com.example.libframework.FrameActivity
import kotlinx.android.synthetic.main.act_ruler.*

/**
 * Created by Alen on 2018/7/12.
 */
class RulerActivity : BaseActivity(), RulersView.OnValueChangeListener {

    override fun getLayoutId(): Int {
        return R.layout.act_ruler
    }

    override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    override fun getMenuId(): Int {
        return FrameActivity.DEFAULT
    }

    var mRulerView : RulersView ?= null
    override fun initView() {
        mRulerView = findViewById(R.id.rulerView)
        mRulerView!!.setValue(55f, 20f, 200f, 0.1f)
        mRulerView!!.setOnValueChangeListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onValueChange(value: Float) {
        tv_value.text = value.toString() + "kg"
    }
}