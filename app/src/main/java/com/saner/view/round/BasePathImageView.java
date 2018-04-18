package com.saner.view.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by sunset on 2018/4/18.
 */

public abstract class BasePathImageView extends android.support.v7.widget.AppCompatImageView {
    public BasePathImageView(Context context) {
        this(context, null);
    }

    public BasePathImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePathImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint mPaint;

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.FILL);


    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        Path path = getPath(rectF);
        canvas.clipPath(path);
        canvas.drawPath(path, mPaint);
        super.onDraw(canvas);
    }

    public abstract Path getPath(RectF rectF);

}
