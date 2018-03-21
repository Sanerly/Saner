package com.saner.view.round;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by sunset on 2018/3/19.
 */

public class ShaderImage extends BaseShaderImageView {
    public ShaderImage(Context context) {
        super(context);
    }

    public ShaderImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShaderImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected float getScale(Bitmap bitmap) {
        return (mSize * 1.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth());
    }


    @Override
    protected void onDrawBitmap(Canvas canvas, Paint paint) {
//        canvas.drawCircle(mSize/2, mSize/2,mSize/2, paint);
        canvas.drawRoundRect(new RectF(0,0,mSize,mSize),30,30,paint);
//        canvas.drawOval(new RectF(0,0,mSize,mSize),paint);
//        canvas.drawArc(new RectF(0,0,mSize,mSize),0,180,true,paint);
    }

}
