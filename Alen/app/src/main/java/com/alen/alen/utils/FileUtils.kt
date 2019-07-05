package com.alen.alen.utils

import android.util.Log
import java.io.*
import java.nio.file.NoSuchFileException


object FileUtils {

    var sdCard = "/sys/devices/platform/ff3d0000.i2c/i2c-4/4-0045/hwen"
    var COMMAND_OPEN = "echo 2 > "
    var COMMAND_CLOSE = "echo 0 > "

    fun isFileExists(): Boolean {
        var file = File(sdCard)
        Log.e("FileUtil", "${file.name} = ${file.exists()}")
        return file.exists()
    }

    fun writeFile(content: String) {
        isFileExists()
        var process: Process? = null
        var os: DataOutputStream? = null
        try {
            Log.e("FileUtil", " start write $content")
            process = Runtime.getRuntime().exec("su")
            var cmd = "$content $sdCard\nexit\n"
            process.outputStream.write(cmd.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("FileUtil", " can't write $content")
        } finally {
            Log.e("FileUtil", " destroy us")
            process?.destroy()
            os?.close()
        }

    }

    fun execShell(cmd: String) {
        try {
            var p: Process? = Runtime.getRuntime().exec(arrayOf("su", "-c", cmd))
            val br = BufferedReader(InputStreamReader(p!!.inputStream))
            var readLine = br.readLine()
            while (readLine != null) {
                println(readLine)
                readLine = br.readLine()
            }
            br?.close()
            p.destroy()
            p = null
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }


    fun write(cmd: String) {
        try {
            val fops = FileOutputStream(sdCard)
            fops.write(cmd.toByteArray())
            fops.flush()
            fops.close()
        } catch (e: IOException) {
            Log.e(javaClass.name, "write failed")
            e.printStackTrace()
        } catch (e: NoSuchFileException) {
            Log.e(javaClass.name, "write failed no file")
            e.printStackTrace()
        }
    }

    fun wOpen() {
        write("2")
    }

    fun wClose() {
        write("0")
    }

    fun open() {
        execShell(COMMAND_OPEN + sdCard)
    }

    fun close () {
        execShell(COMMAND_CLOSE + sdCard)
    }

    //sys_path 为节点映射到的实际路径
    fun read(): String? {
        try {
            val runtime = Runtime.getRuntime()
            val process = runtime.exec("cat $sdCard") // 此处进行读操作
            val `is` = process.inputStream
            val isr = InputStreamReader(`is`)
            val br = BufferedReader(isr)
            val line = br.readLine()
            while (null != line) {
                Log.e("FileUtil", "read data ---> $line")
                return line
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("FileUtil", "*** ERROR *** Here is what I know: " + e.message)
        }

        return null
    }

    fun readFile() {
        var prop = "waiting"// 默认值
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader(sdCard))
            prop = reader.readLine()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            reader?.close()
        }
        Log.e("FileUtil", "readFile = $prop")
    }
}