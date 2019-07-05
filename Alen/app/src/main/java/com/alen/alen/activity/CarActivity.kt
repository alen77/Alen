package com.alen.alen.activity

import com.alen.alen.R
import com.alen.alen.utils.UdpHelper
import com.deepblue.chef.chefapp.bean.CarMessage
import com.deepblue.chef.chefapp.bean.ControlMessage
import com.example.libframework.FrameActivity
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_car.*

class CarActivity : BaseActivity(), UdpHelper.OnUdpReceiverListener {

    private var mCar = CarMessage()

    override fun getLayoutId(): Int {
        return R.layout.activity_car
    }

    public override fun getTitleId(): Int {
        return FrameActivity.TOOLBAR_ID
    }

    public override fun getMenuId(): Int {
        return R.menu.menu_main
    }

    override fun initView() {
        UdpHelper.setIp(this)
        UdpHelper.start(this)
        mCar.carCode = "01"
        mCar.status = CarMessage.Status.READY
        loop()
        btnSetOut.setOnClickListener {
            setOut(5)
        }
        btnHome.setOnClickListener {
            home()
        }
        btnPause.setOnClickListener {
            pause()
        }
    }

    private fun setOut(p: Int) {
        mCar.targetLocation = p
        mCar.status = CarMessage.Status.FORWARD
    }

    private fun home() {
        mCar.targetLocation = 0
        mCar.status = CarMessage.Status.GOHOME
    }

    private fun pause() {
        mCar.status = CarMessage.Status.PAUSE
    }

    private fun send() {
        UdpHelper.sendMessage(UdpHelper.createMessage(mCar))
    }

    private fun loop() {
        Observable.create<Int> {
            while (true) {
                Thread.sleep(1000)
                when (mCar.status) {
                    CarMessage.Status.FORWARD -> {
                        if (mCar.selfLocation < mCar.targetLocation) {
                            mCar.selfLocation += 1
                        }
                        if (mCar.selfLocation == mCar.targetLocation) {
                            mCar.status = CarMessage.Status.TAKING
                        }
                    }
                    CarMessage.Status.GOHOME -> {
                        if (mCar.selfLocation > 0) {
                            mCar.selfLocation -= 1
                        }
                        if (mCar.selfLocation == 0) {
                            mCar.status = CarMessage.Status.READY
                            mCar.targetLocation = 0
                        }
                    }
                    CarMessage.Status.TAKING -> {
                        Thread.sleep(3000)
                        mCar.targetLocation = 0
                        mCar.status = CarMessage.Status.GOHOME
                    }
                    CarMessage.Status.AVOID -> {
                        mCar.selfLocation += 1
                        if (mCar.selfLocation >= mCar.targetLocation) {
                            mCar.status = CarMessage.Status.PAUSE
                        }
                    }
                    CarMessage.Status.PAUSE -> {

                    }
                    else -> {

                    }
                }
                Thread.sleep(2000)
                it.onNext(0)
            }

        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    tvSelf.text = "self: ${mCar.selfLocation}"
                    tvTarget.text = "target: ${mCar.targetLocation}"
                    tvStatus.text = "status: ${mCar.status}"
                    send()
                }
    }

    override fun onReceiver(result: String) {
        val control = Gson().fromJson<ControlMessage>(result, ControlMessage::class.java)
        for (car in control.commands) {
            if (car.carCode == mCar.carCode && (mCar.status != CarMessage.Status.READY
                            || car.status != CarMessage.Status.TAKING
                            || car.status != CarMessage.Status.FORWARD)) {
                mCar.targetLocation = car.targetLocation
                mCar.status = car.status
                mCar.avoid = car.avoid
                tvReceiver.text = "receiver = ${Gson().toJson(car)}"
            }
        }
    }

}