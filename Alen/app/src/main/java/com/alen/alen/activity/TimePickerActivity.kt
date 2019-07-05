package com.alen.alen.activity

import android.view.View
import com.alen.alen.R
import com.example.libframework.FrameActivity
import java.util.*

/**
 * Created by alen on 2017/6/7.
 */

class TimePickerActivity : BaseActivity(), View.OnClickListener {
    override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    override fun getMenuId(): Int {
        return R.menu.menu_main
    }

    override fun getLayoutId(): Int {
        return R.layout.act_time_picker
    }

    override fun initView() {
        val show : View = findViewById(R.id.btn_show)
        show.setOnClickListener(this)
        mTimes.add("上午")
        mTimes.add("下午")
        mTimes.add("夜间")
    }

    private val mTimes = ArrayList<String>()

//    private fun showDialog() {
//        // 设置传入的时间格式
//        val dateFormat = SimpleDateFormat("yyyy-M-d")
//        // 指定一个日期
//        var date: Date? = null
//        try {
//            date = dateFormat.parse("2017-1-1")
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//
//        // 对 calendar 设置为 date 所定的日期
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//        //时间选择器
//        val pvTime = TimePickerView.Builder(this,
//                TimePickerView.OnTimeSelectListener { date, v ->
//                    //选中事件回调
//                    show()
//                })
//                .setType(booleanArrayOf(true, true, true, false, false, false))
//                .setRangDate(calendar, Calendar.getInstance())
//                .build()
//        pvTime.setDate(Calendar.getInstance())//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
//        pvTime.show()
//    }
//
//    private fun show() {
//        //条件选择器
//        val pvOptions = OptionsPickerView.Builder(this,
//                OptionsPickerView.OnOptionsSelectListener { options1, option2, options3, v ->
//                    //返回的分别是三个级别的选中位置
//                }).build()
//        pvOptions.setPicker(mTimes, null, null)
//        pvOptions.show()
//    }

    override fun onClick(view: View) {
//        showDialog()
    }
}
