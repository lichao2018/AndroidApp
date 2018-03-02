package com.lc.scan.ui.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lc.scan.R;
import com.lc.scan.utils.MyWeixinUtil;
import com.lc.scan.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by lichao on 2018/1/24.
 */

public class WeixinFragment extends BaseFragment implements View.OnClickListener{

    IWXAPI mIWXAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weixin, container, false);
        mIWXAPI = WXAPIFactory.createWXAPI(getActivity(), WXEntryActivity.WECHAT_APP_ID);
        initView(view);
        return view;
    }

    public void initView(View view){
        view.findViewById(R.id.btn_start_weixin).setOnClickListener(this);
        view.findViewById(R.id.btn_share_text).setOnClickListener(this);
        view.findViewById(R.id.btn_share_img).setOnClickListener(this);
        view.findViewById(R.id.btn_share_url).setOnClickListener(this);
        view.findViewById(R.id.btn_get_weixin_token).setOnClickListener(this);
        view.findViewById(R.id.btn_register_app_to_weixin).setOnClickListener(this);
        view.findViewById(R.id.btn_unregister_app).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_weixin:
                MyWeixinUtil.openWeixin(mIWXAPI);
                break;
            case R.id.btn_share_text:
                MyWeixinUtil.shareText(mIWXAPI, "hello weixin friend");
                break;
            case R.id.btn_share_img:
                MyWeixinUtil.shareImg(mIWXAPI, BitmapFactory.decodeResource(getResources(), R.mipmap.cat));
                break;
            case R.id.btn_share_url:
                MyWeixinUtil.shareUrl(mIWXAPI, "www.baidu.com", getActivity());
                break;
            case R.id.btn_get_weixin_token:
                MyWeixinUtil.getToken(mIWXAPI);
                break;
            case R.id.btn_register_app_to_weixin:
                MyWeixinUtil.registerAppToWX(mIWXAPI);
                break;
            case R.id.btn_unregister_app:
                MyWeixinUtil.unregisterAppToWX(mIWXAPI);
                break;
            default:
                break;
        }
    }
}
