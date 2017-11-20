package com.lc.androidapp.okhttptest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.lc.androidapp.R;

/**
 * Created by lichao on 2017/11/20.
 */

public class ZhihuStoryActivity extends Activity{
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        mWebView = (WebView) findViewById(R.id.webview);
        int id = getIntent().getIntExtra("id", 0);
        OkHttpUtil.getStringFromServer("http://news-at.zhihu.com/api/4/news/" + id, new HttpCallback() {
            @Override
            public void onResult(String result) {
                Gson gson = new Gson();
                String json = result.substring(1, result.length()-1).replace("\\", "");
                final ZhihuStoryContent storyContent = gson.fromJson(result, ZhihuStoryContent.class);
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl(storyContent.getShare_url());
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
