package com.saner.view.xfermode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sunset on 2018/5/10.
 */

public class CircleView extends View{

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private Paint paint;
    private RectF rectF;
    private void init(Context context) {

        paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);



    }
    int count;
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (count<200){
            canvas.drawCircle(getWidth()/2,getHeight()/2,100+count,paint);
        }else {
            count=0;
        }
        count++;
        invalidate();
    }
}
