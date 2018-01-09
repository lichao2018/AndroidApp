package com.lc.androidapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lc.androidapp.R;
import com.lc.androidapp.bean.ZhiHuStory;
import com.lc.androidapp.presenter.ZhihuCallback;
import com.lc.androidapp.presenter.ZhihuPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lichao on 2016/12/19.
 */
public class DescriptionActivity extends Activity{
    @InjectView(R.id.description_img)
    ImageView iv;
    @InjectView(R.id.description_title)
    TextView tvTitle;
    @InjectView(R.id.description_webview)
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.inject(this);

        int id = getIntent().getIntExtra("id", 0);
        String title = getIntent().getStringExtra("title");
        String imgUrl = getIntent().getStringExtra("image");

        new ZhihuPresenter().getZhihuStroty(id, new ZhihuCallback<ZhiHuStory>() {
            @Override
            public void onResult(ZhiHuStory zhiHuStory) {
                wv.loadUrl(zhiHuStory.getShare_url());
            }
        });
    }
}
