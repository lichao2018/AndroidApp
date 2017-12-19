package com.lc.scan.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lc.scan.R;
import com.lc.scan.ui.fragment.MeituFragment;
import com.lc.scan.ui.fragment.ZhihuFragment;

/**
 * Created by lichao on 2017/11/15.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{

    Context mContext;
    NavigationView mNavigationView;
    ImageView ivDrawer;
    DrawerLayout mDrawerLayout;
    TextView tvHeader;

    ZhihuFragment mZhihuFragment;
    MeituFragment mMeituFragment;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        mContext = this;

        setSwipeBackEnable(false);
        initView();
    }

    public void initView(){
        mZhihuFragment = (ZhihuFragment) getFragmentManager().findFragmentById(R.id.fragment);
        currentFragment = mZhihuFragment;
        tvHeader = (TextView) findViewById(R.id.header_title);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        ivDrawer = (ImageView) findViewById(R.id.iv_drawer);
        ivDrawer.setOnClickListener(this);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_zhihu:
                        if(mZhihuFragment == null){
                            mZhihuFragment = (ZhihuFragment) getFragmentManager().findFragmentById(R.id.fragment);
                        }
                        switchContainer(mZhihuFragment);
                        break;
                    case R.id.menu_item_renmin:
                    case R.id.menu_item_onepiece:
                    case R.id.menu_item_naruto:
                        if(mMeituFragment == null){
                            mMeituFragment = new MeituFragment();
                        }
                        switchContainer(mMeituFragment);
                        break;
                    default:
                        break;
                }
                tvHeader.setText(item.getTitle());
                mNavigationView.getMenu().clear();
                mNavigationView.inflateMenu(R.menu.navigation_menu);
                mNavigationView.getMenu().findItem(item.getItemId()).setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    public void switchContainer(Fragment targetFragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if(currentFragment != null){
            fragmentTransaction.hide(currentFragment);
        }
        if(!targetFragment.isAdded()){
            fragmentTransaction.add(R.id.frame_container, targetFragment).commit();
        }else{
            fragmentTransaction.show(targetFragment).commit();
        }
        currentFragment = targetFragment;
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