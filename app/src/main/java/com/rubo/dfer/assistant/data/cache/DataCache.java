package com.rubo.dfer.assistant.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.rubo.dfer.assistant.MainApplication;
import com.rubo.dfer.assistant.data.entry.Outlay;

import java.util.List;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-09-01
 * Function:
 */
public class DataCache {
    private static final String SP_NAME = "com.rubo.dfer.assistant.cacheSpName";
    private static final String OUTLAYS = "com.rubo.dfer.assistant.outlaysContent";
    private static final String START_TIME = "com.rubo.dfer.assistant.startTime";

    private static DataCache instance;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    private DataCache() {
        sp = MainApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
    }

    public boolean saveOutlays(List<Outlay> outlays) {
        editor.putString(OUTLAYS, Parser.getJsonString(outlays));
        return editor.commit();
    }

    public List<Outlay> getOutlays() {
        String json = sp.getString(OUTLAYS, "");
        return Parser.getOutlays(json);
    }

    public boolean saveStartTime(long startTime) {
        editor.putLong(START_TIME, startTime);
        return editor.commit();
    }

    public long getStartTime() {
        return sp.getLong(START_TIME, 0);
    }
}
