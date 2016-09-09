package com.rubo.dfer.assistant.utils;

import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.rubo.dfer.assistant.MainApplication;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-09-07
 * Function: 系统动画工具类代理类
 */
public class AnimUtils {
    public static Animation loadAnimation(@AnimRes int animRes) {
        Animation anim = AnimationUtils.loadAnimation(MainApplication.getInstance(), animRes);
        anim.setDuration(300);
        return anim;
    }
}
