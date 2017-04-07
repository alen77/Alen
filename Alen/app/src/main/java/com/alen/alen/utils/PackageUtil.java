package com.alen.alen.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Alen on 7/8/16.
 */
public class PackageUtil {
    public final static String WEICHAT_PACKAGE_NAME = "com.tencent.mm";
    public final static String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
    public static final String WEIBO_PACKAGE_NAME = "com.sina.weibo";

    private static PackageUtil sSingletonInstance;
    private static final Object sSingletonLock = new Object();

    private final Context mAppContext;

    public static PackageUtil getInstance(Context context) {
        synchronized (sSingletonLock) {
            if (sSingletonInstance == null) {
                sSingletonInstance = new PackageUtil(context.getApplicationContext());
            }
            return sSingletonInstance;
        }
    }

    private PackageUtil(Context context) {
        mAppContext = context.getApplicationContext();
    }

    public boolean isInstalled(String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mAppContext.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
