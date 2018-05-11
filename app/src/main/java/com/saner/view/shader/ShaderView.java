package com.saner.view.shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.saner.R;
import com.saner.util.MeasureUtil;

/**
 * Created by sunset on 2018/5/10.
 * 着色器练习
 */

public class ShaderView extends View {
    private static final int RECT_SIZE = 400;// 矩形尺寸的一半

    private Paint mPaint;// 画笔

    private int left, top, right, bottom;// 矩形坐上右下坐标

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取屏幕尺寸数据
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        // 获取屏幕中点坐标
        int screenX = screenSize[0] / 2;
        int screenY = screenSize[1] / 2;

        // 计算矩形左上右下坐标值
        left = screenX - RECT_SIZE;
        top = screenY - RECT_SIZE;
        right = screenX + RECT_SIZE;
        bottom = screenY + RECT_SIZE;

        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // 获取位图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_photo);

        // 设置着色器
//        mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT));

        //线性渐变
//        mPaint.setShader(new LinearGradient(left, top, right-RECT_SIZE, bottom-RECT_SIZE, Color.RED, Color.YELLOW, Shader.TileMode.CLAMP));

//        mPaint.setShader(new LinearGradient(left, top, right, bottom, new int[] { Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE }, null, Shader.TileMode.MIRROR));


        //扫描式渐变， 效果有点类似雷达的扫描效果
//        mPaint.setShader(new SweepGradient(screenX,screenY,Color.RED,Color.YELLOW));

//        mPaint.setShader(new SweepGradient(screenX,screenY,new int[]{Color.YELLOW,Color.RED,Color.YELLOW},null));


        //径向渐变，径向渐变说的简单点就是个圆形中心向四周渐变的效果
//        mPaint.setShader(new RadialGradient(screenX,screenY,RECT_SIZE,Color.YELLOW,Color.RED,Shader.TileMode.CLAMP));

//        mPaint.setShader(new RadialGradient(screenX,screenY,RECT_SIZE,new int[]{Color.YELLOW,Color.RED,Color.YELLOW},null,Shader.TileMode.CLAMP));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制矩形
        canvas.drawOval(new RectF(left, top, right, bottom), mPaint);
    }
}
