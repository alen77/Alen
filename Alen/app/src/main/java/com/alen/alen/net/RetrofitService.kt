package com.alen.alen.net

import com.alen.alen.module.AiAsk
import com.alen.alen.module.AiRecive
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Alen on 2018/7/17.
 */
interface RetrofitService {
    @POST("openapi/api/v2")
    fun request(@Body ask: AiAsk): Observable<AiRecive>
}