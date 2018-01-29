package com.lc.scan.ui.fragment;

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

import com.lc.scan.adapter.ZhihuAdapter;
import com.lc.scan.bean.ZhihuStory;
import com.lc.scan.presenter.IBasePresenter;
import com.lc.scan.presenter.ZhihuPresenter;
import com.lc.scan.ui.IBaseView;
import com.lc.scan.ui.activity.ZhihuStoryActivity;
import com.lc.scan.ui.view.MyListView;

import net.sourceforge.simcpux.R;

import java.util.List;

/**
 * Created by lichao on 2017/12/12.
 */

public class ZhihuFragment extends BaseFragment implements IBaseView<List<ZhihuStory>> {

    MyListView mListView;
    ZhihuAdapter mAdapter;
    TextView tvEmpty;
    IBasePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        initView(view);
        mPresenter = new ZhihuPresenter(this);
        mPresenter.loadData();
        return view;
    }

    private void initView(View view){
        mAdapter = new ZhihuAdapter(mContext);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty_zhihu);
        tvEmpty.setText("正在加载数据...");
        mListView = (MyListView) view.findViewById(R.id.lv_test);
        mListView.setAdapter(mAdapter);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setEmptyView(tvEmpty);
        mListView.setRefreshListener(new MyListView.RefreshListener() {
            @Override
            public void onPullRefresh() {
                mPresenter.updateData();
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, ZhihuStoryActivity.class);
                intent.putExtra("id", ((ZhihuStory)mAdapter.getItem(position-1)).getId());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void showDatas(final List<ZhihuStory> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addDatas(datas);
            }
        });
    }

    @Override
    public void refreshData(final List<ZhihuStory> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(datas != null) {
                    mAdapter.updateDatas(datas);
                }
                mListView.completeRefresh();
            }
        });
    }

    @Override
    public void showMore(final List<ZhihuStory> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addDatas(datas);
                mListView.completeRefresh();
            }
        });
    }

    @Override
    public void showMoreErr() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.completeRefresh();
                Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showLoadErr() {
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
                                        mPresenter.loadData();
                                    }
                                }).start();
                            }
                        }, 1500);
                    }
                });
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
