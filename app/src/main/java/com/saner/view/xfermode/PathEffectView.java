package com.saner.view.xfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sunset on 2018/5/10.
 */

public class PathEffectView  extends View {
    private float mPhase;// 偏移值
    private Paint mPaint;// 画笔对象
    private Path mPath;// 路径对象
    private PathEffect[] mEffects;// 路径效果数组

    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*
         * 实例化画笔并设置属性
         */
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.DKGRAY);

        // 实例化路径
        mPath = new Path();

        // 定义路径的起点
        mPath.moveTo(0, 0);

        // 定义路径的各个点
        for (int i = 0; i <= 30; i++) {
            mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }

        // 创建路径效果数组
        mEffects = new PathEffect[7];
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
         * 实例化各类特效
         */
        mEffects[0] = null;
        mEffects[1] = new CornerPathEffect(50);
        //生锈的效果
        mEffects[2] = new DiscretePathEffect(1.0F, 5.0F);
        //虚实线效果
        mEffects[3] = new DashPathEffect(new float[] { 20, 30 }, mPhase);
        Path path = new Path();
        path.addOval(0, 0, 8, 8, Path.Direction.CCW);
        mEffects[4] = new PathDashPathEffect(path, 25, mPhase, PathDashPathEffect.Style.ROTATE);
        mEffects[5] = new ComposePathEffect(mEffects[2], mEffects[4]);
        mEffects[6] = new SumPathEffect(mEffects[4], mEffects[3]);

        /*
         * 绘制路径
         */
        for (int i = 0; i < mEffects.length; i++) {
            mPaint.setPathEffect(mEffects[i]);
            canvas.drawPath(mPath, mPaint);

            // 每绘制一条将画布向下平移250个像素
            canvas.translate(0, 250);
        }

        // 刷新偏移值并重绘视图实现动画效果
        mPhase += 1;
        invalidate();
    }
}
