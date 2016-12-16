package com.lc.androidapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lc.androidapp.R;
import com.lc.androidapp.bean.ZhiHuDaily;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2016/12/16.
 */
public class ZhiHuAdapter extends RecyclerView.Adapter {
    private Context mContext;
    ZhiHuDaily mZhihuDaily;

    public ZhiHuAdapter(Context context){
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.zhihu_layout_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder)holder;
        mHolder.tv.setText(mZhihuDaily.getStories()[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return mZhihuDaily.getStories().length;
    }

    public void setZhihuDaily(ZhiHuDaily zhihuDaily){
        mZhihuDaily = zhihuDaily;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        MyViewHolder(View v){
            super(v);
            tv = (TextView) v.findViewById(R.id.zhihu_item_tv);
        }
    }
}
