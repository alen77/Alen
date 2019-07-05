package com.deepblue.chef.chefapp.bean

class ControlMessage {
    var type = "controlMessage"
    var time = 0L
    val commands = ArrayList<CarMessage>()
}