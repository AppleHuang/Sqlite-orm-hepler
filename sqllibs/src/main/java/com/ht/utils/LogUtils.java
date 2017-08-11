package com.ht.utils;

import android.text.TextUtils;
import android.util.Log;


public class LogUtils {
	private static final String TAG = "baseLibs";
	private static final boolean IS_DEBUG_MODE = true;

	private LogUtils(){}
	
	public static void info(String tag, String msg) {
		if (!IS_DEBUG_MODE)
			return;
		if (!TextUtils.isEmpty(tag)) {
			Log.i(tag, msg);
		} else {
			Log.i(TAG, msg);
		}
	}

	public static void debug(String tag, String msg) {
		if (!IS_DEBUG_MODE)
			return;
		if (!TextUtils.isEmpty(tag)) {
			Log.d(tag, msg);
		} else {
			Log.d(TAG, msg);
		}
	}

    public static void error(String msg) {
        if (!IS_DEBUG_MODE)
            return;
        Log.e(TAG, msg);
    }

    public static void error(Throwable e) {
        if (!IS_DEBUG_MODE)
            return;
        Log.e(TAG, e.getMessage());
    }

	public static void error(String tag, String msg) {
		if (!IS_DEBUG_MODE)
			return;
		if (!TextUtils.isEmpty(tag)) {
			Log.e(tag, msg);
		} else {
			Log.e(TAG, msg);
		}
	}

	public static void error(String tag, String msg, Throwable tr) {
		if (!IS_DEBUG_MODE)
			return;
		if (!TextUtils.isEmpty(tag)) {
			Log.e(tag, msg, tr);
		} else {
			Log.e(TAG, msg, tr);
		}
	}
}
