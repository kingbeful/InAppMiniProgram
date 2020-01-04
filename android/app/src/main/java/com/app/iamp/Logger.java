package com.app.iamp;

import android.util.Log;

public final class Logger {
    private final static String TAG = "IAMP";

    public static void i (String msg) {
        Log.i(TAG, msg);
    }

    public static void e (String msg) {
        Log.e(TAG, msg);
    }

    public static void d (String msg) {
        Log.d(TAG, msg);
    }
}
