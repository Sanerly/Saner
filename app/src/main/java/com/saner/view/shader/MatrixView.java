package com.saner.view.shader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.saner.R;
import com.saner.util.LogUtil;
import com.saner.util.MeasureUtil;

import java.util.Arrays;

/**
 * Created by sunset on 2018/5/11.
 * 矩阵练习
 */

public class MatrixView extends View{
    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private Paint mPaint;
    private Bitmap bitmap;
    private int centerX,centerY;
    private int left,top,right,bottom;

    private int RECT_SIZE=400;
    private void init(Context context) {
        int [] centerSize=MeasureUtil.getScreenSize((Activity) context);
        centerX=centerSize[0]/2;
        centerY=centerSize[1]/2;

        left=centerX-RECT_SIZE;
        top=centerY-RECT_SIZE;
        right=centerX+RECT_SIZE;
        bottom=centerY+RECT_SIZE;

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.icon_photo);

        BitmapShader bitmapShader=new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        Matrix matrix=new Matrix();

//        matrix.setTranslate(-200,500);
        matrix.postRotate(5);
//        matrix.preRotate(5);

       ;
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        /*
 * 新建一个9个单位长度的浮点数组
 * 因为我们的Matrix矩阵是9个单位长的对吧
 */
        float[] fs = new float[9];

// 将从matrix中获取到的浮点数组装载进我们的fs里
        matrix.getValues(fs);

        LogUtil.logd("矩阵数组 = "+ Arrays.toString(fs));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0,0,centerX*2,centerY*2,mPaint);
    }
}
