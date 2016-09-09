package com.rubo.dfer.assistant.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-09-07
 * Function: 格式化时间的工具类
 */
public class TimeFormatUtils {
    public static final String DEFAULT_TIME = "未知";
    public static final String separator = "/";
    private static Calendar calendarAgo = Calendar.getInstance();
    private static Calendar calendarCurrent = Calendar.getInstance();
    private static SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();

    /**
     * 根据时间戳和当前时间戳,得到格式化的时间字符串, 如: 刚刚, 2分钟前, 昨天, 4月1日, 2012年12月23日等.
     *
     * @param timestamp         时间戳
     * @param currentTimeMillis 当前时间戳
     * @return 代表时间的字符串
     */
    public static synchronized String getFormatTimeString(long timestamp, long currentTimeMillis) {

        if (currentTimeMillis < timestamp || timestamp == 0) {
            return DEFAULT_TIME;
        }

        calendarAgo.setTime(new Date(timestamp));
        calendarCurrent.setTime(new Date(currentTimeMillis));

        int yearAgo = calendarAgo.get(Calendar.YEAR);
        int yearCurrent = calendarCurrent.get(Calendar.YEAR);

        int monthAgo = calendarAgo.get(Calendar.MONTH) + 1;
        int monthCurrent = calendarCurrent.get(Calendar.MONTH) + 1;

        int dayAgo = calendarAgo.get(Calendar.DAY_OF_MONTH);
        int dayCurrent = calendarCurrent.get(Calendar.DAY_OF_MONTH);

        String result;
        if (yearAgo < yearCurrent) {
            dateFormat.applyPattern("yyyy年MM月dd日");
            result = dateFormat.format(calendarAgo.getTime());
        } else if (monthAgo == monthCurrent && (dayAgo == dayCurrent || dayAgo + 1 == dayCurrent || dayAgo + 2 == dayCurrent)) {
            if (dayAgo == dayCurrent) {
                result = "今天";
            } else if (dayAgo + 1 == dayCurrent) {
                result = "昨天";
            } else if (dayAgo + 2 == dayCurrent) {
                result = "前天";
            } else {
                result = "未知";
            }
        } else {
            dateFormat.applyPattern("MM月dd日");
            result = dateFormat.format(calendarAgo.getTime());
        }

        return result;
    }

    public static String getDate(long time) {
        Date date = new Date(time);
        calendarAgo.setTime(date);
        int year = calendarAgo.get(Calendar.YEAR);
        int month = calendarAgo.get(Calendar.MONTH) + 1;
        int day = calendarAgo.get(Calendar.DAY_OF_MONTH);
        return String.format(Locale.CHINA, "%02d/%02d", month, day);
    }

    public static String getDeltaDay(long timestamp, long currentTimeMillis) {
        if (currentTimeMillis < timestamp || timestamp == 0) {
            return "???";
        }

        calendarAgo.setTime(new Date(timestamp));
        calendarCurrent.setTime(new Date(currentTimeMillis));

        int yearAgo = calendarAgo.get(Calendar.YEAR);
        int yearCurrent = calendarCurrent.get(Calendar.YEAR);

        int dayAgo = calendarAgo.get(Calendar.DAY_OF_YEAR);
        int dayCurrent = calendarCurrent.get(Calendar.DAY_OF_YEAR);

        if (yearAgo < yearCurrent) {
            return "一年多了^.^";
        }

        return "" + (dayCurrent - dayAgo + 1);
    }

    private static String getAmPm(int hour) {
        if (hour < 12) {
            return "上午";
        } else {
            return "下午";
        }
    }

    @SuppressLint("DefaultLocale")
    public static String getTimeTickString(long seconds) {
        long hour = seconds / 3600;
        long minute = seconds % 3600 / 60;
        long second = seconds % 3600 % 60;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
