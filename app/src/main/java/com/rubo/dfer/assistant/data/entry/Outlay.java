package com.rubo.dfer.assistant.data.entry;

import java.util.Comparator;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-23
 * Function:
 */
public class Outlay {
    public final float outlay;
    public final String summary;
    public final long time;

    public Outlay(float outlay, String summary) {
        this.outlay = outlay;
        this.summary = summary;
        time = System.currentTimeMillis();
    }

    public Outlay(  float outlay, String summary, long time) {
        this.outlay = outlay;
        this.summary = summary;
        this.time = time;
    }

    public static Comparator<Outlay> timeComparator() {
        return new Comparator<Outlay>() {
            @Override
            public int compare(Outlay lhs, Outlay rhs) {
                if (lhs.time > rhs.time) {
                    return -1;
                } else if (lhs.time < rhs.time) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }

    public static Comparator<Outlay> outlayComparator() {
        return new Comparator<Outlay>() {
            @Override
            public int compare(Outlay lhs, Outlay rhs) {
                if (lhs.outlay > rhs.outlay) {
                    return -1;
                } else if (lhs.outlay < rhs.outlay) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }
}
