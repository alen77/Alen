package com.alen.alen.activity

import com.alen.alen.R
import com.alen.alen.R.id.*
import com.alen.alen.utils.FileUtils
import com.example.libframework.FrameActivity
import kotlinx.android.synthetic.main.activity_file.*

class FileActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_file
    }

    public override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    public override fun getMenuId(): Int {
        return R.menu.menu_main
    }

    override fun initView() {
        btnSuOpen.setOnClickListener {
            FileUtils.open()
        }
        btnSuClose.setOnClickListener {
            FileUtils.close()
        }
        btnWOpen.setOnClickListener {
            FileUtils.wOpen()
        }
        btnWClose.setOnClickListener {
            FileUtils.wClose()
        }
    }

}