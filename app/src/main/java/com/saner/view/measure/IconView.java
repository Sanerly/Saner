package com.saner.view.measure;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.saner.R;
import com.saner.util.MeasureUtil;

/**
 * Created by sunset on 2018/5/18.
 */

public class IconView extends View {

    private Bitmap mBitmap;// 位图
    private TextPaint mPaint;// 绘制文本的画笔
    private String mStr;// 绘制的文本

    private float mTextSize;// 画笔的文本尺寸

    /**
     * 宽高枚举类
     */
    private enum Ratio {
        WIDTH, HEIGHT
    }

    public IconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_photo);

        int sreenw = MeasureUtil.getScreenSize((Activity) context)[0];
        mTextSize = sreenw / 10f;

        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(Color.RED);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);

        mStr = "六六六";
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasureSize(widthMeasureSpec, Ratio.WIDTH), getMeasureSize(heightMeasureSpec, Ratio.HEIGHT));
    }

    private int getMeasureSize(int measurespec, Ratio ratio) {
        int result = 0;

        int mode = MeasureSpec.getMode(measurespec);
        int size = MeasureSpec.getSize(measurespec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            if (ratio == Ratio.WIDTH) {
                int textWidth = (int) mPaint.measureText(mStr);
                result = textWidth >= mBitmap.getWidth() ? textWidth : mBitmap.getWidth() + getPaddingLeft() + getPaddingRight();
            } else {
                result = (int) (mBitmap.getHeight() + getPaddingTop() + getPaddingBottom() + (mPaint.descent() - mPaint.ascent()));
            }

            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }

        }


        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        canvas.drawBitmap(mBitmap, getPaddingLeft(), getPaddingTop(), null);
//        canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2,  getPaddingTop() + getHeight()/2-mBitmap.getHeight()/2- (mPaint.density - mPaint.ascent()), null);
        canvas.drawText(mStr, getWidth() / 2, getPaddingTop() + mBitmap.getHeight() + (mPaint.density - mPaint.ascent()), mPaint);
//        canvas.drawText(mStr, getWidth() / 2, getPaddingTop() + getHeight()/2-mBitmap.getHeight()/2+mBitmap.getHeight() , mPaint);

//        canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2, getHeight() / 2 - mBitmap.getHeight() / 2, null);
//        canvas.drawText(mStr, getWidth() / 2, mBitmap.getHeight() + getHeight() / 2 - mBitmap.getHeight() / 2 - mPaint.ascent(), mPaint);

    }
}
