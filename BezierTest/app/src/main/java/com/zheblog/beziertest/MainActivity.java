package com.zheblog.beziertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by liuz on 17/7/18.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_two: {
                intent.setClass(this, TwoBezierActivity.class);
            }
            break;
            case R.id.btn_three: {
                intent.setClass(this, ThreeBezierActivity.class);
            }
            break;
        }
        startActivity(intent);
    }
}
