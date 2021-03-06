package com.lc.scan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lc.scan.R;
import com.lc.scan.bean.ZhihuStory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2017/11/15.
 */

public class ZhihuAdapter extends BaseAdapter implements IBaseAdapter<List<ZhihuStory>>{

    private List<ZhihuStory> mDatas;
    private Context mContext;

    public ZhihuAdapter(Context context){
        mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public void updateDatas(List<ZhihuStory> datas){
        mDatas.addAll(0, datas);
        notifyDataSetChanged();
    }

    @Override
    public void addDatas(List<ZhihuStory> datas){
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void clearDatas() {
        mDatas.clear();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_zhihu, parent, false);
            viewHodler = new ViewHodler();
            viewHodler.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tvTitle.setText(mDatas.get(position).getTitle());
        return convertView;
    }

    private class ViewHodler{
        TextView tvTitle;
    }
}
