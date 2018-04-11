package com.saner.view.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.saner.util.LogUtil;

/**
 * Created by sunset on 2018/4/11.
 */

public class ClipImageView extends android.support.v7.widget.AppCompatImageView {
    private Context mContext;

    private float mLastX;
    private float mLastY;
    private RectF mRestrictRect;
    //测试时使用的大小
    private int spec;

    public ClipImageView(Context context) {
        this(context, null);
    }

    public ClipImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        postCenter();
        spec = getSpec(ClipBorderView.SPEC);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        //测试时使用的大小
        setRestrictRect(new RectF(centerX - spec, centerY - spec, centerX + spec, centerY + spec));
    }


    int mActivePointerId;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int activePointerIndex = event.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
                    return true;
                }
                float mx = event.getX(activePointerIndex);
                float my = event.getY(activePointerIndex);
                drag(mx, my);
                mLastX = mx;
                mLastY = my;
                break;
        }
        return true;
    }

    //motionX,motionY为当前触摸的坐标
    public void drag(float motionX, float motionY) {
        //mLastY,mLastX 为上一次触摸的坐标
        float moveX = motionX - mLastX;
        float moveY = motionY - mLastY;

        RectF rectF = getCurrentRectF();
        //边界限制
        if (mRestrictRect != null && rectF!=null) {

            if (moveX > 0) {
                if (rectF.left + moveX > mRestrictRect.left) {
                    moveX = mRestrictRect.left - rectF.left;
                }
            } else {
                if (rectF.right + moveX < mRestrictRect.right) {
                    moveX = mRestrictRect.right - rectF.right;
                }
            }
            if (moveY > 0) {
                if (rectF.top + moveY > mRestrictRect.top) {
                    moveY = mRestrictRect.top - rectF.top;
                }
            } else {
                if (rectF.bottom + moveY < mRestrictRect.bottom) {
                    moveY = mRestrictRect.bottom - rectF.bottom;
                }
            }
        }
        //通过postTranslate方法就可以移动到相应的位置
        getImageMatrix().postTranslate(moveX, moveY);
        //重画视图
        invalidate();

    }

    /**
     * 获取自己的边界
     *
     * @return
     */
    private RectF getCurrentRectF() {
        if (getDrawable() == null) {
            LogUtil.logd("没有设置图片资源");
            return null;
        }
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();

        Bitmap bitmap = drawable.getBitmap();
        RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        getImageMatrix().mapRect(rectF);
        return rectF;
    }


    /**
     * 在屏幕中心显示,这里来自于ImageView的源码
     */
    private void postCenter() {
        post(new Runnable() {
            @Override
            public void run() {
                if (getDrawable() == null) {
                    return;
                }
                final int dwidth = getDrawable().getIntrinsicWidth();
                final int dheight = getDrawable().getIntrinsicHeight();

                final int vwidth = getWidth() - getPaddingLeft() - getPaddingRight();
                final int vheight = getHeight() - getPaddingTop() - getPaddingBottom();
                float scale;
                float dx = 0, dy = 0;

                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight;
                    dx = (vwidth - dwidth * scale) * 0.5f;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                    dy = (vheight - dheight * scale) * 0.5f;
                }
                Matrix matrix = new Matrix();
                matrix.setScale(scale, scale);
                matrix.postTranslate(Math.round(dx), Math.round(dy));
                setImageMatrix(matrix);
//                mScale = scale;
            }
        });

    }


    public void setRestrictRect(RectF restrictRect) {
        this.mRestrictRect = restrictRect;
    }


    public int getSpec(@FloatRange(from = 0.0, to = 1.0) float rate) {
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        return (int) (screenWidth / 2 * rate);
    }
}
