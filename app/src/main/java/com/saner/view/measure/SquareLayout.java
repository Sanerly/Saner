package com.saner.view.measure;

import android.annotation.SuppressLint;
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

    private int mOrientation = ORIENTATION_HORIZONTAL;// 排列方向默认横向

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
//        mMaxRow = mMaxColumn = 2;
        mMaxRow = 6;
        mMaxColumn =3;
    }

    @SuppressLint("DrawAllocation")
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

            int[] childWidth = new int[getChildCount()];
            int[] childHeight = new int[getChildCount()];

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

//                    int childActualWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
//                    int childActualHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
//
//                    if (mOrientation == ORIENTATION_HORIZONTAL) {
//
//                        parentDesireWidth += childActualWidth;
//
//                        parentDesireHeight = Math.max(parentDesireHeight, childActualHeight);
//
//                    } else if (mOrientation == ORIENTATION_VERTICAL) {
//
//                        parentDesireHeight += childActualHeight;
//
//                        parentDesireWidth = Math.max(parentDesireWidth, childActualWidth);
//                    }


                    childWidth[i] = childView.getMeasuredWidth() + params.topMargin + params.rightMargin;
                    childHeight[i] = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;

                    // 合并子元素的测量状态
                    childMeasureState = combineMeasuredStates(childMeasureState, childView.getMeasuredState());

                }

            }

            // 声明临时变量存储行/列宽高
            int indexMultiWidth = 0, indexMultiHeight = 0;


            if (mOrientation == ORIENTATION_HORIZONTAL) {
                //如果子元素数量大于限定值则进行折行计算
                if (getChildCount() > mMaxColumn) {
                    // 计算产生的行数
                    int row = getChildCount() / mMaxColumn;
                    // 计算余数
                    int remainder = getChildCount() % mMaxColumn;

                    // 声明临时变量存储子元素宽高数组下标值  
                    int index = 0;

                    //遍历数组计算父容器期望宽高值
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < mMaxColumn; j++) {
                            // 单行宽度累加
                            indexMultiWidth += childWidth[index];
                            // 单行高度取最大值
                            indexMultiHeight = Math.max(indexMultiHeight, childHeight[index++]);
                        }
                        // 每一行遍历完后将该行宽度与上一行宽度比较取最大值
                        parentDesireWidth = Math.max(indexMultiWidth, parentDesireWidth);

                        // 每一行遍历完后累加各行高度
                        parentDesireHeight += indexMultiHeight;

                        // 重置参数
                        indexMultiWidth = indexMultiHeight = 0;

                    }
                    /*
                     * 如果有余数表示有子元素未能占据一行
                     */
                    if (remainder != 0) {
                             /*
                         * 遍历剩下的这些子元素将其宽高计算到父容器期望值
                         */
                        for (int i = getChildCount() - remainder; i < getChildCount(); i++) {
                            indexMultiWidth += childWidth[i];

                            indexMultiHeight = Math.max(indexMultiHeight, childHeight[i]);

                        }

                        parentDesireWidth = Math.max(indexMultiWidth, parentDesireWidth);
                        parentDesireHeight += indexMultiHeight;

                        indexMultiWidth = indexMultiHeight = 0;
                    }


                } else {
                    //如果子元素数量还没有限制值大那么直接计算即可不须折行

                    for (int i = 0; i < getChildCount(); i++) {
//                        parentDesireWidth+=childWidth[i];
//
//                        parentDesireHeight=Math.max(parentDesireHeight,childHeight[i]);

                        // 累加子元素的实际高度
                        parentDesireHeight += childHeight[i];

                        // 获取子元素中宽度最大值
                        parentDesireWidth = Math.max(parentDesireWidth, childWidth[i]);
                    }
                }


            } else if (mOrientation == ORIENTATION_VERTICAL) {
                if (getChildCount() > mMaxRow) {
                    int column = getChildCount() / mMaxRow;
                    int remainder = getChildCount() % mMaxRow;
                    int index = 0;

                    for (int x = 0; x < column; x++) {
                        for (int y = 0; y < mMaxRow; y++) {
                            indexMultiHeight += childHeight[index];
                            indexMultiWidth = Math.max(indexMultiWidth, childWidth[index++]);
                        }
                        parentDesireHeight = Math.max(parentDesireHeight, indexMultiHeight);
                        parentDesireWidth += indexMultiWidth;
                        indexMultiWidth = indexMultiHeight = 0;
                    }

                    if (remainder != 0) {
                        for (int i = getChildCount() - remainder; i < getChildCount(); i++) {
                            indexMultiHeight += childHeight[i];
                            indexMultiWidth = Math.max(indexMultiHeight, childWidth[i]);
                        }
                        parentDesireHeight = Math.max(parentDesireHeight, indexMultiHeight);
                        parentDesireWidth += indexMultiWidth;
                        indexMultiWidth = indexMultiHeight = 0;
                    }
                } else {
                    for (int i = 0; i < getChildCount(); i++) {
                        // 累加子元素的实际宽度
                        parentDesireWidth += childWidth[i];

                        // 获取子元素中高度最大值
                        parentDesireHeight = Math.max(parentDesireHeight, childHeight[i]);
                    }
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

        if (getChildCount() > 0) {
            // 声明临时变量存储宽高倍增值
            int mutil = 0;

            // 指数倍增值
            int indexMulti = 1;

            // 声明临时变量存储行/列宽高
            int indexMultiWidth = 0, indexMultiHeight = 0;

            // 声明临时变量存储行/列临时宽高
            int tempHeight = 0, tempWidth = 0;


            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);

                if (childView.getVisibility() != GONE) {

                    MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                    int childActualSize = childView.getMeasuredWidth();


                    if (mOrientation == ORIENTATION_HORIZONTAL) {
                          /*
                         * 如果子元素数量比限定值大
                         */
                        if (getChildCount() > mMaxColumn) {

                               /*
                             * 根据当前子元素进行布局
                             */

                            if (i < mMaxColumn * indexMulti) {

                                childView.layout(getPaddingLeft() + params.leftMargin + indexMultiWidth, getPaddingTop() + params.topMargin + indexMultiHeight,
                                        childActualSize + getPaddingLeft() + params.leftMargin + indexMultiWidth, childActualSize + getPaddingTop()
                                                + params.topMargin + indexMultiHeight);


                                indexMultiWidth += childActualSize + params.leftMargin + params.rightMargin;
                                tempHeight = Math.max(tempHeight, childActualSize) + params.topMargin + params.bottomMargin;
                                /*
                                 * 如果下一次遍历到的子元素下标值大于限定值
                                 */

                                if (i + 1 >= mMaxColumn * indexMulti) {
                                    // 那么累加高度到高度倍增值
                                    indexMultiHeight += tempHeight;

                                    // 重置宽度倍增值
                                    indexMultiWidth = 0;
                                    // 增加指数倍增值
                                    indexMulti++;
                                }

                            }


                        } else {
                            childView.layout(getPaddingLeft() + mutil + params.leftMargin, getPaddingTop() + params.topMargin,
                                    getPaddingLeft() + childActualSize + params.leftMargin + mutil, childActualSize + getPaddingTop() + params.topMargin);

                            mutil += childActualSize + params.leftMargin + params.rightMargin;
                        }

                    } else if (mOrientation == ORIENTATION_VERTICAL) {

                        if (getChildCount() > mMaxRow) {
                            if (i < mMaxRow * indexMulti) {
                                childView.layout(getPaddingLeft() + params.leftMargin + indexMultiWidth, getPaddingTop() + params.topMargin + indexMultiHeight,
                                        childActualSize + getPaddingLeft() + params.leftMargin + indexMultiWidth, childActualSize + getPaddingTop()
                                                + params.topMargin + indexMultiHeight);
                                indexMultiHeight += childActualSize + params.topMargin + params.bottomMargin;
                                tempWidth = Math.max(tempWidth, childActualSize) + params.leftMargin + params.rightMargin;
                                if (i + 1 >= mMaxRow * indexMulti) {
                                    indexMultiWidth += tempWidth;
                                    indexMultiHeight = 0;
                                    indexMulti++;
                                }
                            }
                        } else {
                            childView.layout(getPaddingLeft() + params.leftMargin, getPaddingTop() + mutil + params.topMargin,
                                    getPaddingLeft() + params.leftMargin + childActualSize, getPaddingTop() + params.topMargin + childActualSize + mutil);

                            mutil += childActualSize + params.topMargin + params.bottomMargin;
                        }


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
