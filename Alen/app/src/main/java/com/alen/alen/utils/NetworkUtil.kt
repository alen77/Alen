package com.alen.alen.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager


object NetworkUtil{

    fun isNetworkAvaiable(context: Context):Boolean{
        val manager : ConnectivityManager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false
        val info = manager.activeNetworkInfo
        if (info == null || !info.isConnected) {
            return false
        }
        return true
    }

    fun getLocalIPAddress(context: Context): String {
        val manager : WifiManager = context.applicationContext.getSystemService(
                Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = manager.connectionInfo
        return intToIp(wifiInfo.ipAddress)
    }

    private fun intToIp(i: Int): String {
        return ((i and 0xFF).toString() + "." + (i shr 8 and 0xFF) + "." + (i shr 16 and 0xFF) + "."
                + (i shr 24 and 0xFF))
    }
}