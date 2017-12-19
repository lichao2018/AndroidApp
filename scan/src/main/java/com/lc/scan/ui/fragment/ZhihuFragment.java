package com.lc.scan.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lc.scan.R;
import com.lc.scan.adapter.ZhihuAdapter;
import com.lc.scan.bean.ZhihuNews;
import com.lc.scan.bean.ZhihuStory;
import com.lc.scan.callback.HttpCallback;
import com.lc.scan.net.OkHttpApi;
import com.lc.scan.ui.activity.ZhihuStoryActivity;
import com.lc.scan.ui.view.MyListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2017/12/12.
 */

public class ZhihuFragment extends Fragment {

    private List<ZhihuStory> mStories;
    private ZhihuNews mZhihuNews;
    MyListView mListView;
    ZhihuAdapter mAdapter;
    TextView tvEmpty;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        initView(view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }).start();
        return view;
    }

    private void initView(View view){
        mStories = new ArrayList<>();
        mAdapter = new ZhihuAdapter(mContext, mStories);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        tvEmpty.setText("正在加载数据...");
        mListView = (MyListView) view.findViewById(R.id.lv_test);
        mListView.setAdapter(mAdapter);
        mListView.setVerticalScrollBarEnabled(false);
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mListView.completeRefresh();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListView.completeRefresh();
                                Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mListView.completeRefresh();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListView.completeRefresh();
                                Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void loadData() {
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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvEmpty.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    tvEmpty.setTextColor(Color.BLUE);
                    tvEmpty.setText("网络加载失败，点击重试");
                    tvEmpty.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvEmpty.getPaint().setFlags(Paint.LINEAR_TEXT_FLAG);
                            tvEmpty.setTextColor(Color.BLACK);
                            tvEmpty.setText("正在加载数据...");
                            mListView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadData();
                                        }
                                    }).start();
                                }
                            }, 1500);

                        }
                    });
                }
            });
            e.printStackTrace();
        }finally {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
