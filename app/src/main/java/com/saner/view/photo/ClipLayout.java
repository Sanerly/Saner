package com.saner.view.photo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.saner.R;


public class ClipLayout extends FrameLayout{
    private Context mContext;
    private ClipImageView mClipImage;
    private ClipBorderView mClipBorder;

    public ClipLayout(@NonNull Context context) {
        this(context,null);
    }

    public ClipLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClipLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext=context;

        mClipImage=new ClipImageView(mContext);
        mClipBorder=new ClipBorderView(mContext);
        setAttributes();
        addView(mClipImage);
        addView(mClipBorder);
    }

    private void setAttributes() {
        setBackgroundColor(Color.BLACK);
        setImageRes(R.drawable.icon_photo);
    }
    private void setChildLayout(View childLayout){
        FrameLayout.LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        childLayout.setLayoutParams(params);
    };

    /**
     * 设置图片资源
     * @param resId
     */
    public void setImageRes(int resId){
        if (mClipImage!=null){
            mClipImage.setImageResource(resId);
        }
    }
}
