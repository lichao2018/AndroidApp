package com.lc.androidapp.okhttptest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.lc.androidapp.R;

/**
 * Created by lichao on 2017/11/28.
 */

public class MyListView extends ListView implements AbsListView.OnScrollListener{
    private int downY;
    private View headerView;
    private TextView mTvHeader;
    private int headerHight;
    private View footerView;
    RefreshListener mRefreshListener;
    boolean isReFreshing = false;
    private Context mContext;
    private boolean isLoadingMore = false;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public void setRefreshListener(RefreshListener listener){
        mRefreshListener = listener;
    }

    public void completeRefresh(){
        if(isLoadingMore){
            footerView.setPadding(0, -footerView.getMeasuredHeight(), 0, 0);
            isLoadingMore = false;
        }else {
            headerView.setPadding(0, -headerHight, 0, 0);
            isReFreshing = false;
        }
    }

    private void initView() {
        setOnScrollListener(this);
        initHeaderView();
        initFooterView();
    }

    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.layout_header, null);
        mTvHeader = (TextView) headerView.findViewById(R.id.tv_header);
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
                if(isReFreshing){
                    break;
                }
                int deltaY = (int)(ev.getY() - downY);
                int padding = -headerHight + deltaY;
                if(padding > -headerHight && getFirstVisiblePosition() == 0) {
                    if(padding >= headerHight){
                        padding = headerHight;
                    }
                    headerView.setPadding(0, padding, 0, 0);
                    if(padding < 0){
                        mTvHeader.setText("下拉刷新");
                    }else if(padding > 0){
                        mTvHeader.setText("松开刷新");
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mRefreshListener != null && getFirstVisiblePosition() == 0) {
                    mRefreshListener.onPullRefresh();
                    mTvHeader.setText("正在刷新");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE && getLastVisiblePosition() == (getCount()-1) && !isLoadingMore){
            footerView.setPadding(0, 0, 0, 0);
            setSelection(getCount());
            mRefreshListener.onLoadMore();
            isLoadingMore = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public interface RefreshListener {
        void onPullRefresh();
        void onLoadMore();
    }
}
