package com.alen.alen.Net;

import com.alen.alen.module.AiAsk;
import com.alen.alen.module.AiRecive;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Alen on 2018/7/17.
 */

public interface Api {

    @POST
    Call<AiRecive> request(@Body AiAsk aiAsk);
}
