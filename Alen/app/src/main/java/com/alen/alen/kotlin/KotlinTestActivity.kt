package com.alen.alen.kotlin

import android.content.Intent
import com.alen.alen.R
import com.alen.alen.activity.BaseActivity
import com.alen.alen.activity.TimePickerActivity
import com.alen.alen.utils.DragViewUtil
import kotlinx.android.synthetic.main.activity_kotlin_test.*


class KotlinTestActivity : BaseActivity() {

    private var containerWidth: Int = 0
    private var containerHeight: Int = 0
    var lastX: Float = 0.toFloat()
    var lastY: Float = 0.toFloat()

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

        ivMove.setOnTouchListener { view, event ->
            DragViewUtil.dragView(view, event)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            DragViewUtil.containerHeight = container.height
            DragViewUtil.containerWidth = container.width
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin_test
    }

    fun showDialog() {
        startActivity(Intent(this, TimePickerActivity::class.java))
    }
}
