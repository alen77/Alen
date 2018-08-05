package com.alen.alen.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by alen on 2017/9/10.
 */

public class EveryUtils {


    /**
     * 简单的字符放大动画
     * @param view
     */
    public void enlargeNumView(View view) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 2.3f, 1.0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 2.3f, 1.0f);

        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.playTogether(animator1, animator2);
        animatorSet.start();
    }
}

