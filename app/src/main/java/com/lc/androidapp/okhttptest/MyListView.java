package com.lc.androidapp.okhttptest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.lc.androidapp.R;

/**
 * Created by lichao on 2017/11/28.
 */

public class MyListView extends ListView{
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        initHeaderView();
        initFooterView();
    }

    private void initHeaderView() {
        View header = View.inflate(getContext(), R.layout.layout_header, null);
        addHeaderView(header);
    }

    private void initFooterView() {
        View footer = View.inflate(getContext(), R.layout.layout_footer, null);
        addFooterView(footer);
    }
}
