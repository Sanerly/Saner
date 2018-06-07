package com.saner.view.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.saner.R;
import com.saner.util.LogUtil;
import com.saner.util.MeasureUtil;

/**
 * Created by sunset on 2018/6/5.
 */

public class ImageScaleView extends View{
    public ImageScaleView(Context context) {
        this(context,null);
    }

    public ImageScaleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    Bitmap mBitmap;

//    RectF mRectF;
    Matrix mMatrix;
    private void init(Context context) {
        mBitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.icon_photo);


        int size=MeasureUtil.getScreenSize(context)[0];
//        mRectF=new RectF(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        mMatrix=new Matrix();
        float sx= (float)864/mBitmap.getWidth();
        LogUtil.loge("sx = "+sx);
        mMatrix.postScale(sx,sx);

//
//        float[] values = new float[9];
//        mMatrix.getValues(values);


//        LogUtil.loge("sx = "+(values[0]*mBitmap.getWidth()));

//        mRectF=new RectF(0,0,mBitmap.getWidth()*values[0],mBitmap.getHeight()*values[0]);
//        mMatrix.mapRect(mRectF);


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.loge("id = "+v.getId());
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap,mMatrix,null);
    }
}
