package com.saner.view.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sunset on 2018/4/3.
 */

public class ClipImageBorderView extends View {
    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageBorderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    Context mContext;

    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding = 100;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;
    /**
     * 绘制的矩形的宽度
     */
    private int mWidth;
    /**
     * 边框的颜色，默认为白色
     */
    private int mBorderColor = Color.parseColor("#FFFFFF");
    /**
     * 边框的宽度 单位dp
     */
    private int mBorderWidth = 3;

    Paint mPaint;

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*计算水平方向的宽度*/
        mWidth = getWidth() - 2 * mHorizontalPadding;
        /*计算垂直边界距离屏幕边缘的距离*/
        mVerticalPadding = (getHeight() - mWidth) / 2;


        mPaint.setColor(Color.parseColor("#aa000000"));
        mPaint.setStyle(Paint.Style.FILL);
        /*绘制左边矩形*/
        canvas.drawRect(0, 0, mHorizontalPadding, getHeight(), mPaint);

        /*绘制右边矩形*/
        canvas.drawRect(new RectF(mWidth + mHorizontalPadding, 0f, getWidth(), getHeight()), mPaint);

        /*绘制下边矩形*/
        canvas.drawRect(new Rect(mHorizontalPadding, mWidth + mVerticalPadding, mWidth + mHorizontalPadding, getHeight()), mPaint);

        /*绘制上边矩形*/
        canvas.drawRect(mHorizontalPadding, 0, mWidth + mHorizontalPadding, mVerticalPadding, mPaint);

        /*设置绘制裁剪边框的画笔*/
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        /*绘制裁剪需要的白色边框*/
        canvas.drawRect(new Rect(mHorizontalPadding, mVerticalPadding, mWidth + mHorizontalPadding, mWidth + mVerticalPadding), mPaint);

    }
}
