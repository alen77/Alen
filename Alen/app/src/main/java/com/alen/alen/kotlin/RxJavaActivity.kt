package com.alen.alen.kotlin

import com.alen.alen.activity.BaseActivity
import com.alen.alen.dialog.BaseDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.act_time_picker.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by Alen on 2018/7/13.
 */
class RxJavaActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTitleId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMenuId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {

    }

    private fun test() : Observable<String>? {
        return Observable.just("a")
    }

}