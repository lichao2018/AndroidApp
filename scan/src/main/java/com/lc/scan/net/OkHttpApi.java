package com.lc.scan.net;

import com.lc.scan.callback.HttpCallback;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lichao on 2017/11/19.
 */

public class OkHttpApi {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    static{
        mOkHttpClient.setConnectTimeout(5, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(5, TimeUnit.SECONDS);
    }

    private static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    private static void enqueue(Request request, Callback responseCallback){
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    private static void enqueue(Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }

    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if(response.isSuccessful()){
            String responseUrl = response.body().string();
            return responseUrl;
        }else{
            throw new IOException("Unexpected code " + response);
        }
    }

    public static void getStringFromServer(String url, final HttpCallback callback){
        Request request = new Request.Builder().url(url).build();
        enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseUrl = response.body().string();
                callback.onResult(responseUrl);
            }
        });
    }

    private static final String CHARSET_NAME = "UTF-8";
    private static String formatParams(List<BasicNameValuePair> params){
        return URLEncodedUtils.format(params, CHARSET_NAME);
    }

    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params){
        return url + "?" + formatParams(params);
    }

    public static String attachHttpGetParam(String url, String name, String value){
        return url + "?" + name + "=" + value;
    }
}
