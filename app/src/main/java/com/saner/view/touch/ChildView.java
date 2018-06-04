package com.saner.view.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.saner.util.LogUtil;

/**
 * Created by sunset on 2018/6/1.
 */

public class ChildView extends View{
    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.logd("ChildView     dispatchTouchEvent");
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.logd("ChildView     onTouchEvent");
        return false;
    }


}
