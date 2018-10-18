package com.alen.alen.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.OvershootInterpolator

/**
 * Created by alen on 2017/9/10.
 */

class EveryUtils {


    /**
     * 简单的字符放大动画
     * @param view
     */
    fun enlargeNumView(view: View) {
        val animatorSet = AnimatorSet()

        val animator1 = ObjectAnimator.ofFloat(view, "scaleX", 2.3f, 1.0f)
        val animator2 = ObjectAnimator.ofFloat(view, "scaleY", 2.3f, 1.0f)

        animatorSet.duration = 200
        animatorSet.interpolator = OvershootInterpolator()
        animatorSet.playTogether(animator1, animator2)
        animatorSet.start()
    }
}

