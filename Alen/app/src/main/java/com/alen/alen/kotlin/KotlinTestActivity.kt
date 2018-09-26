package com.alen.alen.kotlin

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.MotionEvent
import com.alen.alen.R
import com.alen.alen.activity.BaseActivity
import com.alen.alen.activity.TimePickerActivity
import kotlinx.android.synthetic.main.activity_kotlin_test.*



class KotlinTestActivity : BaseActivity() {

    private var containerWidth: Int = 0
    private var containerHeight: Int = 0
    var lastX: Float = 0.toFloat()
    var lastY:Float = 0.toFloat()

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
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.rawX;
                    lastY = event.rawY;
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    val distanceX = lastX - event.rawX
                    val distanceY = lastY - event.rawY
                    var nextY = ivMove.y - distanceY
                    var nextX = ivMove.x - distanceX

                    if (nextY < 0) {
                        nextY = 0F;
                    }else if (nextY > containerHeight - ivMove.height) {
                        nextY = (containerHeight - ivMove.height).toFloat();
                    }
                    if (nextX < 0)
                        nextX = 0F;
                    else if (nextX > containerWidth - ivMove.width)
                        nextX = (containerWidth - ivMove.width).toFloat();

                    val y = ObjectAnimator.ofFloat(ivMove, "y", ivMove.y, nextY)
                    val x = ObjectAnimator.ofFloat(ivMove, "x", ivMove.x, nextX)
                    val animatorSet = AnimatorSet()
                    animatorSet.playTogether(x, y)
                    animatorSet.duration = 0;
                    animatorSet.start();
                    lastX = event.rawX;
                    lastY = event.rawY;
                    return@setOnTouchListener true
                }
                else -> {
                    return@setOnTouchListener false
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            containerHeight = container.height
            containerWidth = container.width
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin_test
    }

    fun showDialog() {
        startActivity(Intent(this, TimePickerActivity::class.java))
    }
}
