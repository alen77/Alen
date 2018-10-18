package com.alen.alen.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View

object DragViewUtil {

    var containerWidth: Int = 0
    var containerHeight: Int = 0
    var lastX: Float = 0F
    var lastY: Float = 0F

    public fun dragView(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val distanceX = lastX - event.rawX
                val distanceY = lastY - event.rawY
                var nextY = view.y - distanceY
                var nextX = view.x - distanceX

                if (nextY < 0) {
                    nextY = 0F
                } else if (nextY > containerHeight - view.height) {
                    nextY = (containerHeight - view.height).toFloat();
                }
                if (nextX < 0)
                    nextX = 0F
                else if (nextX > containerWidth - view.width)
                    nextX = (containerWidth - view.width).toFloat();

                val y = ObjectAnimator.ofFloat(view, "y", view.y, nextY)
                val x = ObjectAnimator.ofFloat(view, "x", view.x, nextX)
                val animatorSet = AnimatorSet()
                animatorSet.playTogether(x, y)
                animatorSet.duration = 0
                animatorSet.start()
                lastX = event.rawX
                lastY = event.rawY
                return true
            }
            else -> {
                return false
            }
        }
    }
}