package com.rubo.dfer.assistant.data.util;

public class ThreeTuples<A, B, C> extends TwoTuples<A, B> {
    public final C third;

    public ThreeTuples(A a, B b, C c) {
        super(a, b);
        this.third = c;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ")";
    }
}
