package com.lc.scan.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lc.scan.R;

import java.util.List;

/**
 * Created by lichao on 2017/12/19.
 */

public class MeituAdapter extends RecyclerView.Adapter<MeituAdapter.ViewHolder>{
    private Context mContext;
    private List<Drawable> mDrawableList;

    public MeituAdapter(Context context, List<Drawable> drawableList){
        mContext = context;
        mDrawableList = drawableList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meitu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drawable drawable = mDrawableList.get(position);
        int h = drawable.getIntrinsicHeight()/3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, h);
        holder.mImageView.setLayoutParams(layoutParams);
        holder.mImageView.setImageDrawable(mDrawableList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDrawableList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public ViewHolder(View view){
            super(view);
            mImageView = view.findViewById(R.id.iv_meitu);
        }
    }
}
