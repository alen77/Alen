package com.example.libframework;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Alen on 2018/7/10.
 */

public abstract class FrameActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    public static final int DEFAULT = -1;
    public static final int TOOLBAR_ID = R.layout.toolbar;

    protected abstract int getLayoutId();

    protected abstract int getTitleId();

    protected abstract int getMenuId();

    protected abstract void initView();

    private LinearLayout mContentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置新的线性父容器
        initContentView();
        //设置toolbar
        setToolbar(getTitleId(), getMenuId());
        //传入对应的layoutId
        setContentView(getLayoutId());
        //主要方法入口
        initView();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, mContentLayout, true);
    }

    private void initContentView() {
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        content.removeAllViews();
        mContentLayout = new LinearLayout(this);
        mContentLayout.setOrientation(LinearLayout.VERTICAL);
        content.addView(mContentLayout);
//        LayoutInflater.from(this).inflate(R.layout.view_loader, view, true);
    }

    protected void setToolbar() {
        setToolbar(TOOLBAR_ID, DEFAULT);
    }

    protected void setToolbar(int resId) {
        setToolbar(resId, DEFAULT);
    }

    protected void setToolbarMenu(int menuId) {
        setToolbar(TOOLBAR_ID, menuId);
    }

    protected void setToolbar(int resId, int menuId) {
        LayoutInflater.from(this).inflate(resId, mContentLayout, true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (menuId != DEFAULT) {
            toolbar.inflateMenu(menuId);
        }
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
