package com.alen.alen.Net

import com.alen.alen.module.AiAsk
import com.alen.alen.module.AiRecive
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Alen on 2018/7/17.
 */
interface RetrofitService {
    @POST("openapi/api/v2")
    fun request(@Body ask: AiAsk): Observable<AiRecive>
}