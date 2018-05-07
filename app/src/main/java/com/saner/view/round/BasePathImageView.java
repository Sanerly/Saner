package com.saner.view.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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
        init();
    }

    private Paint mPaint;
    private RectF mRectF;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF();
        mRectF.left = getPaddingLeft();
        mRectF.top = getPaddingTop();
        mRectF.right = w - getPaddingRight();
        mRectF.bottom = h - getPaddingBottom();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Builder builder = getBuilder(mRectF);
        if (builder == null) {
            LogUtil.loge("no builder");
            return;
        }
        Path path = builder.path;
        canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        onClipDraw(builder, path, canvas);
        canvas.restore();

    }

    public void onClipDraw(Builder builder, Path path, Canvas canvas) {
        if (builder.strokeWidth > 0) {
            // 支持半透明描边，将与描边区域重叠的内容裁剪掉  在源图上面抠出一个目标图形
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeWidth(builder.strokeWidth * 2);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, mPaint);
            // 绘制描边  在刚刚抠出的目标图形上绘制边框
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setColor(builder.color);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, mPaint);
        }
        // 混合模式为 DST_IN, 即仅显示当前绘制区域和背景区域交集的部分，并仅显示背景内容。
        //裁剪掉目标图以外的内容
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
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

        public Builder setColor(@ColorInt int color) {
            this.color = color;
            return this;
        }

        static Builder newInstance() {

            return new Builder();
        }
    }

}
