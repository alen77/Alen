package com.alen.alen.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alen.alen.R;
import com.alibaba.view.BubblingView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View mVLike;
    private BubblingView mBubblingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVLike = findViewById(R.id.btn_like);
        mBubblingView = (BubblingView) findViewById(R.id.bubbling_view);

        mVLike.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mBubblingView.addBubblingItem(R.drawable.shape_circle);
    }
}
