package com.saner.view.measure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sunset on 2018/5/23.
 */

public class SquareLayout extends ViewGroup {

    private static final int ORIENTATION_HORIZONTAL = 0, ORIENTATION_VERTICAL = 1;// 排列方向的常量标识值
    private static final int DEFAULT_MAX_ROW = Integer.MAX_VALUE, DEFAULT_MAX_COLUMN = Integer.MAX_VALUE;// 最大行列默认值

    private int mMaxRow = DEFAULT_MAX_ROW;// 最大行数
    private int mMaxColumn = DEFAULT_MAX_COLUMN;// 最大列数

    private int mOrientation = ORIENTATION_VERTICAL;// 排列方向默认横向

    public SquareLayout(Context context) {
        this(context, null);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        // 初始化最大行列数
        mMaxRow = mMaxColumn = 2;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

       /* 声明临时变量存储父容器的期望值
         该值应该等于父容器的内边距加上所有子元素的测量宽高和外边距*/
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;


        // 声明临时变量存储子元素的测量状态
        int childMeasureState = 0;


        if (getChildCount() > 0) {

            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);

                if (childView.getVisibility() != GONE) {
                    // 测量子元素并考量其外边距
                    measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);

                    // 比较子元素测量宽高并比较取其较大值
                    int childMeasureSize = Math.max(childView.getMeasuredHeight(), childView.getMeasuredWidth());

                    // 重新封装子元素测量规格
                    int childMeasureSpec = MeasureSpec.makeMeasureSpec(childMeasureSize, MeasureSpec.EXACTLY);

                    // 重新测量子元素
                    childView.measure(childMeasureSpec, childMeasureSpec);

                    // 获取子元素布局参数

                    MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                    int childActualWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                    int childActualHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;


                    if (mOrientation == ORIENTATION_HORIZONTAL) {

                        parentDesireWidth += childActualWidth;

                        parentDesireHeight = Math.max(parentDesireHeight, childActualHeight);

                    } else if (mOrientation == ORIENTATION_VERTICAL) {

                        parentDesireHeight += childActualHeight;

                        parentDesireWidth = Math.max(parentDesireWidth, childActualWidth);
                    }
                    // 合并子元素的测量状态
                    childMeasureState = combineMeasuredStates(childMeasureState, childView.getMeasuredState());

                }

            }

            //考量父容器内边距将其累加到期望值
            parentDesireWidth += getPaddingLeft() + getPaddingRight();

            parentDesireHeight += getPaddingTop() + getPaddingBottom();


            //尝试比较父容器期望值与Android建议的最小值大小并取较大值

            parentDesireWidth = Math.max(parentDesireWidth, getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());


        }
        // 确定父容器的测量宽高

        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, childMeasureState),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, childMeasureState << MEASURED_HEIGHT_STATE_SHIFT));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (getChildCount()>0){
            // 声明临时变量存储宽高倍增值
            int mutil=0;

            for (int i = 0; i < getChildCount(); i++) {
                View childView=getChildAt(i);

                if (childView.getVisibility()!=GONE){

                    MarginLayoutParams params= (MarginLayoutParams) childView.getLayoutParams();

                    int childActualSize=childView.getMeasuredWidth();


                    if (mOrientation==ORIENTATION_HORIZONTAL){

                        childView.layout(getPaddingLeft()+mutil+params.leftMargin,getPaddingTop()+params.topMargin,
                                getPaddingLeft()+childActualSize+params.leftMargin+mutil,childActualSize+getPaddingTop()+params.topMargin);

                        mutil+=childActualSize+params.leftMargin+params.rightMargin;
                    }else if (mOrientation==ORIENTATION_VERTICAL){

                        childView.layout(getPaddingLeft()+params.leftMargin,getPaddingTop()+mutil+params.topMargin,
                                getPaddingLeft()+params.leftMargin+childActualSize,getPaddingTop()+params.topMargin+childActualSize+mutil);

                        mutil+=childActualSize+params.topMargin+params.bottomMargin;
                    }


                }

            }

        }

    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
