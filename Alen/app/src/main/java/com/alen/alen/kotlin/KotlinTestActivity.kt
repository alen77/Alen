package com.alen.alen.kotlin

import android.content.Intent
import android.os.Bundle
import com.alen.alen.R
import com.alen.alen.activity.BaseActivity
import com.alen.alen.activity.TimePickerActivity
import kotlinx.android.synthetic.main.activity_kotlin_test.*

class KotlinTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar(BaseActivity.TOOLBAR_ID, R.menu.menu_main)
        setContentView(R.layout.activity_kotlin_test)
        initView()
    }

    fun initView() {
        val test: String = "测试Kotlin"
        tv_title.text = test
        tv_title.setOnClickListener {
            showDialog()
        }
    }

    fun showDialog() {
        startActivity(Intent(this, TimePickerActivity::class.java))
    }
}
