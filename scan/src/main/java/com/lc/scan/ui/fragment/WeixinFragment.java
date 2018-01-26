package com.lc.scan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.sourceforge.simcpux.R;

/**
 * Created by lichao on 2018/1/24.
 */

public class WeixinFragment extends BaseFragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weixin, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        view.findViewById(R.id.btn_send_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_text:
                WXTextObject textObject = new WXTextObject();
                textObject.text = "12345";
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = textObject;
                msg.description ="12345";
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "text" + System.currentTimeMillis();
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                IWXAPI iwxapi = WXAPIFactory.createWXAPI(getActivity(), "wxd930ea5d5a258f4f");
                iwxapi.sendReq(req);
                break;
            default:
                break;
        }
    }
}
