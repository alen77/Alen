package com.alen.alen.utils

import android.text.TextUtils
import java.io.InputStreamReader
import java.io.LineNumberReader

object CpuInfoHelp {

    /**
     * 获取CPU序列号
     *
     * @return CPU序列号(16位)
     * 读取失败为"0000000000000000"
     */

    fun getCpuSerial(): String {
        var str = ""
        var cpuStr = ""
        var cpuAddress = "0000000000000000"
        try {
            val pc = Runtime.getRuntime().exec("cat/proc/cpuinfo")
            val isr = InputStreamReader(pc.inputStream)
            val lnr = LineNumberReader(isr)

            for (i in 0..100) {
                str = lnr.readLine()
                if (!TextUtils.isEmpty(str)) {
                    if (str.indexOf("Serial") > -1) {
                        cpuStr = str.substring(str.indexOf(":") + 1, str.length)
                        cpuAddress = cpuStr.trim()
                    }
                } else {
                    break
                }
            }
        } catch (e: Exception) {

        }
        return cpuAddress
    }

}