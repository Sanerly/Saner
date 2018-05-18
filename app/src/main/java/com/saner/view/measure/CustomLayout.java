package com.saner.view.measure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sunset on 2018/5/18.
 */

public class CustomLayout extends ViewGroup{
    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount()>0){
            measureChildren(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        // 获取父容器内边距
        int parentPaddingLeft = getPaddingLeft();
        int parentPaddingTop = getPaddingTop();

        if (getChildCount()>0){
            int mutilHeight=0;
            for (int i = 0; i < getChildCount(); i++) {
                View child=getChildAt(i);

                child.layout(parentPaddingLeft,parentPaddingTop+mutilHeight,child.getMeasuredWidth()+parentPaddingLeft,child.getMeasuredHeight()+mutilHeight+parentPaddingTop);

                mutilHeight+=child.getMeasuredHeight();
            }
        }
    }
}
