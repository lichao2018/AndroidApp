package com.lc.androidapp.okhttptest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.lc.androidapp.R;

/**
 * Created by lichao on 2017/11/28.
 */

public class MyListView extends ListView implements AbsListView.OnScrollListener{
    private int downY;
    private View headerView;
    private int headerHight;
    private View footerView;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setOnScrollListener(this);
        initHeaderView();
        initFooterView();
    }

    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.layout_header, null);
        headerView.measure(0,0);
        headerHight = headerView.getMeasuredHeight();
        headerView.setPadding(0,-headerHight, 0,0);
        addHeaderView(headerView);
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.layout_footer, null);
        footerView.measure(0, 0);
        footerView.setPadding(0, -footerView.getMeasuredHeight(), 0, 0);
        addFooterView(footerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = (int)(ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int)(ev.getY());
                int padding = moveY - downY;
                if(padding >= headerHight){
                    padding = headerHight;
                }
                headerView.setPadding(0, -(headerHight - padding), 0, 0);
                break;
            case MotionEvent.ACTION_UP:
                headerView.setPadding(0, -headerHight, 0, 0);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_TOUCH_SCROLL && getLastVisiblePosition() == (getCount()-1)){
            footerView.setPadding(0, 0, 0, 0);
        }
        if(scrollState == SCROLL_STATE_IDLE && getLastVisiblePosition() == (getCount()-1)){
            footerView.setPadding(0, -footerView.getMeasuredHeight(), 0, 0);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
