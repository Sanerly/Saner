package com.saner.view.touch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.saner.util.LogUtil;

/**
 * Created by sunset on 2018/6/1.
 */

public class TouchViewGroup extends FrameLayout{
    public TouchViewGroup(@NonNull Context context) {
        super(context);
    }

    public TouchViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.logd("TouchViewGroup     dispatchTouchEvent = "+super.dispatchTouchEvent(ev));
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.logd("TouchViewGroup     onInterceptTouchEvent");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.logd("TouchViewGroup     onTouchEvent");
        return true;
    }
}
