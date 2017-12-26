package com.lc.scan.ui.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.lc.scan.R;
import com.lc.scan.adapter.MeituAdapter;
import com.lc.scan.bean.BaseGankResponse;
import com.lc.scan.bean.Gank;
import com.lc.scan.callback.HttpCallback;
import com.lc.scan.net.OkHttpApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2017/12/14.
 */

public class MeituFragment extends Fragment {

    private List<Gank> mGankList;
    RecyclerView mRecyclerView;
    MeituAdapter mMeituAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private int pageCount = 1;
    private boolean isLoading = false;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meitu, container, false);
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
        tvEmpty = view.findViewById(R.id.tv_empty_meitu);
        tvEmpty.setText("正在加载数据...");
        mRecyclerView = view.findViewById(R.id.recyclerview_meitu);
        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mGankList = new ArrayList<>();
        mMeituAdapter = new MeituAdapter(getActivity(), mGankList);
        mRecyclerView.setAdapter(mMeituAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1) && !isLoading){
                    isLoading = true;
                    loadData();
                }
            }
        });
        mRefreshLayout = view.findViewById(R.id.swipe_refresh_meitu);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里实现下拉刷新
                pageCount = 1;
                mGankList.clear();
                loadData();
            }
        });
    }

    private void loadData(){
        mRefreshLayout.setRefreshing(true);
        OkHttpApi.getStringFromServer("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/" + pageCount, new HttpCallback() {
            @Override
            public void onResult(String result) {
                tvEmpty.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                BaseGankResponse baseGankResponse = gson.fromJson(result, BaseGankResponse.class);
                List<Gank> gankList = baseGankResponse.getResults();
                try {
                    for (Gank gank : gankList) {
                        Drawable drawable = Glide.with(getActivity()).load(gank.getUrl()).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                        gank.setGirlWidth(drawable.getIntrinsicWidth()/3);
                        gank.setGirlHeight(drawable.getIntrinsicHeight()/3);
                        mGankList.add(gank);
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                mMeituAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    pageCount++;
                    loadComplete();
                }
            }

            @Override
            public void onFailure(Exception e) {
                loadComplete();
                mRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmpty.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                        tvEmpty.setTextColor(Color.BLUE);
                        tvEmpty.setText("网络加载失败，点击重试");
                        tvEmpty.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tvEmpty.getPaint().setFlags(Paint.LINEAR_TEXT_FLAG);
                                tvEmpty.setTextColor(Color.BLACK);
                                tvEmpty.setText("正在加载数据...");
                                mRefreshLayout.setRefreshing(true);
                                tvEmpty.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadData();
                                    }
                                }, 1500);
                            }
                        });
                    }
                });
            }
        });
    }

    private void loadComplete(){
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        });
        isLoading = false;
    }
}
