package com.rubo.dfer.assistant.data.cache;

import android.util.Log;

import com.rubo.dfer.assistant.data.entry.Outlay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lgd(1973140289@qq.com) Date: 2016-08-11 Function:
 */
public class Parser {
    private static final String TAG = "MyParser";

    public static String getJsonString(List<Outlay> outlays) {
        if (outlays == null || outlays.isEmpty()) {
            return "";
        }
        JSONArray jsonArray = new JSONArray();
        try {
            for (Outlay o : outlays) {
                JSONObject jo = new JSONObject();
                jo.put("outlay", o.outlay);
                jo.put("summary", o.summary);
                jo.put("time", o.time);
                jsonArray.put(jo);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return jsonArray.toString();
    }

    public static List<Outlay> getOutlays(String phonebookJson) {
        List<Outlay> list = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(phonebookJson);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                float ol = (float) jo.getDouble("outlay");
                String summary = jo.getString("summary");
                long time = jo.getLong("time");
                Outlay outlay = new Outlay(ol, summary, time);
                list.add(outlay);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return list.isEmpty() ? null : list;
    }
}
