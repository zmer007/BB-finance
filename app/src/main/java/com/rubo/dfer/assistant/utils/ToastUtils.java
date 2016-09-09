package com.rubo.dfer.assistant.utils;

import android.widget.Toast;

import com.rubo.dfer.assistant.MainApplication;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-04
 * Function: 提示
 */
public class ToastUtils {
    private static Toast toast;

    public static void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(MainApplication.getInstance(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showToast(int resId) {
        String msg = MainApplication.getInstance().getString(resId);
        showToast(msg);
    }
}
