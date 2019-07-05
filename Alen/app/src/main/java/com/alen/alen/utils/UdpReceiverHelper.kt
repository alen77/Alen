package com.alen.alen.utils

import android.content.Context
import android.util.Log
import com.deepblue.chef.chefapp.bean.CarMessage
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


object UdpHelper {
    private const val TYPE_CAR_MESSAGE = "carMessage"
    private const val TYPE_CONTROL_MESSAGE = "controlMessage"

    private const val RECEIVE_PORT = 16000
    private const val SEND_PORT = 22000

    private val mSocket = DatagramSocket(RECEIVE_PORT)
    private val mSendSocket = DatagramSocket(SEND_PORT)
    private var mHost = ""
    private lateinit var mListener: OnUdpReceiverListener

    private class UdpReceiveThread : Thread() {
        override fun run() {
            super.run()
            while (true) {
                try {
                    var data = ByteArray(1024)
                    var packet = DatagramPacket(data, data.size)
                    mSocket.receive(packet)
                    val result = String(packet.data, packet.offset, packet.length) //packet 转换
                    val host = packet.address.toString().replace("/", "")
                    if (result.contains(TYPE_CONTROL_MESSAGE)) {
                        mListener.onReceiver(result)
                    }
                    Log.e("udpReceiver", "ip = $host result = $result")
                } catch (e: Exception) {
                    break
                }
            }
        }
    }

    fun receiver() {
        Observable.create<String> {
            while (true) {
                try {
                    var data = ByteArray(1024)
                    var packet = DatagramPacket(data, data.size)
                    mSocket.receive(packet)
                    val result = String(packet.data, packet.offset, packet.length) //packet 转换
                    val host = packet.address.toString().replace("/", "")
                    if (result.contains(TYPE_CAR_MESSAGE)) {
                        it.onNext(result)
                    }
                    Log.e("udpReceiver", "ip = $host result = $result")
                } catch (e: Exception) {
                    break
                }
            }
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mListener.onReceiver(it)
                }
    }

    fun setIp(context: Context) {
        mHost = NetworkUtil.getLocalIPAddress(context)
    }

    fun sendMessage(message: String) {

        val packet = DatagramPacket(message.toByteArray(),
                message.length,
                InetAddress.getByName("192.168.23.119"),
                SEND_PORT)

        Observable.create<Int> {
            mSendSocket.send(packet)
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ()

    }

    fun createMessage(carMessage: CarMessage): String {
        val gson = Gson()
        carMessage.type = TYPE_CAR_MESSAGE
        return gson.toJson(carMessage)
    }

    fun start(listener: OnUdpReceiverListener) {
        receiver()
        mListener = listener
    }

    fun stop() {
    }

    interface OnUdpReceiverListener {
        fun onReceiver(result: String)
    }

}