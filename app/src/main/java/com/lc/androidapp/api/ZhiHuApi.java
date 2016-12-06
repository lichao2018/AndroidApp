package com.lc.androidapp.api;

import com.lc.androidapp.bean.Stories;
import com.lc.androidapp.bean.ZhiHuDaily;
import com.lc.androidapp.bean.ZhiHuStory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lichao on 2016/11/28.
 */
public interface ZhiHuApi {
    @GET("api/4/news/latest")
    Call<ZhiHuDaily> getLastDaily();

    @GET("api/4/news/before/{date}")
    Call<ZhiHuDaily> getTheDaily(@Path("date") String date);

    @GET("api/4/news/{id}")
    Call<ZhiHuStory> getZhiHuStory(@Path("id") int id);
}
