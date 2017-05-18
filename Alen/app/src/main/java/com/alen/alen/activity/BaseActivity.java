package com.alen.alen.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alen.alen.R;

/**
 * Created by alen on 2017/5/16.
 */

public class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    protected static final int DEFAULT = -1;
    protected static final int TOOLBAR_ID = R.layout.toolbar;

    private LinearLayout mContentLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
    }

    private void initContentView() {
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        content.removeAllViews();
        mContentLayout = new LinearLayout(this);
        mContentLayout.setOrientation(LinearLayout.VERTICAL);
        content.addView(mContentLayout);
//        LayoutInflater.from(this).inflate(R.layout.view_loader, view, true);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, mContentLayout, true);
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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (menuId != DEFAULT) {
            mToolbar.inflateMenu(menuId);
        }
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
