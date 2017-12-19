package com.lc.scan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lc.scan.R;

/**
 * Created by lichao on 2017/12/19.
 */

public class MeituAdapter extends RecyclerView.Adapter<MeituAdapter.ViewHolder>{
    private String[] mDatas;

    public MeituAdapter(String[] datas){
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meitu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDatas[position]);
    }

    @Override
    public int getItemCount() {
        return mDatas.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = view.findViewById(R.id.tv_meitu);
        }
    }
}
