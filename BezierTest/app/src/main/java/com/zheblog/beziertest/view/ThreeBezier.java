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

public class ThreeBezier extends View {

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
    private float mControlPointOneX;
    private float mControlPointOneY;
    private float mControlPointTwoX;
    private float mControlPointTwoY;

    //触摸范围半径，为了控制点击/移动控制点
    private final float TOUCH_RADIUS = 30F;

    private boolean isTwoPoint = false;

    private Path mPath = new Path();

    public ThreeBezier(Context context) {
        super(context);
    }

    public ThreeBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化控制点位置
        mControlPointOneX = 30F;
        mControlPointOneY = 30F;
        mControlPointTwoX = 80F;
        mControlPointTwoY = 80F;

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

        canvas.drawPoint(mControlPointOneX, mControlPointOneY, mPaintControl);
        canvas.drawText("start", mStartPointX, mStartPointY, mPaintText);
        canvas.drawText("end", mEndPointX, mEndPointY, mPaintText);
        canvas.drawText("control1", mControlPointOneX, mControlPointOneY, mPaintText);
        canvas.drawText("control2", mControlPointTwoX, mControlPointTwoY, mPaintText);

        canvas.drawLine(mStartPointX, mStartPointY, mControlPointOneX, mControlPointOneY, mPaintControl);
        canvas.drawLine(mEndPointX, mEndPointY, mControlPointTwoX, mControlPointTwoY, mPaintControl);
        canvas.drawLine(mControlPointOneX, mControlPointOneY, mControlPointTwoX, mControlPointTwoY, mPaintControl);

        mPath.cubicTo(mControlPointOneX, mControlPointOneY, mControlPointTwoX, mControlPointTwoY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTwoPoint = selectTwoPoint(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTwoPoint) {
                    mControlPointTwoX = event.getX();
                    mControlPointTwoY = event.getY();
                } else {
                    mControlPointOneX = event.getX();
                    mControlPointOneY = event.getY();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isTwoPoint = false;
                break;
        }
        return true;
    }

    private boolean selectTwoPoint(float x, float y) {
        return (mControlPointTwoX - x) < TOUCH_RADIUS && (mControlPointTwoY - y) < TOUCH_RADIUS;
    }

}
