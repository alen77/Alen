package com.alen.alen.utils;

import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.util.List;

/**
 * 相机工具，初始化-预览-拍照
 */
public class CameraHelper implements Camera.PreviewCallback, SurfaceHolder.Callback, Camera.PictureCallback {
    private static final String TAG = "CameraHelper";
    private Camera mCamera;
    private int mCameraId;
    private Builder mBuilder;

    public CameraHelper(Builder builder) {
        mBuilder = builder;
    }

    public void init() {
        ((SurfaceView) mBuilder.previewDisplayView).getHolder().addCallback(this);
    }

    public void takePicture() {
        if (mCamera == null || mBuilder.cameraListener == null) return;
        mCamera.takePicture(null, null, this);
    }

    public void startPreview() {
        mCamera.startPreview();
    }

    private void start() {
            //相机数量为2则打开1,1则打开0,相机ID 1为前置，0为后置
            mCameraId = Camera.getNumberOfCameras() - 1;
            //若指定了相机ID且该相机存在，则打开指定的相机
            if (mBuilder.specificCameraId != null) {
                mCameraId = mBuilder.specificCameraId;
            }

            //没有相机
            if (mCameraId == -1) {
                if (mBuilder.cameraListener != null) {
                    mBuilder.cameraListener.onCameraError(new Exception("camera not found"));
                }
                return;
            }
            if (mCamera == null) {
                try {
                    mCamera = Camera.open(mCameraId);
                } catch (Exception e) {
                    mBuilder.cameraListener.onCameraError(e);
                }
            }

            //相机设置
            try {
                //预览方向
                int displayOrientation = getCameraOri(mBuilder.rotation);
                mCamera.setDisplayOrientation(displayOrientation);

                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPreviewFormat(ImageFormat.NV21);

                //预览大小设置
                Camera.Size previewSize = parameters.getPreviewSize();
                List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                if (supportedPreviewSizes != null && supportedPreviewSizes.size() > 0) {
                    previewSize = getBestSupportedSize(supportedPreviewSizes, mBuilder.previewSize);
                }
                parameters.setPreviewSize(previewSize.width, previewSize.height);

                //对焦模式设置
                List<String> supportedFocusModes = parameters.getSupportedFocusModes();
                if (supportedFocusModes != null && supportedFocusModes.size() > 0) {
                    if (supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                    } else if (supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                    } else if (supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    }
                }
                mCamera.setParameters(parameters);

                //设置预览界面
                mCamera.setPreviewDisplay(((SurfaceView) mBuilder.previewDisplayView).getHolder());
                if (mBuilder.needPreviewByte) {
                    mCamera.setPreviewCallback(this);
                }
                mCamera.startPreview();

                if (mBuilder.cameraListener != null) {
                    mBuilder.cameraListener.onCameraOpened(mCamera, mCameraId, displayOrientation);
                }
            } catch (Exception e) {
                if (mBuilder.cameraListener != null) {
                    mBuilder.cameraListener.onCameraError(e);
                }
            }
    }

    public void stop() {
            if (mCamera == null) {
                return;
            }
            try {
                mCamera.setPreviewCallback(null);
                mCamera.setPreviewDisplay(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
                if (mBuilder.cameraListener != null) {
                    mBuilder.cameraListener.onCameraClosed();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private int getCameraOri(int rotation) {
        int degrees = rotation * 90;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                break;
        }
        mBuilder.additionalRotation /= 90;
        mBuilder.additionalRotation *= 90;
        degrees += mBuilder.additionalRotation;
        int result;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, info);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, Point previewViewSize) {
        if (sizes == null || sizes.size() == 0 || previewViewSize == null) {
            return mCamera.getParameters().getPreviewSize();
        }
        Camera.Size bestSize = sizes.get(0);
        float previewViewRatio = (float) previewViewSize.x / (float) previewViewSize.y;

        if (previewViewRatio > 1) {
            previewViewRatio = 1 / previewViewRatio;
        }
        boolean isNormalRotate = (mBuilder.additionalRotation % 180 == 0);

        for (Camera.Size s : sizes) {
            if (mBuilder.previewSize != null
                    && mBuilder.previewSize.x == s.width
                    && mBuilder.previewSize.y == s.height) {
                return s;
            }
            if (isNormalRotate) {
                if (Math.abs((s.height / (float) s.width) - previewViewRatio) < Math.abs(bestSize.height / (float) bestSize.width - previewViewRatio)) {
                    bestSize = s;
                }
            } else {
                if (Math.abs((s.width / (float) s.height) - previewViewRatio) < Math.abs(bestSize.width / (float) bestSize.height - previewViewRatio)) {
                    bestSize = s;
                }
            }
        }
        return bestSize;
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        mBuilder.cameraListener.onPreview(bytes, camera);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        mBuilder.cameraListener.onPictureTaken(bytes, camera);
    }

    public static final class Builder {

        /**
         * 预览显示的view，目前仅支持surfaceView和textureView
         */
        private View previewDisplayView;

        /**
         * 指定的相机ID
         */
        private Integer specificCameraId;
        /**
         * 事件回调
         */
        private CameraListener cameraListener;
        /**
         * 屏幕的长宽，在选择最佳相机比例时用到
         */
        private Point previewViewSize;
        /**
         * 传入getWindowManager().getDefaultDisplay().getRotation()的值即可
         */
        private int rotation;
        /**
         * 指定的预览宽高，若系统支持则会以这个预览宽高进行预览
         */
        private Point previewSize;

        /**
         * 额外的旋转角度（用于适配一些定制设备）
         */
        private int additionalRotation;

        /**
         * 设置是否需要相机预览数据
         */
        private boolean needPreviewByte;

        public Builder() {
        }


        public CameraHelper.Builder previewOn(View val) {
            if (val instanceof SurfaceView) {
                previewDisplayView = val;
                return this;
            } else {
                throw new RuntimeException("you must preview on a surfaceView");
            }
        }

        public CameraHelper.Builder previewSize(Point val) {
            previewSize = val;
            return this;
        }

        public CameraHelper.Builder previewViewSize(Point val) {
            previewViewSize = val;
            return this;
        }

        public CameraHelper.Builder rotation(int val) {
            rotation = val;
            return this;
        }

        public CameraHelper.Builder additionalRotation(int val) {
            additionalRotation = val;
            return this;
        }

        public CameraHelper.Builder specificCameraId(Integer val) {
            specificCameraId = val;
            return this;
        }

        public CameraHelper.Builder cameraListener(CameraListener val) {
            cameraListener = val;
            return this;
        }

        public CameraHelper.Builder needPreviewByte(boolean val) {
            needPreviewByte = val;
            return this;
        }

        public CameraHelper build() {
            if (previewViewSize == null) {
                Log.e(TAG, "previewViewSize is null, now use default previewSize");
            }
            if (cameraListener == null) {
                Log.e(TAG, "cameraListener is null, callback will not be called");
            }
            if (previewDisplayView == null) {
                throw new RuntimeException("you must preview on a textureView or a surfaceView");
            }
            return new CameraHelper(this);
        }
    }
}
