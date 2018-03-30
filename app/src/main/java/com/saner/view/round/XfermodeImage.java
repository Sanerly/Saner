package com.saner.view.round;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by sunset on 2018/3/19.
 */

public class XfermodeImage extends BaseXfermodeImageView{
    public XfermodeImage(Context context) {
        super(context);
    }

    public XfermodeImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XfermodeImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Bitmap getBitmap() {

        Bitmap bitmap=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        float size=Math.min(getWidth(),getHeight())*1.0f;
//        canvas.drawCircle(size / 2,size / 2, size / 2f, paint);

//        canvas.drawRoundRect(new RectF(0,0,size,size),50,50,paint);
//        canvas.drawOval(new RectF(0,0,size,size),paint);

        canvas.drawArc(new RectF(0,0,size,size),0,360,true,paint);
        return bitmap;
    }
}
