package com.saner.view.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sunset on 2018/5/14.
 */

public class MultiCricleView extends View {
    private static final float STROKE_WIDTH = 1F / 256F, // 描边宽度占比
            SPACE = 1F / 64F,// 大圆小圆线段两端间隔占比
            LINE_LENGTH = 3F / 32F, // 线段长度占比
            CRICLE_LARGER_RADIU = 3F / 32F,// 大圆半径
            CRICLE_SMALL_RADIU = 5F / 64F,// 小圆半径
            ARC_RADIU = 1F / 8F,// 弧半径
            ARC_TEXT_RADIU = 5F / 32F;// 弧围绕文字半径

    private Paint strokePaint, textPaint, arcPaint;// 描边画笔

    private int size;// 控件边长

    private float strokeWidth;// 描边宽度
    private float ccX, ccY;// 中心圆圆心坐标
    private float largeCircleRadius;// 大圆半径
    private float smallCircleRadiu;// 小圆半径
    private float lineLength;// 线段长度
    private float space;// 大圆小圆线段两端间隔

    private float textOffsetY;// 文本的Y轴偏移值

    public MultiCricleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        strokePaint.setColor(Color.WHITE);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);


        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = w;

        //计算边框宽度
        strokeWidth = STROKE_WIDTH * size;
        strokePaint.setStrokeWidth(strokeWidth);

        //计算大圆半径
        largeCircleRadius = CRICLE_LARGER_RADIU * size;

        //中心圆圆心坐标
        ccX = size / 2;
        ccY = size / 2 + largeCircleRadius;

        //圆与圆的连线
        lineLength = LINE_LENGTH * size;
        //小圆和大圆之间的间隔
        space = SPACE * size;
        //小圆半径
        smallCircleRadiu = CRICLE_SMALL_RADIU * size;


        textOffsetY = (textPaint.descent() + textPaint.ascent()) / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(ccX, ccY, largeCircleRadius, strokePaint);
        canvas.drawText("center", ccX, ccY - textOffsetY, textPaint);

        drawTopLeft(canvas);

        // 绘制右上方图形
        drawTopRight(canvas);

        // 绘制左下方图形
        drawBottomLeft(canvas);

        // 绘制下方图形
        drawBottom(canvas);

        // 绘制右下方图形
        drawBottomRight(canvas);
    }

    private void drawTopLeft(Canvas canvas) {
        canvas.save();
// 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(-30);
        canvas.drawLine(0, -largeCircleRadius, 0, -lineLength * 2, strokePaint);
        canvas.drawCircle(0, -lineLength * 3, largeCircleRadius, strokePaint);
        canvas.drawLine(0, -lineLength * 4, 0, -lineLength * 5, strokePaint);
        canvas.drawCircle(0, -lineLength * 6, largeCircleRadius, strokePaint);


        canvas.translate(0, -lineLength * 3);
        canvas.rotate(30);
        canvas.drawText("TopLeft-1", 0, 0 - textOffsetY, textPaint);
        canvas.rotate(-30);
        canvas.translate(0, -lineLength * 3);
        canvas.rotate(30);
        canvas.drawText("TopLeft-2", -0, 0 - textOffsetY, textPaint);


        canvas.restore();
    }

    private void drawTopRight(Canvas canvas) {

        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.rotate(30);
        canvas.drawLine(0, -largeCircleRadius, 0, -lineLength * 2, strokePaint);
        canvas.drawCircle(0, -lineLength * 3, largeCircleRadius, strokePaint);

        drawTopRightArc(canvas);

        canvas.translate(0, -lineLength * 3);
        canvas.rotate(-30);
        canvas.drawText("TopRight", 0, 0 - textOffsetY, textPaint);

        canvas.restore();


    }

    private void drawTopRightArc(Canvas canvas) {
        canvas.save();
        canvas.translate(0, -lineLength * 3);
        canvas.rotate(-30);

        float arcRadius = ARC_RADIU * size;
        RectF oval = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);

        arcPaint.setStyle(Paint.Style.FILL);
        arcPaint.setColor(0x55EC6941);
        canvas.drawArc(oval, -22.5F, -135, true, arcPaint);


        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setColor(Color.WHITE);
        arcPaint.setStrokeWidth(8);
        canvas.drawArc(oval, -22.5F, -135, false, arcPaint);



        canvas.rotate(-135 / 2);

        for (float i = 0; i < 5 * 33.75f; i+=33.75) {
            canvas.save();
            canvas.rotate(i);
            canvas.drawText("A-1", 0, -arcRadius-space, textPaint);

            canvas.restore();
        }


        canvas.restore();
    }

    private void drawBottomLeft(Canvas canvas) {
        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.rotate(-100);
        canvas.drawLine(0, -largeCircleRadius - space, 0, -lineLength * 2 - space, strokePaint);
        canvas.drawCircle(0, -lineLength * 2 - smallCircleRadiu - space * 2, smallCircleRadiu, strokePaint);
//        canvas.drawText("哈哈哈",0,-lineLength * 2- smallCircleRadiu -space*2-textOffsetY,textPaint);
        canvas.translate(0, -lineLength * 2 - smallCircleRadiu - space * 2);
        canvas.rotate(100);
        canvas.drawText("Left", 0, 0 - textOffsetY, textPaint);
        canvas.restore();
    }

    private void drawBottom(Canvas canvas) {
        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.rotate(180);
        canvas.drawLine(0, -largeCircleRadius - space, 0, -lineLength * 2 - space, strokePaint);
        canvas.drawCircle(0, -lineLength * 2 - smallCircleRadiu - space * 2, smallCircleRadiu, strokePaint);
        canvas.translate(0, -lineLength * 2 - smallCircleRadiu - space * 2);
        canvas.rotate(-180);
        canvas.drawText("Bottom", 0, 0 - textOffsetY, textPaint);
        canvas.restore();


    }

    private void drawBottomRight(Canvas canvas) {
        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.rotate(100);
        canvas.drawLine(0, -largeCircleRadius - space, 0, -lineLength * 2 - space, strokePaint);
        canvas.drawCircle(0, -lineLength * 2 - smallCircleRadiu - space * 2, smallCircleRadiu, strokePaint);
//        canvas.drawText("drawBottomRight",0,-lineLength * 2- smallCircleRadiu -space*2-textOffsetY,textPaint);


        canvas.translate(0, -lineLength * 2 - smallCircleRadiu - space * 2);
        canvas.rotate(-100);
        canvas.drawText("Right", 0, 0 - textOffsetY, textPaint);

        canvas.restore();
    }


}

