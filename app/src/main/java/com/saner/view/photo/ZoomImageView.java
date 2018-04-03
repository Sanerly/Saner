package com.saner.view.photo;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * Created by sunset on 2018/4/3.
 */

public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener {

    public static final float SCALE_MAX = 4.0f;
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];
    private final Matrix mScaleMatrix = new Matrix();


    private boolean once = true;

    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector = null;





    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    Context mContext;
    Paint mPaint;

    private void init(Context context) {
        mScaleGestureDetector=new ScaleGestureDetector(context,this);
    }

    /*************************ScaleGestureDetector************************************/
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor=detector.getScaleFactor();

        if (getDrawable()==null){
            return true;
        }

        /**
         * 缩放的范围控制
         */
        if ((scale<SCALE_MAX && scaleFactor>1.0f) || (scale > initScale && scaleFactor<1.0f)){

        }

        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 获得当前缩放比例
     */
    public float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }
}
