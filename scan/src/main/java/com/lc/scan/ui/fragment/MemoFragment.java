package com.lc.scan.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lc.scan.adapter.MemoAdapter;

import net.sourceforge.simcpux.R;

/**
 * Created by lichao on 2017/12/29.
 */

public class MemoFragment extends BaseFragment{

    private RecyclerView mRecyclerView;
    private Context mContext;
    private MemoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view){
        mContext = getActivity();
        mRecyclerView = view.findViewById(R.id.recyclerview_memo);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 6);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MemoAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(){
        for(int i = 0; i < 9; i ++){
            mAdapter.addData(R.mipmap.ic_launcher_round);
        }
        mAdapter.addData(R.mipmap.ic_drawer);
    }
}
