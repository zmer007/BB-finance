package com.rubo.dfer.assistant.utils;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.rubo.dfer.assistant.R;

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

    public static void setPrimaryClipPlaintText(Context context, CharSequence text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", text));
        ToastUtils.showToast(context.getString(R.string.copy_to_clipboard));
    }
}
