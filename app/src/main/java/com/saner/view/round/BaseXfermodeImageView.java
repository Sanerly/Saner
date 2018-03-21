package com.saner.view.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by sunset on 2018/3/19.
 */

@SuppressLint("AppCompatCustomView")
public abstract class BaseXfermodeImageView extends ImageView {

    private PorterDuffXfermode mXfermode;

    private Paint mPaint;

    private Bitmap mDstbitmap;
    private WeakReference<Bitmap> mSrcbitmap;

    public BaseXfermodeImageView(Context context) {
        this(context, null);
    }

    public BaseXfermodeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseXfermodeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
    }

    @Override
    public void invalidate() {
        mSrcbitmap = null;
        if (mDstbitmap != null) {
            mDstbitmap.recycle();
        }
        super.invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInEditMode()) {
            int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            Bitmap bitmap = mSrcbitmap != null ? mSrcbitmap.get() : null;
            if (bitmap == null || bitmap.isRecycled()) {
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas bitCanvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, getWidth(), getHeight());
                    drawable.draw(bitCanvas);

                    mPaint.reset();
                    mPaint.setFilterBitmap(false);
                    mPaint.setXfermode(mXfermode);

                    if (mDstbitmap == null || mDstbitmap.isRecycled()) {
                        mDstbitmap = getBitmap();
                    }

                    bitCanvas.drawBitmap(mDstbitmap,0,0, mPaint);

                    mSrcbitmap=new WeakReference<>(bitmap);
                }
            }
            if (bitmap!=null){
                mPaint.setXfermode(null);
                canvas.drawBitmap(bitmap,0f,0f,mPaint);
            }
            canvas.restoreToCount(saveCount);
        } else {
            super.onDraw(canvas);
        }
    }

    protected abstract Bitmap getBitmap();



}
