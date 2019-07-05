package com.alen.alen.activity

import android.support.v7.widget.GridLayoutManager
import com.alen.alen.R
import com.alen.alen.adapter.ListAdapter
import com.alen.alen.bean.ListBean
import com.example.libframework.FrameActivity
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_list
    }

    override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    override fun getMenuId(): Int {
        return R.menu.menu_main
    }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        initData()
    }

    fun initData() {
        val info = ListBean.Info()
        info.top = "top"
        info.center = "center"
        info.bottom = "bottom"
        val info1 = ListBean.Info()
        info1.top = "top111"
        info1.center = "center111"
        info1.bottom = "bottom111"

        val data = ListBean()
        data.title = "first"
        data.data.add(info)

        val data1 = ListBean()
        data1.title = "second"
        data1.data.add(info)

        val data2 = ListBean()
        data2.title = "third"
        data2.data.add(info)

        val data3 = ListBean()
        data3.title = "fourth"
        data3.data.add(info)

        val data4 = ListBean()
        data4.title = "fifth"
        data4.data.add(info)
        data4.data.add(info1)

        val list = ArrayList<ListBean>()
        list.add(data)
        list.add(data1)
        list.add(data2)
        list.add(data3)
        list.add(data4)

        val adapter = ListAdapter()
        recyclerView.adapter = adapter

        adapter.setData(list)
    }

}
