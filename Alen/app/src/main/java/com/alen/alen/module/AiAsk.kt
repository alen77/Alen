package com.alen.alen.module


data class AiAsk(
        val reqType: Int,
        val perception: Perception,
        val userInfo: UserInfo) {

    data class UserInfo(
            val apiKey: String,
            val userId: String
    )


    data class Perception(val inputText: InputText) {

        data class InputText(val text: String)
    }
}