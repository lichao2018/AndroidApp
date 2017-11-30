package com.lc.androidapp.okhttptest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lc.androidapp.R;

import java.util.List;

/**
 * Created by lichao on 2017/11/15.
 */

public class ListViewTestActivity extends Activity{

    private List<ZhihuStory> mStories;
    private ZhihuNews mZhihuNews;
    ListView mListView;
    TestAdapter mAdapter;
    Context mContext;
    TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_listview);
        mContext = this;

        mListView = (ListView) findViewById(R.id.lv_test);
        tvEmpty = (TextView)findViewById(R.id.tv_empty);
        tvEmpty.setText("正在加载数据...");
        mListView.setEmptyView(tvEmpty);
        initData();
    }

    private void initData() {
        OkHttpUtil.getStringFromServer("http://news-at.zhihu.com/api/4/news/latest", new HttpCallback() {
            @Override
            public void onResult(String result) {
                Gson gson = new Gson();
                mZhihuNews = gson.fromJson(result, ZhihuNews.class);
                mStories = mZhihuNews.getStories();
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new TestAdapter(mContext, mStories);
                        mListView.setAdapter(mAdapter);
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(mContext, ZhihuStoryActivity.class);
                                intent.putExtra("id", mStories.get(position-1).getId());
                                mContext.startActivity(intent);
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                tvEmpty.setText("网络加载失败");
            }
        });
    }
}
