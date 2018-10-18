package com.alen.alen.module


data class AiRecive(
        val intent: Intent,
        val results: List<Result>) {

    data class Intent(
            val code: Int,
            val intentName: String,
            val actionName: String,
            val parameters: Parameters) {

        data class Parameters(val nearby_place: String)
    }


    data class Result(
            val groupType: Int,
            val resultType: String,
            val values: Values) {

        data class Values(val text: String)
    }
}