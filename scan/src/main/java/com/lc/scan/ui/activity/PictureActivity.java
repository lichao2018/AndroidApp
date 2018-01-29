package com.lc.scan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.sourceforge.simcpux.R;

/**
 * Created by lichao on 2017/12/29.
 */

public class PictureActivity extends BaseActivity{

    public static final String EXTRA_IMAGE_URL = "image_url";
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        initView();
    }

    public void initView(){
        mImageView = (ImageView) findViewById(R.id.iv_picture);
        Intent intent = getIntent();
        String url = intent.getStringExtra(EXTRA_IMAGE_URL);
        Glide.with(mContext).load(url).into(mImageView);
    }

    public static Intent newIntent(Context context, String url){
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, url);
        return intent;
    }
}
