package com.lc.androidapp.okhttptest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lc.androidapp.R;

import java.util.List;

/**
 * Created by lichao on 2017/11/15.
 */

public class ListViewTestActivity extends Activity{

    private List<ZhihuNews> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_listview);
        Context context = this;

        initData();

        ListView listView = (ListView) findViewById(R.id.lv_test);
        TestAdapter adapter = new TestAdapter(context, mDatas);
        listView.setAdapter(adapter);
    }

    private void initData() {
        OkHttpUtil.getStringFromServer("http://news-at.zhihu.com/api/4/news/latest", new HttpCallback() {
            @Override
            public void onResult(String result) {
                Gson gson = new Gson();
                mDatas = gson.fromJson(result, new TypeToken<List<ZhihuNews>>(){}.getType());
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}
