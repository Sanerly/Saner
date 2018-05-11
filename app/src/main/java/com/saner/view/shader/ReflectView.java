package com.saner.view.shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.saner.R;
import com.saner.util.MeasureUtil;

/**
 * Created by sunset on 2018/5/10.
 * 倒影
 */

public class ReflectView extends View{
    public ReflectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    Paint mPaint;
    Bitmap mSrcBitmap,mRefBitmap;
    private PorterDuffXfermode mXfermode;// 混合模式

    private int x, y;// 位图起点坐标
    private void init(Context context) {
        mSrcBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.icon_photo);

        Matrix matrix=new Matrix();
        matrix.setScale(1F,-1F);

        mRefBitmap=Bitmap.createBitmap(mSrcBitmap,0,0,mSrcBitmap.getWidth(),mSrcBitmap.getHeight(),matrix,true);



        int screenW = MeasureUtil.getScreenSize((Activity) context)[0];
        int screenH = MeasureUtil.getScreenSize((Activity) context)[1];

        x = screenW / 2 - mSrcBitmap.getWidth() / 2;
        y = screenH / 2 - mSrcBitmap.getHeight() / 2;

        // ………………………………
        mPaint = new Paint();
        mPaint.setShader(new LinearGradient(x, y + mSrcBitmap.getHeight(), x, y + mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 4, Color.RED, Color.TRANSPARENT, Shader.TileMode.CLAMP));


        //只在源图和目标图相交的地方绘制目标图
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mSrcBitmap, x, y, null);

        int sc = canvas.saveLayer(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);
////
        canvas.drawBitmap(mRefBitmap, x, y + mSrcBitmap.getHeight(), null);

        mPaint.setXfermode(mXfermode);
//
        canvas.drawRect(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, mPaint);
//
        mPaint.setXfermode(null);
//
        canvas.restoreToCount(sc);
    }
}
