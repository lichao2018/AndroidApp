package com.lc.androidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lc.androidapp.R;
import com.lc.androidapp.bean.TestData;

import java.util.List;

/**
 * Created by lichao on 2017/11/15.
 */

public class TestAdapter extends BaseAdapter{

    private List<TestData> mDatas;
    private Context mContext;

    public TestAdapter(Context context, List<TestData> datas){
        mContext = context;
        mDatas = datas;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
            viewHodler = new ViewHodler();
            viewHodler.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHodler.tvBody = (TextView) convertView.findViewById(R.id.tv_body);
            viewHodler.tvFoot = (TextView) convertView.findViewById(R.id.tv_foot);
            convertView.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tvTitle.setText(mDatas.get(position).getTitle());
        viewHodler.tvBody.setText(mDatas.get(position).getBody());
        viewHodler.tvFoot.setText(mDatas.get(position).getFoot());
        return convertView;
    }

    private class ViewHodler{
        TextView tvTitle;
        TextView tvBody;
        TextView tvFoot;
    }
}
