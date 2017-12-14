package com.lc.scan.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.lc.scan.R;

/**
 * Created by lichao on 2017/11/15.
 */

public class ZhihuActivity extends BaseActivity implements View.OnClickListener{

    Context mContext;
    NavigationView mNavigationView;
    ImageView ivDrawer;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        mContext = this;

        setSwipeBackEnable(false);
        initView();
    }

    public void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        ivDrawer = (ImageView) findViewById(R.id.iv_drawer);
        ivDrawer.setOnClickListener(this);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_drawer:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            default:
                break;
        }
    }
}
