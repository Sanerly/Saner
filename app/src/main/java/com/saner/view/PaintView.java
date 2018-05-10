package com.saner.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.saner.R;

/**
 * Created by sunset on 2018/5/9.
 */

public class PaintView extends View{

    public PaintView(Context context) {
        this(context,null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private void init() {
        mPaint=new Paint();
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        //设置关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        // 设置画笔遮罩滤镜
//        mPaint.setDither();
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
    }

    /**
     * 初始化资源
     */
    private void initRes(Context context) {



    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.GRAY);
        // 画一个矩形
//        canvas.drawRect(100, 100, 600, 600, mPaint);

        // 获取位图
    Bitmap srcBitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.icon_photo);

        // 获取位图的Alpha通道图
        Bitmap    shadowBitmap = srcBitmap.extractAlpha();

        canvas.drawBitmap(shadowBitmap,100,100,mPaint);
        canvas.drawBitmap(srcBitmap,100,100,null);
    }

}
