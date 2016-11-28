package com.lc.androidapp.api;

import com.lc.androidapp.bean.ZhiHuDaily;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lichao on 2016/11/28.
 */
public interface ZhiHuApi {
    @GET("api/4/news/latest")
    public Call<ZhiHuDaily> userInfo();
}
