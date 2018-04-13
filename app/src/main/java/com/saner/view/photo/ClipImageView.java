package com.saner.view.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.saner.util.LogUtil;
import com.saner.util.SelPhotoUtil;

/**
 * Created by sunset on 2018/4/11.
 */

public class ClipImageView extends android.support.v7.widget.AppCompatImageView {
    private Context mContext;

    private float mLastX;
    private float mLastY;
    private int mActivePointerId;

    private RectF mBorderRect;
    //测试时使用的大小
    private int spec;

    private float mScale = 1;

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

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        //设置正方形大小
        setBorderRect(new RectF(centerX - spec, centerY - spec, centerX + spec, centerY + spec));
//        setScaleType(ScaleType.CENTER_CROP);
//        setPostCenter();
    }



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
        if (mBorderRect != null && rectF != null) {

            if (moveX > 0) {
                if (rectF.left + moveX > mBorderRect.left) {
                    moveX = mBorderRect.left - rectF.left;
                }
            } else {
                if (rectF.right + moveX < mBorderRect.right) {
                    moveX = mBorderRect.right - rectF.right;
                }
            }
            if (moveY > 0) {
                if (rectF.top + moveY > mBorderRect.top) {
                    moveY = mBorderRect.top - rectF.top;
                }
            } else {
                if (rectF.bottom + moveY < mBorderRect.bottom) {
                    moveY = mBorderRect.bottom - rectF.bottom;
                }
            }
        }
        //通过postTranslate方法就可以移动到相应的位置
        getImageMatrix().postTranslate(moveX, moveY);
//        LogUtil.logd("moveX = "+moveX+" moveY = "+moveY);
        //重画视图
        invalidate();

    }

    /**
     * 获取图片自己的边界
     */
    private RectF getCurrentRectF() {
        if (getDrawable() == null) {
            LogUtil.logd("image resource is null");
            return null;
        }
        int w = getDrawable().getIntrinsicWidth();
        int h = getDrawable().getIntrinsicHeight();
        RectF rectF = new RectF(0, 0,w, h);
        getImageMatrix().mapRect(rectF);
        return rectF;
    }


    /**
     * 在屏幕中心显示,这里来自于ImageView的源码
     */
    private void setPostCenter() {
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
                LogUtil.logd("vwidth = "+vwidth);
                float scale;
                float dx = 0, dy = 0;

                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight;
                    dx = (vwidth - dwidth * scale) * 0.5f;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                    dy = (vheight - dheight * scale) * 0.5f;
                }
                Matrix matrix= new Matrix();
                matrix.setScale(scale, scale);
                matrix.postTranslate(Math.round(dx), Math.round(dy));
                setImageMatrix(matrix);
                mScale = scale;
            }
        });

    }

    /***
     * 获取边框的边界
     * @return
     */
    public RectF getBorderRect() {
        return mBorderRect;
    }
    /***
     * 设置边框的边界
     * @return
     */
    public void setBorderRect(RectF mBorderRect) {
        this.mBorderRect = mBorderRect;
    }

    /**
     * 设置需要裁剪的大小
     * @param rate
     */
    public void setSpec(@FloatRange(from = 0.0, to = 1.0) float rate) {
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        spec = (int) (screenWidth / 2 * rate);
    }

    /**
     * 裁剪图片
     */
    public Bitmap clip() {
        if (getDrawable() == null) {
            LogUtil.loge("image resource is null");
            return null;
        }

        Bitmap bitmap = SelPhotoUtil.drawable2Bitmap(getDrawable());
        //以下所有步骤的思路，均是将点或者大小还原到加载图片大小比例后，再进行处理。
        //获取裁剪区域的实际长宽==裁剪框的大小
        int clipWidth = (int) (getClipWidth() /mScale);
        int clipHeight = (int) (getClipHeight()/mScale);

        //重新计算得出最终裁剪起始点
        int clipLeft = (int) (bitmap.getWidth() / 2 -clipWidth/2-getActuallyScrollX()/mScale);
        int clipTop = (int) (bitmap.getHeight() / 2 -clipHeight/2-getActuallyScrollY()/mScale);

       //其中width与height是最终实际裁剪的图片大小,saveBitmap就是最终裁剪的图片
        Bitmap saveBitmap = Bitmap.createBitmap(clipWidth, clipHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(saveBitmap);
        //当裁剪超出图片边界，超出区域以颜色填充
        canvas.drawColor(Color.RED);
        //计算显示与实际裁剪的大小
        int showRight = clipWidth;
        int showBottom = clipHeight;
        int cropRight = clipLeft+clipWidth;
        int cropBottom =  clipTop+clipHeight;
        //裁剪超出图片边界超出边界
        if(cropRight>bitmap.getWidth()){
            cropRight = bitmap.getWidth();
            showRight = bitmap.getWidth()-clipLeft;
        }
        if(cropBottom>bitmap.getHeight()){
            cropBottom = bitmap.getHeight();
            showBottom = bitmap.getHeight()-clipTop;
        }
        Rect cropRect = new Rect(clipLeft,clipTop,cropRight,cropBottom);
        Rect showRect = new Rect(0,0,showRight,showBottom);
        canvas.drawBitmap(bitmap,cropRect,showRect,new Paint());
        return saveBitmap;
    }


    /**
     * 获取移动图片后的偏移量
     */
    public float getActuallyScrollY() {
        return getScrollY() +getCenterOffsetScrollY();
    }

    /**
     * 获取移动图片后的偏移量
     */
    public float getActuallyScrollX() {
        return getScrollX()+getCenterOffsetScrollX();
    }


    /**
     * 获取裁剪框的中心点和图片的中心的偏移量
     * @return X 轴的偏移量
     */
    public float getCenterOffsetScrollX() {
        int sourceCenterX = getWidth()/2;
        int afterCenterX = (int) getCurrentRectF().centerX();
        return (float) (afterCenterX-sourceCenterX);
    }


    /**
     * 获取裁剪框的中心点和图片的中心的偏移量
     * @return Y 轴的偏移量
     */
    public float getCenterOffsetScrollY() {
        double sourceCenterY = getHeight()/2;
        double afterCenterY = getCurrentRectF().centerY();
        return (float) (afterCenterY-sourceCenterY);
    }

    /**
     * 获取实际裁剪的宽度
     */
    public float getClipWidth() {
        if (getBorderRect() == null) {
            return 0;
        }
        return getBorderRect().width();
    }

    /**
     * 获取实际裁剪的高度
     */
    public float getClipHeight() {
        if (getBorderRect() == null) {
            return 0;
        }
        return getBorderRect().height();
    }
}
