package com.saner.view.xfermode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.saner.util.LogUtil;

/**
 * Created by sunset on 2018/5/11.
 */

public class RecordCircleView extends View {
    public RecordCircleView(Context context) {
        this(context, null);
    }

    public RecordCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint mForeignPaint;
    private Paint mInnerPaint;
    private Paint mArcPaint;
    private int mForeignRadius = 150;
    private int mInnerRadius = 100;
    private int mRate = 0;
    private double mSweepAngle = 0;
    private int centerX, centerY;
    private RectF mArcRectF;
    private int mArcStrokeWidth = 15;


    private void init(Context context) {

        mForeignPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mForeignPaint.setStyle(Paint.Style.FILL);
        mForeignPaint.setColor(Color.DKGRAY);

        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setColor(Color.WHITE);

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcStrokeWidth);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setColor(Color.GREEN);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = w / 2;
        centerY = h / 2;

        mArcRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = mForeignRadius + mRate;
        canvas.drawCircle(centerX, centerY, radius, mForeignPaint);
        canvas.drawCircle(centerX, centerY, mInnerRadius - mRate, mInnerPaint);


        mArcRectF.left = centerX - radius + mArcStrokeWidth / 2;
        mArcRectF.top = centerY - radius + mArcStrokeWidth / 2;
        mArcRectF.right = radius * 2 + (centerX - radius - mArcStrokeWidth / 2);
        mArcRectF.bottom = radius * 2 + (centerY - radius - mArcStrokeWidth / 2);


        canvas.drawArc(mArcRectF, 0, (float) (365 * mSweepAngle), false, mArcPaint);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.logd("按下");
                mRate = 40;
//                invalidate();
                handler.sendMessage(handler.obtainMessage(0));
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.logd("移动" + mSweepAngle);
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.logd("抬起");
                mRate = 0;
                mSweepAngle = 0;
                handler.removeMessages(0);
                invalidate();
                break;
        }
        return true;
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (mSweepAngle <= 1) {
                        mSweepAngle = mSweepAngle + 0.01;
                        postInvalidate();
                        handler.sendMessageDelayed(handler.obtainMessage(0), 70);
                    }
                    break;
            }
            return false;
        }

    });


}
