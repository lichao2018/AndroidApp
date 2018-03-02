package com.lc.scan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lc.scan.R;
import com.lc.scan.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.io.ByteArrayOutputStream;

/**
 * Created by lichao on 2018/1/29.
 */

public class MyWeixinUtil {
    public static void openWeixin(IWXAPI iwxapi){
        iwxapi.openWXApp();
    }

    public static void shareText(IWXAPI iwxapi, String text){
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = "msg from lc app";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  //分享到消息列表
        //req.scene = SendMessageToWX.Req.WXSceneTimeline;  //分享到朋友圈
        iwxapi.sendReq(req);
    }

    public static void shareImg(IWXAPI iwxapi, Bitmap bitmap){
        WXImageObject imgObj = new WXImageObject(bitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);
    }

    public static void shareUrl(IWXAPI iwxapi, String url, Context context){
        WXWebpageObject webPageObj = new WXWebpageObject();
        webPageObj.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage(webPageObj);
        msg.title = "我的百度";
        msg.description = "就是百度";
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
        msg.thumbData = bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("url");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);
    }

    public static void getToken(IWXAPI iwxapi){
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";             //授权域，要获取的内容，个人信息就是snsapi_userinfo
        req.state = "myTest";                      //会话密钥，微信的回调会原样返回
        iwxapi.sendReq(req);
    }

    public static void getAccessToken(String code){

    }

    public static void registerAppToWX(IWXAPI iwxapi){
        iwxapi.registerApp(WXEntryActivity.WECHAT_APP_ID);
    }

    public static void unregisterAppToWX(IWXAPI iwxapi){
        iwxapi.unregisterApp();
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
