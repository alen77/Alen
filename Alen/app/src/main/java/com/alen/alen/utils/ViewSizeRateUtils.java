package com.alen.alen.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by Alen on 16/2/17.
 */
public class ViewSizeRateUtils {
    /**
     * 以宽为基准设置图片比例
     *
     * @param v
     * @param rateW
     * @param rateH
     */
    public static void setViewSizeRate(final View v, final int rateW, final int rateH) {
        if (v == null) return;
        if (v.getWidth() > 0) {
            ViewGroup.LayoutParams params = v.getLayoutParams();
            params.height = (int) (((float) v.getWidth()) / rateW * rateH);
            v.setLayoutParams(params);
            return;
        }

        final ViewTreeObserver vto = v.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            boolean isDone = false;

            @Override
            public void onGlobalLayout() {
                if (isDone) return;
                ViewGroup.LayoutParams params = v.getLayoutParams();
                params.height = (int) (((float) v.getWidth()) / rateW * rateH);
                v.setLayoutParams(params);

                try {

                    vto.removeGlobalOnLayoutListener(this);
                } catch (Exception e) {

                } finally {
                    isDone = true;
                }
            }
        });
    }
}
