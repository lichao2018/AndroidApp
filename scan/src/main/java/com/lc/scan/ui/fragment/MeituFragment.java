package com.lc.scan.ui.fragment;

import android.graphics.Color;
import android.graphics.Paint;
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

import com.lc.scan.R;
import com.lc.scan.adapter.MeituAdapter;
import com.lc.scan.bean.Gank;
import com.lc.scan.presenter.IBasePresenter;
import com.lc.scan.presenter.MeituPresenter;
import com.lc.scan.ui.IBaseView;

import java.util.List;

/**
 * Created by lichao on 2017/12/14.
 */

public class MeituFragment extends BaseFragment implements IBaseView<List<Gank>>{

    RecyclerView mRecyclerView;
    MeituAdapter mMeituAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isLoading = false;
    private TextView tvEmpty;
    private IBasePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meitu, container, false);
        initView(view);
        mPresenter = new MeituPresenter(getActivity(), this);
        mPresenter.loadData();
        return view;
    }

    private void initView(View view){
        tvEmpty = view.findViewById(R.id.tv_empty_meitu);
        tvEmpty.setText("正在加载数据...");
        mRecyclerView = view.findViewById(R.id.recyclerview_meitu);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mMeituAdapter = new MeituAdapter(getActivity());
        mRecyclerView.setAdapter(mMeituAdapter);
        //上拉加载更多
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1) && !isLoading){
                    mPresenter.loadMore();
                }
            }
        });
        mRefreshLayout = view.findViewById(R.id.swipe_refresh_meitu);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里实现下拉刷新
                mPresenter.updateData();
            }
        });
    }

    @Override
    public void showDatas(final List<Gank> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMeituAdapter.addDatas(datas);
            }
        });
    }

    @Override
    public void showLoadErr() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
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
                                mPresenter.loadData();
                            }
                        }, 1500);
                    }
                });
            }
        });
    }

    @Override
    public void showMore(List<Gank> datas) {

    }

    @Override
    public void showMoreErr() {

    }

    @Override
    public void refreshData(final List<Gank> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMeituAdapter.updateDatas(datas);
            }
        });
    }

    @Override
    public void showLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isLoading = true;
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isLoading = false;
                mRefreshLayout.setRefreshing(false);
                tvEmpty.setVisibility(View.GONE);
            }
        });
    }
}
