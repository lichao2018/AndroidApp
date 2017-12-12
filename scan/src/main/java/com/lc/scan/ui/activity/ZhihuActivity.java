package com.lc.scan.ui.activity;

import android.content.Context;
import android.os.Bundle;

import com.lc.scan.R;

/**
 * Created by lichao on 2017/11/15.
 */

public class ZhihuActivity extends BaseActivity{

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        mContext = this;

        setSwipeBackEnable(false);
    }
}
