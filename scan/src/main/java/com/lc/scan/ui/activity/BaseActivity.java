package com.lc.scan.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by lichao on 2017/12/8.
 */

public class BaseActivity extends SwipeBackActivity{
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }
}
