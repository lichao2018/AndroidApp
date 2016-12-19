package com.lc.androidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lc.androidapp.R;
import com.lc.androidapp.activity.DescriptionActivity;
import com.lc.androidapp.bean.ZhiHuDaily;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2016/12/16.
 */
public class ZhiHuAdapter extends RecyclerView.Adapter {
    private Context mContext;
    ZhiHuDaily mZhihuDaily;

    public ZhiHuAdapter(Context context, ZhiHuDaily zhihuDaily){
        mContext = context;
        mZhihuDaily = zhihuDaily;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.zhihu_layout_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder mHolder = (MyViewHolder)holder;
        mHolder.tv.setText(mZhihuDaily.getStories()[position].getTitle());
        mHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra("id", mZhihuDaily.getStories()[position].getId());
                intent.putExtra("title", mZhihuDaily.getStories()[position].getTitle());
                intent.putExtra("image", mZhihuDaily.getStories()[position].getImages()[0]);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mZhihuDaily.getStories().length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        MyViewHolder(View v){
            super(v);
            tv = (TextView) v.findViewById(R.id.zhihu_item_tv);
        }
    }
}
