package com.rubo.dfer.assistant.presentation;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.utils.SystemUtils;
import com.rubo.dfer.assistant.widget.SwipeBackLayout;
import com.rubo.dfer.assistant.widget.SwipeBackLayout.OnFinishGestureListener;


/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-31
 * Function:
 */
public class TitleActivity extends BaseActivity {
    private FrameLayout contentView;

    private View baseTitle;
    private View consumerTitle;
    private View optionMenuTitle;

    private boolean enableSwipeBack = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_title);
        bindView();
    }

    private void bindView() {
        SwipeBackLayout rootView = (SwipeBackLayout) findViewById(R.id.at_rootView);
        contentView = (FrameLayout) findViewById(R.id.at_content);

        baseTitle = findViewById(R.id.ltc_baseTitlePanel);
        assert baseTitle != null;
        baseTitle.findViewById(R.id.tb_back).setOnClickListener(clickListener);

        consumerTitle = findViewById(R.id.ltc_consumerTitlePanel);

        optionMenuTitle = findViewById(R.id.ltc_optionMenuPanel);

        assert rootView != null;
        rootView.setFinishGestureListener(new OnFinishGestureListener() {
            @Override
            public void onFinishActivity() {
                if (enableSwipeBack && !isTaskRoot()) {
                    finish();
                }
            }
        });

    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tb_back:
                    finish();
                    break;
            }
        }
    };

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getLayoutInflater().inflate(layoutResID, contentView);
    }

    @Override
    public void setContentView(View view) {
        contentView.addView(view);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        contentView.addView(view, params);
    }

    public void setContentView(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.at_content);
        if (f == null) {
            fm.beginTransaction().add(R.id.at_content, fragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.at_content, fragment).commit();
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        ((TextView) baseTitle.findViewById(R.id.tb_title)).setText(title);
    }

    public void setEnableSwipeBack(boolean enable) {
        enableSwipeBack = enable;
    }

    protected void showBaseTitle() {
        baseTitle.setVisibility(View.VISIBLE);

        consumerTitle.setVisibility(View.GONE);
        optionMenuTitle.setVisibility(View.GONE);

        resetAnim(consumerTitle, baseTitle, optionMenuTitle);
    }

    protected void showConsumerTitle() {
        consumerTitle.setVisibility(View.VISIBLE);

        baseTitle.setVisibility(View.GONE);
        optionMenuTitle.setVisibility(View.GONE);

        resetAnim(consumerTitle, baseTitle, optionMenuTitle);

        SystemUtils.changeStatusBarColor(this, R.color.lavender);
    }

    private void resetAnim(View... views) {
        for (View v : views) {
            v.clearAnimation();
        }
    }

    protected void showOptionMenuTitle(int resAnimIn) {
        Animation animIn = getAnimation(resAnimIn);
        optionMenuTitle.setVisibility(View.VISIBLE);
        optionMenuTitle.clearAnimation();
        optionMenuTitle.startAnimation(animIn);

        baseTitle.setVisibility(View.GONE);
        SystemUtils.changeStatusBarColor(this, R.color.green);
    }

    protected void showOptionMenuTitle() {
        optionMenuTitle.setVisibility(View.VISIBLE);

        baseTitle.setVisibility(View.GONE);
        consumerTitle.setVisibility(View.GONE);

        resetAnim(consumerTitle, baseTitle, optionMenuTitle);
    }

    private Animation getAnimation(int resAnim) {
        Animation anim = AnimationUtils.loadAnimation(this, resAnim);
        anim.setDuration(300);
        anim.setFillBefore(true);
        anim.setInterpolator(new AccelerateInterpolator());
        return anim;
    }
}
