package com.alen.alen.net

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Alen on 2018/7/17.
 */
class RetrofitUtil private constructor() {
    private lateinit var retrofitService: RetrofitService

    companion object {
        fun getInstance() = Helper.instance
    }

    private object Helper {
        val instance = RetrofitUtil()
    }

    private fun getBuilder() {
        val build = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.w("retrofit url", it)
        })
        logging.level = HttpLoggingInterceptor.Level.BODY

        build.addNetworkInterceptor(logging)

        retrofitService = Retrofit.Builder()
                .client(build.build())
                .baseUrl("http://openapi.tuling123.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RetrofitService::class.java)
    }

    fun getService(): RetrofitService {
        getBuilder()
        return retrofitService
    }
}