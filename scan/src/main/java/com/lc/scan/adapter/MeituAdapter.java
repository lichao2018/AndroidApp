package com.lc.scan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lc.scan.R;
import com.lc.scan.bean.Gank;
import com.lc.scan.ui.activity.PictureActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by lichao on 2017/12/19.
 */

public class MeituAdapter extends RecyclerView.Adapter<MeituAdapter.ViewHolder>{
    private Context mContext;
    private List<Gank> mGankList;
    private List<Map<Integer, Integer>> mHeight;

    public MeituAdapter(Context context, List<Gank> gankList){
        mContext = context;
        mGankList = gankList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mHeight = new ArrayList<>();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meitu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
        layoutParams.height = mGankList.get(position).getGirlHeight();
        holder.mImageView.setLayoutParams(layoutParams);
        holder.mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = PictureActivity.newIntent(mContext, mGankList.get(position).getUrl());
                mContext.startActivity(intent);
            }
        });

        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(android.R.color.white);
        Glide.with(mContext).load(mGankList.get(position).getUrl()).apply(options).transition(withCrossFade()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public ViewHolder(View view){
            super(view);
            mImageView = view.findViewById(R.id.iv_meitu);
        }
    }
}
