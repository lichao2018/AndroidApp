package com.lc.scan.ui.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
    private List<Drawable> mDrawableList;

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
        mDrawableList = new ArrayList<>();
        mMeituAdapter = new MeituAdapter(getActivity(), mDrawableList);
        mRecyclerView.setAdapter(mMeituAdapter);
    }

    private void loadData(){
        OkHttpApi.getStringFromServer("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/0", new HttpCallback() {
            @Override
            public void onResult(String result) {
                Gson gson = new Gson();
                BaseGankResponse baseGankResponse = gson.fromJson(result, BaseGankResponse.class);
                final List<Gank> gankList = baseGankResponse.getResults();
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                for(Gank gank : gankList){
                    Glide.with(getActivity()).load(gank.getUrl()).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            mDrawableList.add(resource);
                            mMeituAdapter.notifyDataSetChanged();
                        }
                    });
                }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
