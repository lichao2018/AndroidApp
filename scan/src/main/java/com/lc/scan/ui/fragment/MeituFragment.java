package com.lc.scan.ui.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        mRecyclerView = view.findViewById(R.id.recyclerview_meitu);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mGankList = new ArrayList<>();
        mMeituAdapter = new MeituAdapter(getActivity(), mGankList);
        mRecyclerView.setAdapter(mMeituAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //这里实现上拉加载更多
//                visibleItemCount = recyclerView.getChildCount();
//                totalItemCount = mLinearLayoutManager.getItemCount();
//                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
//                if(loading){
//                    //Log.d("wnwn","firstVisibleItem: " +firstVisibleItem);
//                    //Log.d("wnwn","totalPageCount:" +totalItemCount);
//                    //Log.d("wnwn", "visibleItemCount:" + visibleItemCount)；
//
//                    if(totalItemCount > previousTotal){
//                        //说明数据已经加载结束
//                        loading = false;
//                        previousTotal = totalItemCount;
//                    }
//                }
//                //这里需要好好理解
//                if (!loading && totalItemCount-visibleItemCount <= firstVisibleItem){
//                    currentPage ++;
//                    onLoadMore(currentPage);
//                    loading = true;
//                }
            }
        });
        mRefreshLayout = view.findViewById(R.id.swipe_refresh_meitu);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里实现下拉刷新
                loadData();
            }
        });
    }

    private void loadData(){
        OkHttpApi.getStringFromServer("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/" + pageCount, new HttpCallback() {
            @Override
            public void onResult(String result) {
                Gson gson = new Gson();
                BaseGankResponse baseGankResponse = gson.fromJson(result, BaseGankResponse.class);
                if(mGankList.size() != 0) {
                    if (baseGankResponse.getResults().get(0).get_id().equals(mGankList.get(0).get_id())){
                        mRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshLayout.setRefreshing(false);
                            }
                        });
                        return;
                    }
                }
                mGankList.addAll(0, baseGankResponse.getResults());

                try {
                    for (Gank gank : mGankList) {
                        Drawable drawable = Glide.with(getActivity()).load(gank.getUrl()).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                        gank.setGirlWidth(drawable.getIntrinsicWidth()/3);
                        gank.setGirlHeight(drawable.getIntrinsicHeight()/3);
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                mMeituAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                mRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                });
                pageCount++;
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
