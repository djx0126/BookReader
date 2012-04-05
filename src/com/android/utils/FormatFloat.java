package com.android.utils;

public class FormatFloat {
    public static String toString3(float f) {
        String result = "";
        result = String.format("%.3f", f);
        return result;
    }
}
