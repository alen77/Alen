package com.alen.alen.activity

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.alen.alen.R
import com.alen.alen.dialog.AlertDialog
import com.alen.alen.kotlin.*
import com.alen.alen.utils.FileUtils
import com.alibaba.view.BubblingView
import com.example.libframework.FrameActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(),
        View.OnClickListener,
        AlertDialog.OnNegativeClickListener {

    private var mVLike: Button? = null
    private var mBubblingView: BubblingView? = null

    private val mResIds = intArrayOf(R.drawable.flower, R.drawable.shape_circle_blue, R.drawable.shape_circle_green, R.drawable.shape_circle_red, R.drawable.shape_square_black, R.drawable.shape_square_blue, R.drawable.shape_square_orange)

    private var mIndex = 0

    private val mRunnable = Runnable { showFlower() }

    public override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    public override fun initView() {
        mVLike = findViewById(R.id.btn_like)
        mBubblingView = findViewById<View>(R.id.bubbling_view) as BubblingView
        mVLike!!.setOnClickListener(this)
        findViewById<View>(R.id.btn_kotlin).setOnClickListener(this)
        findViewById<View>(R.id.btnRuler).setOnClickListener(this)
        findViewById<View>(R.id.btnStretch).setOnClickListener(this)
        findViewById<View>(R.id.btnAi).setOnClickListener(this)
        findViewById<View>(R.id.btnStretch).setOnLongClickListener {
            startActivity(Intent(this, GridListActivity::class.java))
            false
        }
        btnScreen.setOnClickListener {
            startActivity(Intent(this, ScreenActivity::class.java))
        }
        btnCar.setOnClickListener {
            startActivity(Intent(this, CarActivity::class.java))
        }
        btnCamera.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btnList.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
        window.decorView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                Toast.makeText(this, motionEvent.x.toString() + "", Toast.LENGTH_SHORT).show()
            }
            false
        }
        FileUtils.open()
        if (
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    !Settings.canDrawOverlays(this)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivityForResult(intent, 1)
        }
    }

    public override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    public override fun getMenuId(): Int {
        return R.menu.menu_main
    }

    private fun showFlower() {
        mVLike!!.text = mIndex.toString() + "赞"
        val random = Random()
        mBubblingView!!.addBubblingItem(mResIds[random.nextInt(mResIds.size - 1)])
        mVLike!!.postDelayed(mRunnable, 500)
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
                .setTitle("Ai陪聊")
                .setMessage("一个非常机智的人工智能陪你聊天")
                .setPositive("开始尬聊", this)
                .setNegative("不想聊天", this)
                .create()!!.show()

    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_kotlin) {
            startActivity(Intent(this, KotlinTestActivity::class.java))
        } else if (view.id == R.id.btnRuler) {
            startActivity(Intent(this, RulerActivity::class.java))
        } else if (view.id == R.id.btnStretch) {
            startActivity(Intent(this, ScrollViewActivity::class.java))
        } else if (view.id == R.id.btnAi) {
            showDialog()
        } else {
            if (mIndex == 0) {
                mIndex = 1
                mVLike!!.postDelayed(mRunnable, 100)
            } else {
                mIndex = 0
                mVLike!!.removeCallbacks(mRunnable)
            }
        }
    }

    override fun onNegative() {}

    override fun onPositive() {
        startActivity(Intent(this, AiTalkActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        FileUtils.close()
    }
}
