package com.lc.scan.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by lichao on 2017/12/29.
 */

public class DownloadService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://github.com/lichao2018/AndroidApp/archive/master.zip"));
        request.setDestinationInExternalPublicDir("/Download/", "a.zip");
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
