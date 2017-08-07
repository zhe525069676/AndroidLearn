package com.zheblog.circleprogresstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CircleProgressView mCircleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleProgressView = (CircleProgressView) findViewById(R.id.view);
        mCircleProgressView.setCurrentProgress(8000, 1000);
    }
}
