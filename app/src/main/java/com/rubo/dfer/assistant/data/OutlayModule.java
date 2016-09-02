package com.rubo.dfer.assistant.data;

import com.rubo.dfer.assistant.data.cache.DataCache;
import com.rubo.dfer.assistant.data.entry.Outlay;
import com.rubo.dfer.assistant.utils.SystemUtils;
import com.rubo.dfer.assistant.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-23
 * Function:
 */
public class OutlayModule {
    private static OutlayModule instance = new OutlayModule();

    public static OutlayModule getInstance() {
        return instance;
    }

    long startTime;
    List<Outlay> outlays;
    DataCache dataCache;

    private OutlayModule() {
        dataCache = DataCache.getInstance();
        startTime = dataCache.getStartTime();
        outlays = dataCache.getOutlays();
    }

    public String sumOutlays() {
        float sum = 0f;
        if (outlays == null || outlays.isEmpty()) {
            sum = 0;
        } else {
            for (Outlay o : outlays) {
                sum += o.outlay;
            }
        }
        return String.format(Locale.CHINA, "%.2f", sum);
    }

    public List<Outlay> getOutlays() {
        if (outlays == null) {
            return new ArrayList<>();
        }
        return outlays;
    }

    public void addOutlay(Outlay outlay) {
        if (outlays == null) {
            outlays = new ArrayList<>();
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
        }
        outlays.add(0, outlay);
    }

    public void removeOutlays(List<Outlay> outlays) {
        if (outlays != null) {
            this.outlays.removeAll(outlays);
        }
    }

    public String getStartDate() {
        if (startTime == 0) {
            return "月/日";
        }
        return TimeFormatUtils.getDate(startTime);
    }

    public String getDurationDays() {
        return TimeFormatUtils.getDeltaDay(startTime, System.currentTimeMillis());
    }

    public void resetData() {
        startTime = 0;
        outlays = null;
        saveOutlaysData();
    }

    public void sortDataByTime() {
        if (outlays == null) {
            return;
        }
        Collections.sort(outlays, Outlay.timeComparator());
    }

    public void sortDataByOutlay() {
        if (outlays == null) {
            return;
        }
        Collections.sort(outlays, Outlay.outlayComparator());
    }

    public void saveOutlaysData() {
        dataCache.saveOutlays(outlays);
        dataCache.saveStartTime(startTime);
    }
}
