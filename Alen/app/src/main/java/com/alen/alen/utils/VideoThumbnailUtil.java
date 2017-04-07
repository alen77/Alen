package com.alen.alen.utils;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

/**
 * Created by Alen on 16/3/9.
 * 获取本地视频缩略图??
 */
public class VideoThumbnailUtil {

    public static Bitmap getVideoThumbnail(String videoPath, int width, int height) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath,
                MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
        if (bitmap == null){
            return null;
        }
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}
