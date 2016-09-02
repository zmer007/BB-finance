package com.rubo.dfer.assistant.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class SwipeBackLayout extends LinearLayout {
    private static final float PX_UNIT = 1750;
    private float mSwipePxCount = PX_UNIT;
    private SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (mFinishListener != null
                    && Math.abs(velocityY) < mSwipePxCount
                    && velocityX > mSwipePxCount) {
                mFinishListener.onFinishActivity();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    private GestureDetector mDetector;


    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSwipePxCount *= getResources().getDisplayMetrics().density;
        mDetector = new GestureDetector(context, mGestureListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public interface OnFinishGestureListener {
        void onFinishActivity();
    }

    private OnFinishGestureListener mFinishListener;

    public void setFinishGestureListener(OnFinishGestureListener listener) {
        mFinishListener = listener;
    }
}
