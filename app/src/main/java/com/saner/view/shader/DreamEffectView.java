package com.saner.view.shader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.saner.R;
import com.saner.util.MeasureUtil;

/**
 * Created by sunset on 2018/5/10.
 * 梦幻滤镜
 */

public class DreamEffectView extends View {
    public DreamEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private Bitmap bitmap;

    private Paint mPaint;

    private PorterDuffXfermode xfermode;

    private int x, y;// 位图起点坐标

    private int screenW, screenH;// 屏幕宽高


    private RectF mRectF;
    private Paint mShaderPaint;

    private Bitmap darkCornerBitmap;

    private void init(Context context) {

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.gril);


        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);

        screenW = MeasureUtil.getScreenSize((Activity) context)[0];
        screenH = MeasureUtil.getScreenSize((Activity) context)[1];

        x = screenW / 2 - bitmap.getWidth() / 2;
        y = screenH / 2 - bitmap.getHeight() / 2;

        mRectF= new RectF(x,y,x+bitmap.getWidth(),y+bitmap.getHeight());



        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 去饱和、提亮、色相矫正
        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{0.8587F, 0.2940F, -0.0927F, 0, 6.79F, 0.0821F, 0.9145F, 0.0634F, 0, 6.79F, 0.2019F, 0.1097F, 0.7483F, 0, 6.79F, 0, 0, 0, 1, 0}));

        mShaderPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

        //设置中心向四周渐变  让四周变暗
//        mShaderPaint.setShader(new RadialGradient(screenW/2,screenH/2,bitmap.getHeight()*6/8,Color.TRANSPARENT,Color.BLACK, Shader.TileMode.CLAMP));


        darkCornerBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // 将该暗角Bitmap注入Canvas
        Canvas canvas = new Canvas(darkCornerBitmap);

        // 计算径向渐变半径
        float radiu = canvas.getHeight() * (2F / 3F);

        // 实例化径向渐变
        RadialGradient radialGradient = new RadialGradient(canvas.getWidth() / 2F, canvas.getHeight() / 2F, radiu, new int[] { 0, 0, 0xAA000000 }, new float[] { 0F, 0.7F, 1.0F }, Shader.TileMode.CLAMP);

        // 实例化一个矩阵
        Matrix matrix = new Matrix();

        // 设置矩阵的缩放
        matrix.setScale(canvas.getWidth() / (radiu * 2F), 1.0F);
//
//        // 设置矩阵的预平移
        matrix.preTranslate(((radiu * 2F) - canvas.getWidth()) / 2F, 0);

        // 将该矩阵注入径向渐变
        radialGradient.setLocalMatrix(matrix);

        // 设置画笔Shader
        mShaderPaint.setShader(radialGradient);

        // 绘制矩形
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mShaderPaint);

    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        int sc = canvas.saveLayer(0, 0, x + bitmap.getWidth(), y + bitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);

        mPaint.setColor(0xcc1c093e);
        canvas.drawRect(mRectF,mPaint);

        mPaint.setXfermode(xfermode);

        canvas.drawBitmap(bitmap,x,y,mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);

//        canvas.drawRect(mRectF,mShaderPaint);

        // 绘制我们画好的径向渐变图
        canvas.drawBitmap(darkCornerBitmap, x, y, null);
    }
}
