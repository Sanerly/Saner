package com.saner.view.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.saner.util.LogUtil;

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
        Builder builder = getBuilder(rectF);
        if (builder==null){
            LogUtil.loge("no builder");
            return;
        }
        Path path = builder.path;
        canvas.clipPath(path);
        canvas.drawPath(path, mPaint);
        super.onDraw(canvas);

        if (builder.color==0 || builder.strokeWidth==0){
            LogUtil.loge("current color is null or strokeWidth is 0");
            return;
        }
        mPaint.setStrokeWidth(builder.strokeWidth);
        mPaint.setColor(builder.color);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
    }

    public abstract Builder getBuilder(RectF rectF);

    public static class Builder {
        private Path path;
        private int strokeWidth = 0;
        private int color = 0;
        public Builder setPath(Path path) {
            this.path = path;
            return this;
        }
        public Builder setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }
        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        static Builder newInstance() {

            return new Builder();
        }
    }

}
