package com.alen.alen.kotlin

import android.content.Intent
import com.alen.alen.R
import com.alen.alen.activity.BaseActivity
import com.alen.alen.activity.TimePickerActivity
import kotlinx.android.synthetic.main.activity_kotlin_test.*

class KotlinTestActivity : BaseActivity() {
    override fun getTitleId(): Int {
        return TOOLBAR_ID
    }

    override fun getMenuId(): Int {
        return DEFAULT
    }

    override fun initView() {
        val test: String = "测试Kotlin"
        tv_title.text = test
        tv_title.setOnClickListener {
            showDialog()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin_test
    }

    fun showDialog() {
        startActivity(Intent(this, TimePickerActivity::class.java))
    }
}
