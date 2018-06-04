package com.saner.view.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.saner.util.LogUtil;

/**
 * Created by sunset on 2018/6/4.
 */

public class MotionView extends View{
    public MotionView(Context context) {
        super(context);
    }

    public MotionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MotionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int index=event.getActionIndex();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.loge("ACTION_DOWN 第1个手指按下");

                break;
            case MotionEvent.ACTION_UP:
                LogUtil.loge("ACTION_UP 最后1个手指抬起");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                LogUtil.loge("ACTION_POINTER_DOWN 第"+(index+1)+"个手指按下");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                LogUtil.loge("ACTION_POINTER_DOWN 第"+(index+1)+"个手指抬起");
                break;
        }

        return true;
    }
}

