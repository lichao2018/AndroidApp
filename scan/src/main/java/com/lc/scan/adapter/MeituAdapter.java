package com.lc.scan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lc.scan.R;
import com.lc.scan.bean.Gank;

import java.util.List;

/**
 * Created by lichao on 2017/12/19.
 */

public class MeituAdapter extends RecyclerView.Adapter<MeituAdapter.ViewHolder>{
    private Context mContext;
    private List<Gank> mGankList;

    public MeituAdapter(Context context, List<Gank> gankList){
        mContext = context;
        mGankList = gankList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meitu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(mGankList.get(position).getUrl()).into(holder.mImageView);
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
