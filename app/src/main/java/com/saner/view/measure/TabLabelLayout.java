package com.saner.view.measure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.saner.util.LogUtil;
import com.saner.util.MeasureUtil;

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
        init(context);
    }

    private Scroller mScroller;
    private int mScreenHeight;
    private void init(Context context) {

        mScroller=new Scroller(context);
        mScreenHeight=MeasureUtil.getScreenSize((Activity) context)[1];
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

//                    int childMeasureSize = Math.max(childView.getMeasuredWidth(), childView.getMeasuredHeight());

                    int childMeasureWidth = MeasureSpec.makeMeasureSpec(childView.getMeasuredWidth(), MeasureSpec.UNSPECIFIED);


                    int childMeasureHeight = MeasureSpec.makeMeasureSpec(childView.getMeasuredHeight(), MeasureSpec.UNSPECIFIED);

                    childView.measure(childMeasureWidth, childMeasureHeight);

                    MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                    int childWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                    int childHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;

                    parentDesireHeight = Math.max(parentDesireHeight, childHeight);


                    if (parentDesireWidth+childWidth >= layoutWidth) {
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
        totalHeight=parentDesireHeight;
        setMeasuredDimension(resolveSizeAndState(parentDesireWidth, widthMeasureSpec, childMeasureState),
                resolveSizeAndState(parentDesireHeight, heightMeasureSpec, childMeasureState << MEASURED_HEIGHT_STATE_SHIFT));


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineHeight = 0;
        int top = getPaddingTop();
        int left = getPaddingLeft();
        if (getChildCount() > 0) {

            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);

                if (childView.getVisibility() != GONE) {
                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight() ;
                    MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                    lineHeight=Math.max(childHeight,lineHeight);
                    if (left+childWidth>getWidth()){
                        left=getPaddingLeft();
                        top+=childHeight+params.topMargin;
                        lineHeight=0;
                    }
//                    LogUtil.logd("leftMargin = "+params.leftMargin);
                    childView.layout(left+params.leftMargin,top+params.topMargin,left+childWidth,top+childHeight);
                    left+=childWidth+params.leftMargin;
                }

            }
        }

    }
    private float lastDownY;
    private float mScrollStart;
    private float mScrollEnd;
    private int totalHeight;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastDownY = event.getY();
                mScrollStart = getScrollY();
                LogUtil.logd("totalHeight = " + totalHeight);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }

                float currentY=event.getY();
                float dy=lastDownY-currentY;
//                LogUtil.logd("getScrollY()  = " + getScrollY());
                LogUtil.logd("mScreenHeight()  = " + mScreenHeight);
//                LogUtil.logd("getHeight()-mScreenHeight  = " + (getHeight()-mScreenHeight));
//                LogUtil.logd("getHeight()  = " + getHeight());
                if (mScrollEnd<-500){
                    dy=0;
                    LogUtil.logd("顶端 = "+getScrollY());
                }else if (mScrollEnd>mScreenHeight){
                    dy=0;
                    LogUtil.logd("底部 getScrollY() = "+getScrollY());
                    LogUtil.logd("底部 getHeight()-mScreenHeight = "+(getHeight()-mScreenHeight));
                }
//                LogUtil.logd("dy = " + dy);
                scrollBy(0, (int) dy);
                lastDownY=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mScrollEnd = getScrollY();
//                LogUtil.logd("mScrollEnd  = " + mScrollEnd);
//                LogUtil.logd("getHeight()-mScreenHeight  = " + (getHeight()-mScreenHeight));
//                LogUtil.logd("getScrollY()  = " + getScrollY());
//
//                LogUtil.logd("getHeight() - mScreenHeight - (int) mScrollEnd  = " + (getHeight() - mScreenHeight - (int) mScrollEnd));
                if (mScrollEnd<0){
                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                }else if (mScrollEnd>mScreenHeight){
                    mScroller.startScroll(0, getScrollY(), 0,  0);
//
                }
                mScrollEnd=0;
//                LogUtil.logd("getScrollY() 2  = " + getScrollY());
//                int dScrollY = (int) (mScrollEnd - mScrollStart);
////                Log.d("test", "dScrollY = " + dScrollY);
////                此处实现的是根据滑动的距离来实现滚动
//                if (mScrollEnd < 0) {// 最顶端：手指向下滑动，回到初始位置
////                    Log.d(TAG, "mScrollEnd < 0" + dScrollY);
//                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
//                } else if (mScrollEnd > getHeight() - mScreenHeight) {//已经到最底端，手指向上滑动回到底部位置
////                    Log.d(TAG, "getHeight() - mScreenHeight - (int) mScrollEnd " + (getHeight() - mScreenHeight - (int) mScrollEnd));
//                    mScroller.startScroll(0, getScrollY(), 0, getHeight() - mScreenHeight - (int) mScrollEnd);
//                }
                break;
        }
        postInvalidate();// 重绘执行computeScroll()
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
//        Log.d(TAG, "mScroller.getCurrY() " + mScroller.getCurrY());
        if (mScroller.computeScrollOffset()) {//是否已经滚动完成
            scrollTo(0, mScroller.getCurrY());//获取当前值，startScroll（）初始化后，调用就能获取区间值
            postInvalidate();
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


     class DataChangeObserver extends DataSetObserver {
        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }

        @Override
        public void onChanged() {
            onDrawChildView();
        }
    }

    BaseAdapter mAdapter;
    DataChangeObserver mObserver;

    public void setAdapter(BaseAdapter adapter) {
//        if (mAdapter == null) {
//            this.mAdapter = adapter;
//            if (mObserver == null) {
//                mObserver = new DataChangeObserver();
//                mAdapter.registerDataSetObserver(mObserver);
//            }
//            onDrawChildView();
//        }

        if (mAdapter == null){
            mAdapter = adapter;
            if (mObserver == null){
                mObserver = new DataChangeObserver();
                mAdapter.registerDataSetObserver(mObserver);
            }
            onDrawChildView();
        }
    }

    private void onDrawChildView() {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }

        this.removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view=mAdapter.getView(i, null, null);
//            LogUtil.logd("Add View index = "+i);
            this.addView(view);

        }
    }
}
