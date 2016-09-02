package com.rubo.dfer.assistant.presentation;

import android.support.v7.app.AppCompatActivity;

import com.rubo.dfer.assistant.R;
import com.rubo.dfer.assistant.utils.SystemUtils;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-31
 * Function:
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void finish() {
        SystemUtils.hideSoftInputFromWindow(this);
        super.finish();
        if (!isTaskRoot()) {
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SystemUtils.hideSoftInputFromWindow(this);
    }
}
