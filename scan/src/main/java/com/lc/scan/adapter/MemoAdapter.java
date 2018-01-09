package com.lc.scan.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lc.scan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2017/12/29.
 */

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>{

    private List<Integer> mList;
    private Context mContext;
    private List<Boolean> mIsClickedList;

    public MemoAdapter(Context context){
        mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meitu, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mImageView.setImageResource(mList.get(position));
        holder.mImageView.setTag(true);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(position == (mList.size()-1)){
                    return;
                }
                if((Boolean) holder.mImageView.getTag()){
                    holder.mImageView.setImageResource(R.mipmap.ic_launcher_round);
                    holder.mImageView.setTag(false);
                }else{
                    holder.mImageView.setImageResource(R.mipmap.ic_launcher);
                    holder.mImageView.setTag(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(int resource){
        mList.add(resource);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_meitu);
        }
    }
}
