package com.lc.androidapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.lc.androidapp.R;
import com.lc.androidapp.adapter.TestAdapter;
import com.lc.androidapp.bean.TestData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2017/11/15.
 */

public class ListViewTestActivity extends Activity{

    private List<TestData> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_listview);
        Context context = this;

        initData();

        ListView listView = (ListView) findViewById(R.id.lv_test);
        TestAdapter adapter = new TestAdapter(context, mDatas);
        listView.setAdapter(adapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            TestData data = new TestData();
            data.setTitle(""+i);
            data.setBody("body");
            data.setFoot("foot");
            mDatas.add(data);
        }
    }
}
