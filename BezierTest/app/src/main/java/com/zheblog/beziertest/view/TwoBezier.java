package com.zheblog.beziertest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liuz on 17/7/18.
 */

public class TwoBezier extends View {

    private Paint mPaintBezier;
    private Paint mPaintControl;
    private Paint mPaintText;

    //起始点
    private float mStartPointX;
    private float mStartPointY;
    //终点
    private float mEndPointX;
    private float mEndPointY;
    //控制点
    private float mControlPointX;
    private float mControlPointY;

    private Path mPath = new Path();

    public TwoBezier(Context context) {
        super(context);
    }

    public TwoBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化控制点位置
        mControlPointX = 30F;
        mControlPointY = 30F;

        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setColor(Color.RED);
        mPaintBezier.setStrokeWidth(8);

        mPaintControl = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintControl.setStyle(Paint.Style.STROKE);
        mPaintControl.setColor(Color.BLACK);
        mPaintControl.setStrokeWidth(6);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setTextSize(20);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2;
        mEndPointX = w / 4 * 3;
        mEndPointY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //重置路径
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);

        canvas.drawPoint(mControlPointX, mControlPointY, mPaintControl);
        canvas.drawText("start", mStartPointX, mStartPointY, mPaintText);
        canvas.drawText("end", mEndPointX, mEndPointY, mPaintText);
        canvas.drawText("control", mControlPointX, mControlPointY, mPaintText);

        canvas.drawLine(mStartPointX, mStartPointY, mControlPointX, mControlPointY, mPaintControl);
        canvas.drawLine(mEndPointX, mEndPointY, mControlPointX, mControlPointY, mPaintControl);

        mPath.quadTo(mControlPointX, mControlPointY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mControlPointX = event.getX();
                mControlPointY = event.getY();
                invalidate();
        }
        return true;
    }

}
