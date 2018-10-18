package com.alen.alen.net

import com.alen.alen.module.AiAsk
import com.alen.alen.module.AiRecive

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Alen on 2018/7/17.
 */

interface Api {

    @POST
    fun request(@Body aiAsk: AiAsk): Call<AiRecive>
}
