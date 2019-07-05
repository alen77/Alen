package com.alen.alen.activity

import android.graphics.Point
import android.hardware.Camera
import android.os.Handler
import android.util.Log
import android.view.SurfaceView
import android.widget.FrameLayout
import com.alen.alen.R
import com.alen.alen.utils.CameraHelper
import com.alen.alen.utils.CameraListener
import com.alen.alen.utils.FileUtil
import com.example.libframework.FrameActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_camera.*
import java.lang.Exception


class CameraActivity : BaseActivity() {
    private var mTaking = false
    private var mSaveLeft = true

    private val mFls = ArrayList<FrameLayout>()
    private val mList = ArrayList<SurfaceView>()
    private val mHelpers = ArrayList<CameraHelper>()

    override fun getLayoutId(): Int {
        return R.layout.activity_camera
    }

    public override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    public override fun getMenuId(): Int {
        return R.menu.menu_main
    }


    override fun initView() {
        mFls.add(fl1)
        mFls.add(fl2)
        mFls.add(fl3)
        mFls.add(fl4)
        mFls.add(fl5)
        mFls.add(fl6)

        val c = Camera.getNumberOfCameras()
        Log.e(javaClass.name, "camera number = $c")
        for (i in 0..c) {
            val preview = SurfaceView(this)
            mList.add(preview)
            mFls[i].addView(preview)
        }

        for (i in mList.indices) {
            mHelpers.add(startCamera(i, mList[i]))
            Observable.create<String> {
                var loop = true
                var a = 1
                while (loop) {
                    Thread.sleep(200)
                    a += 1
                    loop = a < 1000
                }
            }.subscribeOn(Schedulers.newThread())
        }

        btnStart.setOnClickListener {
            mTaking = true
            for (h in mHelpers) {
                h.takePicture()
            }
        }

        btnStop.setOnClickListener {
            mTaking = false
        }
    }

    fun animation() {
    }

    fun loopTake() {
        mSaveLeft = false
        Handler().postDelayed({
            mSaveLeft = true
        }, 200)
    }

    fun startCamera(id: Int, surfaceView: SurfaceView): CameraHelper {
        val help = CameraHelper.Builder()
                .specificCameraId(id)
                .previewViewSize(Point(surfaceView.measuredWidth, surfaceView.measuredHeight))
                .rotation(windowManager?.defaultDisplay?.rotation!!)
                .previewOn(surfaceView)
                .needPreviewByte(true)
                .cameraListener(object : CameraListener {
                    override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
                        data?.let { it ->
                            FileUtil.getOutputImageFile(true, it) {
                            }
                            camera?.startPreview()
                        }
                    }

                    override fun onPreview(data: ByteArray?, camera: Camera?) {
                        if (mTaking && mSaveLeft) {
                            data?.let { it ->
                                FileUtil.getOutBitmapFile(true, camera?.parameters!!, it) {

                                }
                                loopTake()
                            }
                        }
                    }

                    override fun onCameraConfigurationChanged(cameraID: Int, displayOrientation: Int) {
                    }

                    override fun onCameraOpened(camera: Camera?, cameraId: Int, displayOrientation: Int) {
                    }

                    override fun onCameraClosed() {
                    }

                    override fun onCameraError(e: Exception?) {
                        e?.printStackTrace()
                    }
                })
                .build()
        help.init()
        return help
    }
}