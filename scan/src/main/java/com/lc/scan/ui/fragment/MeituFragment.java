package com.lc.scan.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lc.scan.R;
import com.lc.scan.adapter.MeituAdapter;

/**
 * Created by lichao on 2017/12/14.
 */

public class MeituFragment extends Fragment {

    private String[] mDatas = {"aaa", "bbb", "ccc"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meitu, container, false);

        initView(view);

        return view;
    }

    private void initView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_meitu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MeituAdapter meituAdapter = new MeituAdapter(mDatas);
        recyclerView.setAdapter(meituAdapter);
    }
}
