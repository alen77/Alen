package com.alen.utils;

import android.util.Log;

import com.alen.commen.Constants;

public class LogUtil {

    private static boolean RELEASE_MODE = false;

    public static void v(String message) {
        if (isLoggable(Log.VERBOSE)) {
            Log.v(Constants.LOG_TAG, message);
        }
    }

    public static void v(String format, Object... args) {
        if (isLoggable(Log.VERBOSE)) {
            Log.v(Constants.LOG_TAG, String.format(format, args));
        }
    }

    public static void d(String message) {
        if (isLoggable(Log.DEBUG)) {
            Log.d(Constants.LOG_TAG, message);
        }
    }

    public static void d(String format, Object... args) {
        if (isLoggable(Log.DEBUG)) {
            Log.d(Constants.LOG_TAG, String.format(format, args));
        }
    }

    public static void i(String message) {
        if (isLoggable(Log.INFO)) {
            Log.i(Constants.LOG_TAG, message);
        }
    }

    public static void i(String format, Object... args) {
        if (isLoggable(Log.INFO)) {
            Log.i(Constants.LOG_TAG, String.format(format, args));
        }
    }

    public static void w(String message) {
        if (isLoggable(Log.WARN)) {
            Log.w(Constants.LOG_TAG, message);
        }
    }

    public static void w(String format, Object... args) {
        if (isLoggable(Log.WARN)) {
            Log.w(Constants.LOG_TAG, String.format(format, args));
        }
    }

    public static void e(String message) {
        if (isLoggable(Log.ERROR)) {
            Log.e(Constants.LOG_TAG, message);
        }
    }

    public static void e(String message, Throwable tr) {
        if (isLoggable(Log.ERROR)) {
            Log.e(Constants.LOG_TAG, message, tr);
        }
    }

    public static void e(String format, Object... args) {
        if (isLoggable(Log.ERROR)) {
            Log.e(Constants.LOG_TAG, String.format(format, args));
        }
    }

    public static void e(Throwable tr, String format, Object... args) {
        if (isLoggable(Log.ERROR)) {
            Log.e(Constants.LOG_TAG, String.format(format, args), tr);
        }
    }

    /**
     * Checks to see whether or not a log is loggable at the specified level.
     */
    public static boolean isLoggable(int level) {
        if (RELEASE_MODE) {
            return Log.isLoggable(Constants.LOG_TAG, Log.INFO);
        }
        return Log.isLoggable(Constants.LOG_TAG, level);
    }

    /**
     * Forces the loggable level.
     */
    public static void setReleaseMode(boolean isReleaseMode) {
        RELEASE_MODE = isReleaseMode;
    }
}
