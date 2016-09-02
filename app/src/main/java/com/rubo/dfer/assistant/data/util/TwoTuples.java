package com.rubo.dfer.assistant.data.util;

public class TwoTuples<A, B> {
    public final A first;
    public final B second;

    public TwoTuples(A a, B b) {
        this.first = a;
        this.second = b;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second+")";
    }
}
