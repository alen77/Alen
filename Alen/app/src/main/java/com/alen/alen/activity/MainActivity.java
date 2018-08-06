package com.alen.alen.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import com.alen.alen.R;
import com.alen.alen.dialog.AlertDialog;
import com.alen.alen.dialog.BaseDialog;
import com.alen.alen.kotlin.AiTalkActivity;
import com.alen.alen.kotlin.KotlinTestActivity;
import com.alen.alen.kotlin.RulerActivity;
import com.alen.alen.kotlin.ScrollViewActivity;
import com.alibaba.view.BubblingView;

import java.util.Random;

public class MainActivity extends BaseActivity implements View.OnClickListener, AlertDialog.OnNegativeClickListener {

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
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mVLike = findViewById(R.id.btn_like);
        mBubblingView = (BubblingView) findViewById(R.id.bubbling_view);
        mVLike.setOnClickListener(this);
        findViewById(R.id.btn_kotlin).setOnClickListener(this);
        findViewById(R.id.btnRuler).setOnClickListener(this);
        findViewById(R.id.btnStretch).setOnClickListener(this);
        findViewById(R.id.btnAi).setOnClickListener(this);
    }

    @Override
    public int getTitleId() {
        return TOOLBAR_ID;
    }

    @Override
    public int getMenuId() {
        return R.menu.menu_main;
    }

    private void showFlower() {
        mVLike.setText(mIndex + "赞");
        Random random = new Random();
        mBubblingView.addBubblingItem(mResIds[random.nextInt(mResIds.length - 1)]);
        mVLike.postDelayed(mRunnable, 500);
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Ai陪聊")
                .setMessage("一个非常机智的人工智能陪你聊天")
                .setPositive("马上开始", this)
                .setNegative("取消", this)
                .create().show();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_kotlin) {
            startActivity(new Intent(this, KotlinTestActivity.class));
        } else if (view.getId() == R.id.btnRuler) {
            startActivity(new Intent(this, RulerActivity.class));
        } else if (view.getId() == R.id.btnStretch) {
            startActivity(new Intent(this, ScrollViewActivity.class));
        } else if (view.getId() == R.id.btnAi) {
            showDialog();
        } else {
            if (mIndex == 0) {
                mIndex = 1;
                mVLike.postDelayed(mRunnable, 100);
            } else {
                mIndex = 0;
                mVLike.removeCallbacks(mRunnable);
            }
        }
    }

    @Override
    public void onNegative() {
    }

    @Override
    public void onPositive() {
        startActivity(new Intent(this, AiTalkActivity.class));
    }
}
