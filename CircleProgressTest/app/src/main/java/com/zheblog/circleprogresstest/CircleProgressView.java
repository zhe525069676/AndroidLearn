package com.zheblog.circleprogresstest;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuz on 17/8/7.
 */
@SuppressLint("NewApi")
public class CircleProgressView extends View {

    private Paint mSumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mSumRect;
    private float mBorderWidth = dp2Px(16);
    private float mNumTextSize;
    private float mStepNum = 0;
    private float mStartAngle = 135;
    private float mAngleLength = 270;
    private float mCurrentAngleLength;
    private final int mDuration = 3000;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = (getWidth()) / 2;
        /**指定圆弧的外轮廓矩形区域*/
        mSumRect = new RectF(0 + mBorderWidth, mBorderWidth, 2 * centerX - mBorderWidth, 2 * centerX - mBorderWidth);
        drawSumProgress(canvas);

        drawStepProgress(canvas);

        drawNumText(canvas, centerX);
    }

    private void drawSumProgress(Canvas canvas) {
        mSumPaint.setColor(getResources().getColor(R.color.yellow, null));
        mSumPaint.setStrokeJoin(Paint.Join.ROUND);
        mSumPaint.setStrokeCap(Paint.Cap.ROUND);
        mSumPaint.setStyle(Paint.Style.STROKE);
        mSumPaint.setStrokeWidth(mBorderWidth);
        canvas.drawArc(mSumRect, mStartAngle, mAngleLength, false, mSumPaint);
    }

    private void drawStepProgress(Canvas canvas) {
        mSumPaint.setColor(getResources().getColor(R.color.red, null));
//        mSumPaint.setStrokeJoin(Paint.Join.ROUND);
//        mSumPaint.setStrokeCap(Paint.Cap.ROUND);
//        mSumPaint.setStyle(Paint.Style.STROKE);
//        mSumPaint.setStrokeWidth(mBorderWidth);
        canvas.drawArc(mSumRect, mStartAngle, mCurrentAngleLength, false, mSumPaint);
    }

    private void drawNumText(Canvas canvas, float centerX) {
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mNumTextSize);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mTextPaint.setTypeface(font);//字体风格
        mTextPaint.setColor(getResources().getColor(R.color.red, null));
        Rect bounds_Number = new Rect();
        mTextPaint.getTextBounds(String.valueOf((int) mStepNum), 0, String.valueOf((int) mStepNum).length(), bounds_Number);
        canvas.drawText(String.valueOf((int) mStepNum), centerX, getHeight() / 2 + bounds_Number.height() / 2, mTextPaint);
    }

    private void setAnimation(float start, float current, int length) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(start, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(mCurrentAngleLength);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**每次在初始值和结束值之间产生的一个平滑过渡的值，逐步去更新进度*/
                mCurrentAngleLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    public void setCurrentProgress(int totalStepNum, int currentCounts) {
        currentCounts = currentCounts > totalStepNum ? totalStepNum : currentCounts;
        float scalePrevious = mStepNum / totalStepNum;
        float previousAngleLength = scalePrevious * mAngleLength;
        float scale = (float) currentCounts / totalStepNum;
        float currentAngleLength = scale * mAngleLength;
        setAnimation(previousAngleLength, currentAngleLength, mDuration);
        mStepNum = currentCounts;
        setTextSize(currentCounts);
    }


    private int dp2Px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    public void setTextSize(int num) {
        String s = String.valueOf(num);
        int length = s.length();
        if (length <= 4) {
            mNumTextSize = dp2Px(50);
        } else if (length > 4 && length <= 6) {
            mNumTextSize = dp2Px(40);
        } else if (length > 6 && length <= 8) {
            mNumTextSize = dp2Px(30);
        } else if (length > 8) {
            mNumTextSize = dp2Px(25);
        }
    }


}
