package com.rubo.dfer.assistant;

import android.app.Application;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-23
 * Function:
 */
public class MainApplication extends Application {
    private static MainApplication instance;

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
