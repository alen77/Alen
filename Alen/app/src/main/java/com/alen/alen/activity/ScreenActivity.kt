package com.alen.alen.activity

import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class ScreenActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = TextView(this)
        text.text = "It's a TextView"
        text.textSize = 20f

        val param = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.TRANSPARENT)
        param.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        param.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
        param.gravity = Gravity.CENTER
        windowManager.addView(text, param)
    }
}