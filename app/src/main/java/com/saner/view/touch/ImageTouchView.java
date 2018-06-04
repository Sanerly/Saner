package com.saner.view.touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.saner.R;

/**
 * Created by sunset on 2018/6/4.
 */

public class ImageTouchView extends View {
    public ImageTouchView(Context context) {
        this(context, null);
    }

    public ImageTouchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    Matrix matrix;
    Bitmap bitmap;
    RectF rectF;

    PointF lastPoint = new PointF();

    private void init(Context context) {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_photo);

        rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        matrix = new Matrix();
    }

    boolean isCanMove = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                if (event.getPointerId(event.getActionIndex()) == 0 && rectF.contains(event.getX(), event.getY())) {
                    isCanMove = true;
                    lastPoint.set(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isCanMove) {

                    int index=event.findPointerIndex(0);

                    matrix.postTranslate(event.getX(index) - lastPoint.x, event.getY(index) - lastPoint.y);
                    lastPoint.set(event.getX(index), event.getY(index));

                    rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    matrix.mapRect(rectF);

                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    isCanMove = false;
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, matrix, null);
    }
}
