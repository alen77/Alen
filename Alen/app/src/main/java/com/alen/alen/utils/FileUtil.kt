package com.alen.alen.utils

import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.hardware.Camera
import android.os.Environment
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * 创建目录
 * 存储图像
 * 返回地址
 */
object FileUtil {

    /**
     * 处理普通拍摄图片
     */
    fun getOutputImageFile(isLeft: Boolean, data: ByteArray, onResult: (File) -> Unit) {
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "WaterSmart")
        val timeStamp = "${System.currentTimeMillis()}.jpg"
        val name = if (isLeft) "LEFT_IMG_" else "RIGHT_IMG_"
        try {
            val file = File(mediaStorageDir.path, "$name$timeStamp")
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            Log.e(javaClass.name, "file = $file")
            val fos = FileOutputStream(file)
            fos.write(data)
            fos.close()
            onResult(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 处理视频流的一帧
     */
    fun getOutBitmapFile(isLeft: Boolean, previewSize: Camera.Parameters, data: ByteArray,
                         onResult: (File) -> Unit) {
        Observable.create<File> {

            val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "WaterSmart")
            val timeStamp = "${System.currentTimeMillis()}.jpg"
            val name = if (isLeft) "LEFT_IMG_$timeStamp" else "RIGHT_IMG_$timeStamp"

            val yuvimage = YuvImage(data, ImageFormat.NV21, previewSize.previewSize.width,
                    previewSize.previewSize.height, null)
            val baos = ByteArrayOutputStream()
            yuvimage.compressToJpeg(Rect(0, 0, previewSize.previewSize.width,
                    previewSize.previewSize.height), 100, baos)
            try {
                val bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(),
                        0, baos.toByteArray().size)
                val file = File(mediaStorageDir.path, name)
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                Log.e(javaClass.name, "file = $file")
//                val fos = FileOutputStream(file)
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                it.onNext(file)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onResult(it)
                }


    }
}