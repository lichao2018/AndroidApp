package com.lc.scan.wxapi;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lc.scan.R;
import com.lc.scan.ui.activity.BaseActivity;
import com.lc.scan.ui.fragment.DownloadFragment;
import com.lc.scan.ui.fragment.MeituFragment;
import com.lc.scan.ui.fragment.MemoFragment;
import com.lc.scan.ui.fragment.WeixinFragment;
import com.lc.scan.ui.fragment.ZhihuFragment;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by lichao on 2017/11/15.
 */

public class WXEntryActivity extends BaseActivity implements View.OnClickListener, IWXAPIEventHandler{

    Context mContext;
    NavigationView mNavigationView;
    ImageView ivDrawer;
    DrawerLayout mDrawerLayout;
    TextView tvHeader;

    public static String WECHAT_APP_ID = "wxd930ea5d5a258f4f";

    Fragment mZhihuFragment, mMeituFragment, mDownloadFragment, mMemoFragment, mWeixinFragment, currentFragment;
    IWXAPI mWxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        mContext = this;

        mWxApi = WXAPIFactory.createWXAPI(this, WECHAT_APP_ID);
        mWxApi.handleIntent(getIntent(), this);

        setSwipeBackEnable(false);
        initView();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
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
                    case R.id.menu_item_meitu:
                        if(mMeituFragment == null){
                            mMeituFragment = new MeituFragment();
                        }
                        switchContainer(mMeituFragment);
                        break;
                    case R.id.menu_item_download:
                        if(mDownloadFragment == null){
                            mDownloadFragment = new DownloadFragment();
                        }
                        switchContainer(mDownloadFragment);
                        break;
                    case R.id.menu_item_memo:
                        if(mMemoFragment == null){
                            mMemoFragment = new MemoFragment();
                        }
                        switchContainer(mMemoFragment);
                        break;
                    case R.id.menu_item_weixin:
                        if(mWeixinFragment == null){
                            mWeixinFragment = new WeixinFragment();
                        }
                        switchContainer(mWeixinFragment);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("scan", "onNewIntent");
    }

    @Override
    public void onReq(BaseReq req) {
        Log.e("scan", req.toString());
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("scan", resp.errStr);
    }
}
