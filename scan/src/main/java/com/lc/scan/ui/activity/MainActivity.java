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
                            mZhihuFragment = new ZhihuFragment();
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
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                item.setChecked(true);
                return true;
            }
        });
    }

    public void switchContainer(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if(!fragment.isAdded()){
            fragmentTransaction.add(R.id.frame_container, fragment);
        }else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }

    public void hideFragment(FragmentTransaction fragmentTransaction){
        if(mZhihuFragment != null) {
            fragmentTransaction.hide(mZhihuFragment);
        }
        if(mMeituFragment != null) {
            fragmentTransaction.hide(mMeituFragment);
        }
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
