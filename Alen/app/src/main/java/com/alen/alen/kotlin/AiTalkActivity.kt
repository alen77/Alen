package com.alen.alen.kotlin

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.alen.alen.R
import com.alen.alen.activity.BaseActivity
import com.alen.alen.adapter.AiTalkAdapter
import com.alen.alen.commen.Constants
import com.alen.alen.module.AiAsk
import com.alen.alen.module.Chat
import com.alen.alen.net.RetrofitUtil
import com.alen.alen.net.runRxLambda
import com.example.libframework.FrameActivity
import kotlinx.android.synthetic.main.act_ai.*

/**
 * Created by Alen on 2018/7/13.
 */
class AiTalkActivity : BaseActivity(), View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.act_ai
    }

    override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    override fun getMenuId(): Int {
        return FrameActivity.DEFAULT
    }

    private val mList : ArrayList<Chat> = ArrayList()
    private val mAdapter : AiTalkAdapter = AiTalkAdapter()

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        mAdapter.setData(mList)

        btnSend.setOnClickListener(this)
    }

    private fun talkToAi(content : String) {
        val per = AiAsk.Perception(AiAsk.Perception.InputText(content))
        val ask = AiAsk(0, per, AiAsk.UserInfo(Constants.ROBOT_API_KEY, Constants.ROBOT_USER_ID))
        runRxLambda(RetrofitUtil.getInstance().getService().request(ask), {
            Log.d(Constants.LOG_TAG, it.toString())
            mList.add(Chat(it.results[0].values.text, 1))
            mAdapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(mAdapter.itemCount - 1)
            etInput.text.clear()
        }, {

        })
    }


    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume()
    }

    @SuppressLint("MissingSuperCall")
    override fun onPause() {
        super.onPause()
    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onClick(p0: View?) {

        mList.add(Chat(etInput.text.toString(), 0))
        mAdapter.notifyDataSetChanged()
        talkToAi(etInput.text.toString())
    }
}