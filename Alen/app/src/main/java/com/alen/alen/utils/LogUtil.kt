package com.alen.alen.utils

import android.util.Log

import com.alen.alen.commen.Constants

object LogUtil {

    private var RELEASE_MODE = false

    fun v(message: String) {
        if (isLoggable(Log.VERBOSE)) {
            Log.v(Constants.LOG_TAG, message)
        }
    }

    fun v(format: String, vararg args: Any) {
        if (isLoggable(Log.VERBOSE)) {
            Log.v(Constants.LOG_TAG, String.format(format, *args))
        }
    }

    fun d(message: String) {
        if (isLoggable(Log.DEBUG)) {
            Log.d(Constants.LOG_TAG, message)
        }
    }

    fun d(format: String, vararg args: Any) {
        if (isLoggable(Log.DEBUG)) {
            Log.d(Constants.LOG_TAG, String.format(format, *args))
        }
    }

    fun i(message: String) {
        if (isLoggable(Log.INFO)) {
            Log.i(Constants.LOG_TAG, message)
        }
    }

    fun i(format: String, vararg args: Any) {
        if (isLoggable(Log.INFO)) {
            Log.i(Constants.LOG_TAG, String.format(format, *args))
        }
    }

    fun w(message: String) {
        if (isLoggable(Log.WARN)) {
            Log.w(Constants.LOG_TAG, message)
        }
    }

    fun w(format: String, vararg args: Any) {
        if (isLoggable(Log.WARN)) {
            Log.w(Constants.LOG_TAG, String.format(format, *args))
        }
    }

    fun e(message: String) {
        if (isLoggable(Log.ERROR)) {
            Log.e(Constants.LOG_TAG, message)
        }
    }

    fun e(message: String, tr: Throwable) {
        if (isLoggable(Log.ERROR)) {
            Log.e(Constants.LOG_TAG, message, tr)
        }
    }

    fun e(format: String, vararg args: Any) {
        if (isLoggable(Log.ERROR)) {
            Log.e(Constants.LOG_TAG, String.format(format, *args))
        }
    }

    fun e(tr: Throwable, format: String, vararg args: Any) {
        if (isLoggable(Log.ERROR)) {
            Log.e(Constants.LOG_TAG, String.format(format, *args), tr)
        }
    }

    /**
     * Checks to see whether or not a log is loggable at the specified level.
     */
    fun isLoggable(level: Int): Boolean {
        return if (RELEASE_MODE) {
            Log.isLoggable(Constants.LOG_TAG, Log.INFO)
        } else Log.isLoggable(Constants.LOG_TAG, level)
    }

    /**
     * Forces the loggable level.
     */
    fun setReleaseMode(isReleaseMode: Boolean) {
        RELEASE_MODE = isReleaseMode
    }
}
