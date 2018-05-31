package com.saner.view.measure;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by sunset on 2018/5/25.
 */

public class TabLabelLayout extends ViewGroup {


    //滑动工具
    private Scroller mScroller;

    //获取布局的占用屏幕的高度
    private int mHeight;
    //获取布局占用屏幕的宽度
    private int mWidth;
    //上一次的坐标Y
    private float lastDownY;
    //布局中的内容高度
    private int mContentHeight;
    //子View的适配器
    BaseAdapter mAdapter;
    //数据变化是更新View
    DataChangeObserver mObserver;

    //向上和乡下的偏移量
    private int mOffset =500;

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


    private void init(Context context) {
        mScroller=new Scroller(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量布局的宽度
        mWidth=resolveSize(0,widthMeasureSpec);
        /* 声明临时变量存储父容器的期望值 该值应该等于父容器的内边距加上所有子元素的测量宽高和外边距*/
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;
        int childMeasureState = 0;

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

                    if (parentDesireWidth+childWidth >= mWidth) {
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
//        LogUtil.logd("   parentDesireHeight = "+parentDesireHeight);
        mContentHeight =parentDesireHeight;
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
                    if (left+childWidth>mWidth){
                        left=getPaddingLeft();
                        top+=childHeight+params.topMargin;
                        lineHeight=0;
//                        LogUtil.logd((i/4) +" = i  parentDesireHeight = "+top);
                    }
                    childView.layout(left+params.leftMargin,top+params.topMargin,left+childWidth,top+childHeight);
                    left+=childWidth+params.leftMargin;
                }

            }


        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight=h;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mContentHeight<=mHeight){
                    return false;
                }
                lastDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mContentHeight<=mHeight){
                    return false;
                }
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                float currentY=event.getY();
                float dy=lastDownY-currentY;
                if (getScrollY()<-mOffset){
                    dy=0;
                }else if (getScrollY()> mContentHeight -mHeight+ mOffset){
                    dy=0;
                }
                scrollBy(0, (int) dy);
                lastDownY=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (mContentHeight<=mHeight){
                    return false;
                }
//                LogUtil.logd("mContentHeight-mHeight-getScrollY()   = " + (mContentHeight-mHeight-getScrollY()));
                if (getScrollY()<0){
                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                }else if (getScrollY()> mContentHeight -mHeight){
                    mScroller.startScroll(0, getScrollY(), 0, mContentHeight -mHeight-getScrollY());
                }

                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //是否已经滚动完成
        if (mScroller.computeScrollOffset()) {
            //获取当前值，startScroll（）初始化后，调用就能获取区间值
            scrollTo(0, mScroller.getCurrY());
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



    public void setAdapter(BaseAdapter adapter) {
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
            final View view=mAdapter.getView(i, null, null);
//            LogUtil.logd("Add View index = "+i);
//            view.setTag(i);
//            final int position = i;
//            view.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Toast.makeText(view.getContext(), "点击的View索引："+position, Toast.LENGTH_SHORT).show();
//                }
//            });
            this.addView(view);


        }
    }
}
