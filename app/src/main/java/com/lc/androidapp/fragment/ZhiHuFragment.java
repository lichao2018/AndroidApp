package com.lc.androidapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lc.androidapp.R;
import com.lc.androidapp.adapter.ZhiHuAdapter;
import com.lc.androidapp.bean.ZhiHuDaily;
import com.lc.androidapp.presenter.ZhihuCallback;
import com.lc.androidapp.presenter.ZhihuPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lichao on 2016/11/21.
 */
public class ZhiHuFragment extends Fragment {
    @InjectView(R.id.recycle_zhihu)
    RecyclerView recycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ZhihuPresenter zhihuPresenter = new ZhihuPresenter();
        zhihuPresenter.getZhihuDaily(new ZhihuCallback<ZhiHuDaily>() {
            @Override
            public void onResult(ZhiHuDaily zhiHuDaily) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                ZhiHuAdapter mAdapter = new ZhiHuAdapter(getContext(), zhiHuDaily);

                recycleView.setLayoutManager(mLayoutManager);
                recycleView.setAdapter(mAdapter);
            }
        });
    }
}
