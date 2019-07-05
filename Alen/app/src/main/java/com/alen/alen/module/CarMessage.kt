package com.deepblue.chef.chefapp.bean

class CarMessage {
    enum class Status {
        FORWARD,
        TAKING,
        GOHOME,
        AVOID,
        READY,
        ELSE,
        PAUSE,
    }

    var type = ""
    var carCode = ""
    var selfLocation = 0
    var targetLocation = 0
    var status = Status.ELSE
    var faceId = ""

    var avoid = ""
}