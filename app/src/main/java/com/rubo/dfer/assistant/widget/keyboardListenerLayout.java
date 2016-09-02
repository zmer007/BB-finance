package com.rubo.dfer.assistant.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.utils.SystemUtils;

public class keyboardListenerLayout extends RelativeLayout {
    private Rect mLocalRect;
    private int mStatusBarHeight;
    private int mNavigationBarHeight;
    private int mKeyboardHeight;
    private int mInputPanelHeight;
    private int mFullScreenHeight;

    private boolean mKeyboardIsShowing;
    private boolean isEnableIntercept = true;
    private OnKeyboardListener mKeyboardListener;

    public keyboardListenerLayout(Context context) {
        this(context, null);
    }

    public keyboardListenerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources res = context.getResources();
        mLocalRect = new Rect();
        mFullScreenHeight = res.getDisplayMetrics().heightPixels;
        mInputPanelHeight = res.getDimensionPixelSize(R.dimen.base_bar_height);

        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            mStatusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int resourceId2 = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId2 > 0) {
            mNavigationBarHeight = res.getDimensionPixelSize(resourceId);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isPreparedToInterceptTouchEvent()) {
            int inputTop = mFullScreenHeight - mInputPanelHeight - mKeyboardHeight - mNavigationBarHeight;
            if (ev.getRawY() < inputTop) {
                mKeyboardListener.onShouldForceHiding(this);
                return true;
            }
        }
        return false;
    }

    private boolean isPreparedToInterceptTouchEvent() {
        return isEnableIntercept && mKeyboardIsShowing && mKeyboardListener != null;
    }

    public void setInterceptTouchEventEnable(boolean interceptEnable) {
        isEnableIntercept = interceptEnable;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getLocalVisibleRect(mLocalRect);
        int deltaY = Math.abs(h - oldh);
        if (deltaY == 0 || deltaY == mLocalRect.height() || deltaY == mStatusBarHeight || deltaY < mKeyboardHeight) {
            return;
        }

        mKeyboardHeight = deltaY;
        if (mKeyboardListener != null) {
            if (oldh < h) {
                mKeyboardListener.onKeyboardWillHiding();
                mKeyboardIsShowing = false;
            } else {
                mKeyboardListener.onKeyboardWillShowing();
                mKeyboardIsShowing = true;
            }
        }
    }

    public int getKeyboardHeight() {
        return mKeyboardHeight;
    }

    public void setKeyboardIsShowing(boolean keyboardIsShowing) {
        mKeyboardIsShowing = keyboardIsShowing;
    }

    public void setOnKeyboardListener(OnKeyboardListener listener) {
        mKeyboardListener = listener;
    }

    public interface OnKeyboardListener {
        void onKeyboardWillShowing();

        void onKeyboardWillHiding();

        void onShouldForceHiding(View view);
    }

    public static class SimpleKeyboardListener implements OnKeyboardListener {

        @Override
        public void onKeyboardWillShowing() {
        }

        @Override
        public void onKeyboardWillHiding() {
        }

        @Override
        public void onShouldForceHiding(View view) {
            if (view.getContext() instanceof Activity) {
                SystemUtils.hideSoftInputFromWindow((Activity) view.getContext());
            }
        }
    }

    @Override
    protected final boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mKeyboardListener != null) {
            if (insets.bottom == 0) {
                mKeyboardListener.onKeyboardWillHiding();
                mKeyboardIsShowing = false;
            } else {
                mKeyboardHeight = insets.bottom;
                mKeyboardListener.onKeyboardWillShowing();
                mKeyboardIsShowing = true;
            }
            insets.top = 0;
        }
        return super.fitSystemWindows(insets);
    }
}
