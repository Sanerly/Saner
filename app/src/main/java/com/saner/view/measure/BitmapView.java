package com.saner.view.measure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.saner.R;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.UNSPECIFIED;

/**
 * Created by sunset on 2018/5/17.
 */

public class BitmapView extends View {
    public BitmapView(Context context) {
        super(context);
    }

    public BitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public BitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(Context context) {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_photo);
    }

    private Bitmap bitmap;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getPaddingTop(), getPaddingLeft(), null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (widthMode) {
            case UNSPECIFIED:
                width = bitmap.getWidth();
                break;
            case EXACTLY:
                width = widthSize;
                break;
            case AT_MOST:
                width = Math.min(bitmap.getWidth(), widthSize);
                break;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode) {
            case UNSPECIFIED:
                height = bitmap.getHeight();
                break;
            case EXACTLY:
                height = heightSize;
                break;
            case AT_MOST:
                height = Math.min(heightSize, bitmap.getHeight());
                break;
        }
        width=width+getPaddingLeft()+getPaddingRight();
        height=height+getPaddingTop()+getPaddingBottom();
        setMeasuredDimension(width,height);
    }


}
