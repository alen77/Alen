package com.alen.alen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alen.alen.R;
import com.alibaba.view.BubblingView;

import java.util.Random;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mVLike;
    private BubblingView mBubblingView;

    private int[] mResIds = {R.drawable.flower,
            R.drawable.shape_circle_blue,
            R.drawable.shape_circle_green,
            R.drawable.shape_circle_red,
            R.drawable.shape_square_black,
            R.drawable.shape_square_blue,
            R.drawable.shape_square_orange};

    private int mIndex = 0;

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            showFlower();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(TOOLBAR_ID, R.menu.menu_main);

        setContentView(R.layout.activity_main);


        mVLike = (Button) findViewById(R.id.btn_like);
        mBubblingView = (BubblingView) findViewById(R.id.bubbling_view);
        mVLike.setOnClickListener(this);
    }

    private void showFlower() {
        mVLike.setText(mIndex + "赞");
        Random random = new Random();
        mBubblingView.addBubblingItem(mResIds[random.nextInt(mResIds.length - 1)]);
        mVLike.postDelayed(mRunnable, 500);
    }

    @Override
    public void onClick(View view) {
        if (mIndex == 0) {
            mIndex = 1;
            mVLike.postDelayed(mRunnable, 100);
        } else {
            mIndex = 0;
            mVLike.removeCallbacks(mRunnable);
        }
    }
}
