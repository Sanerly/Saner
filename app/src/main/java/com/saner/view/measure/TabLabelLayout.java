package com.saner.view.measure;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.saner.util.LogUtil;

/**
 * Created by sunset on 2018/5/25.
 */

public class TabLabelLayout extends ViewGroup {
    public TabLabelLayout(Context context) {
        this(context, null);
    }

    public TabLabelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLabelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /* 声明临时变量存储父容器的期望值 该值应该等于父容器的内边距加上所有子元素的测量宽高和外边距*/
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;
        int childMeasureState = 0;

        int layoutWidth = getWidth();
        if (getChildCount() > 0) {

            for (int i = 0; i < getChildCount(); i++) {

                View childView = getChildAt(i);

                if (childView.getVisibility() != GONE) {
                    measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);

                    int childMeasureSize = Math.max(childView.getMeasuredWidth(), childView.getMeasuredHeight());

                    int childMeasureSpec = MeasureSpec.makeMeasureSpec(childMeasureSize, MeasureSpec.EXACTLY);

                    childView.measure(childMeasureSpec, childMeasureSpec);

                    MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                    int childWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                    int childHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                    parentDesireHeight = Math.max(parentDesireHeight, childHeight);
                    if (parentDesireWidth >= layoutWidth) {
                        parentDesireWidth = 0;
                        parentDesireHeight += childHeight;
                    }

                    parentDesireWidth += childWidth;


                    childMeasureState = combineMeasuredStates(childMeasureState, childView.getMeasuredState());
                }


            }

            parentDesireWidth += getPaddingLeft() + getPaddingRight();
            parentDesireHeight += getPaddingTop() + getPaddingBottom();


            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());


        }

        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, childMeasureState),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, childMeasureState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int layoutWidth = 0;
        int layoutHeight = 0;

        if (getChildCount() > 0) {
            int mutil = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);

                if (childView.getVisibility() != GONE) {

                    MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight();
                    LogUtil.logd("childHeight =" + childHeight);
                    LogUtil.logd("layoutHeight 1=" + layoutHeight);
                    layoutHeight = Math.max(childHeight, layoutHeight);
                    LogUtil.logd("layoutHeight 2=" + layoutHeight);
                    if (layoutWidth + childWidth+params.leftMargin+params.rightMargin > getWidth()) {
                        layoutWidth = 0;
                        layoutHeight += childHeight+params.topMargin+params.bottomMargin;
                    }

                    int left = getPaddingLeft() + params.leftMargin + layoutWidth;
                    int top = getPaddingTop() + params.topMargin + layoutHeight;
                    int right = getPaddingRight() + getPaddingLeft() + params.leftMargin + params.rightMargin + layoutWidth + childWidth;
                    int bottom = getPaddingBottom() + getPaddingTop() + params.bottomMargin + params.topMargin + layoutHeight + childHeight;

                    childView.layout(left, top, right, bottom);

                    layoutWidth += childWidth+params.leftMargin+params.rightMargin;
                }


            }


        }


    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
