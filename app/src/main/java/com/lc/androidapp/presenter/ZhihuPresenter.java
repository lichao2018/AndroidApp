package com.lc.androidapp.presenter;

import android.util.Log;

import com.lc.androidapp.api.ZhiHuApi;
import com.lc.androidapp.bean.ZhiHuDaily;
import com.lc.androidapp.bean.ZhiHuStory;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lichao on 2016/12/16.
 */
public class ZhihuPresenter {
    private ZhiHuDaily mZhihuDaily;

    public ZhiHuDaily getZhihuDaily(){
        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://news-at.zhihu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ZhiHuApi zhihuApi = retrofit.create(ZhiHuApi.class);
        Call<ZhiHuDaily> call = zhihuApi.getLastDaily();
        call.enqueue(new Callback<ZhiHuDaily>() {
            @Override
            public void onResponse(Call<ZhiHuDaily> call, Response<ZhiHuDaily> response) {
                mZhihuDaily = response.body();
            }

            @Override
            public void onFailure(Call<ZhiHuDaily> call, Throwable t) {
                Log.e("retrofit onFailure ", t.toString());
            }
        });
        return mZhihuDaily;
    }
}
