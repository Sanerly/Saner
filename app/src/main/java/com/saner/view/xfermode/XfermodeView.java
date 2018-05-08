package com.saner.view.xfermode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by sunset on 2018/5/8.
 */

public class XfermodeView extends android.support.v7.widget.AppCompatImageView {
    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;

    private int width = 300;

    private int centerX;
    private int centerY;
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.BLACK);


        //绘制“src”蓝色矩形原图
        canvas.drawBitmap(getSrcBitmap(),10, 10, mPaint);
        //绘制“dst”黄色圆形原图
        canvas.drawBitmap(getDstBitmap(), getWidth()-width, width / 12, mPaint);

        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(getDstBitmap(), centerX - width / 2, centerY - width / 2, mPaint);


//       饱和相加
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//      清除源图和目标图的交集，以及目标图
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//       变暗  交集的地方颜色混合变深
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
//      只显示目标图
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));

//        在源图像和目标图像相交的地方绘制目标图像,在不相交的地方绘制源图像
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
//          只在源图像和目标图像相交的地方绘制目标图像
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        只在源图像和目标图像相交的地方绘制源图像
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        在源图像的上方绘制目标图像
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
//        变亮
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
//        正片叠底
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
//        叠加
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
//         滤色
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
//          只显示源图
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
//        只在源图像和目标图像相交的地方绘制源图像
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        在源图像和目标图像相交的地方绘制源图像，在不相交的地方绘制目标图像
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
//        只在源图像和目标图像不相交的地方绘制源图像
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        在目标图像的顶部绘制源图像
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
//        在源图像和目标图像重叠之外的任何地方绘制他们，而在不重叠的地方不绘制任何内容
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        canvas.drawBitmap(getSrcBitmap(), centerX, centerY, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }


    /**
     * 源图  正方形  意为将要绘制的图像
     * 通俗的讲就是你最终要显示的图
     * * @return
     */
    public Bitmap getSrcBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#63A9FE"));
        p.setStyle(Paint.Style.FILL);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(new RectF(0, 0, width, width), p);
        return bitmap;
    }

    /**
     * 目标图  圆形  意为我们将要把源图像绘制到的图像
     *就是默认的图
     * @return
     */
    public Bitmap getDstBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#FECF41"));
        p.setStyle(Paint.Style.FILL);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawOval(new RectF(0, 0, width, width), p);
        return bitmap;
    }
}
