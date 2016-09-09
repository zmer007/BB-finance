package com.rubo.dfer.assistant.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.rubo.dfer.assistant.MainApplication;
import com.rubo.dfer.assistant.R;

import java.util.List;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-04 Function: 系统工具类
 */
public class SystemUtils {

    /**
     * 强制关闭软键盘
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 强制显示软键盘
     */
    public static void showSoftInputFromWindow(EditText et) {
        if (et != null) {
            InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 唤醒屏幕
     */
    public static void wakeUp(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressWarnings("deprecation")
        PowerManager.WakeLock wake = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        // 点亮屏幕
        wake.acquire();
        // 释放
        wake.release();
    }

    /**
     * 判断某activity是否在前台显示
     */
    public static boolean isTopActivity(Context ctx, String packgeName, String shortClassName) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks == null || tasks.isEmpty()) {
            return false;
        }
        ComponentName topActivity = tasks.get(0).topActivity;
        return TextUtils.equals(packgeName, topActivity.getPackageName()) && TextUtils.equals(shortClassName, topActivity.getShortClassName());
    }

    /**
     * 设置剪切板内容
     */
    public static void setPrimaryClipPlaintText(Context context, CharSequence text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", text));
        ToastUtils.showToast(context.getString(R.string.copy_to_clipboard));
    }

    /**
     * 获取状态栏调试
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = MainApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = MainApplication.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /***
     * 设置状态栏颜色，API > 21
     */
    public static void changeStatusBarColor(Activity activity, @ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(colorRes));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight());
            statusBarView.setBackgroundColor(activity.getResources().getColor(colorRes));
            contentView.addView(statusBarView, lp);
        }
    }
}
