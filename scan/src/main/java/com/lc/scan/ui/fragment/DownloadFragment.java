package com.lc.scan.ui.fragment;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lc.scan.R;
import com.lc.scan.service.DownloadService;

/**
 * Created by lichao on 2017/12/29.
 */

public class DownloadFragment extends BaseFragment{
    Button btnDownload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        final Intent intent = new Intent(getActivity(), DownloadService.class);
        btnDownload = view.findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().startService(intent);
            }
        });
        getActivity().bindService(intent, conn, Service.BIND_AUTO_CREATE);
        return view;
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
