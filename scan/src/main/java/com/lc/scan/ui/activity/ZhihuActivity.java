package com.lc.scan.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lc.scan.R;
import com.lc.scan.adapter.ZhihuAdapter;
import com.lc.scan.bean.ZhihuNews;
import com.lc.scan.bean.ZhihuStory;
import com.lc.scan.callback.HttpCallback;
import com.lc.scan.net.OkHttpApi;
import com.lc.scan.ui.view.MyListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2017/11/15.
 */

public class ZhihuActivity extends Activity{

    private List<ZhihuStory> mStories;
    private ZhihuNews mZhihuNews;
    MyListView mListView;
    ZhihuAdapter mAdapter;
    Context mContext;
    TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        mContext = this;

        mListView = (MyListView) findViewById(R.id.lv_test);
        mStories = new ArrayList<>();
        mAdapter = new ZhihuAdapter(mContext, mStories);
        mListView.setAdapter(mAdapter);
        tvEmpty = (TextView)findViewById(R.id.tv_empty);
        tvEmpty.setText("正在加载数据...");
        mListView.setEmptyView(tvEmpty);
        mListView.setRefreshListener(new MyListView.RefreshListener() {
            @Override
            public void onPullRefresh() {
                OkHttpApi.getStringFromServer("http://news-at.zhihu.com/api/4/news/latest", new HttpCallback() {
                    @Override
                    public void onResult(String result) {
                        Gson gson = new Gson();
                        ZhihuNews zhihuNews = gson.fromJson(result, ZhihuNews.class);
                        List<ZhihuStory> zhihuStories = zhihuNews.getStories();
                        for(int i = zhihuStories.size()-1; i >= 0; i --){
                            boolean exist = false;
                            ZhihuStory newZhihuStory = zhihuStories.get(i);
                            for(int j = mStories.size()-1; j >= 0; j --){
                                ZhihuStory zhihuStory = mStories.get(j);
                                if(newZhihuStory.getId() == zhihuStory.getId()){
                                    exist = true;
                                    break;
                                }
                            }
                            if(!exist){
                                mStories.add(0, newZhihuStory);
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mListView.completeRefresh();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }

            @Override
            public void onLoadMore() {
                OkHttpApi.getStringFromServer("http://news-at.zhihu.com/api/4/news/before/" + mZhihuNews.getDate(), new HttpCallback() {
                    @Override
                    public void onResult(String result) {
                        Gson gson = new Gson();
                        mZhihuNews = gson.fromJson(result, ZhihuNews.class);
                        mStories.addAll(mZhihuNews.getStories());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mListView.completeRefresh();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }
        });
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = OkHttpApi.getStringFromServer("http://news-at.zhihu.com/api/4/news/latest");
                    Gson gson = new Gson();
                    mZhihuNews = gson.fromJson(result, ZhihuNews.class);
                    mStories.addAll(mZhihuNews.getStories());
                    if(mStories.size()<19){
                        result = OkHttpApi.getStringFromServer("http://news-at.zhihu.com/api/4/news/before/" + mZhihuNews.getDate());
                        mZhihuNews = gson.fromJson(result, ZhihuNews.class);
                        mStories.addAll(mZhihuNews.getStories());
                    }
                    mListView.post(new Runnable() {
                        @Override
                        public void run() {
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
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvEmpty.setText("网络加载失败");
                        }
                    });
                    e.printStackTrace();
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }
}
