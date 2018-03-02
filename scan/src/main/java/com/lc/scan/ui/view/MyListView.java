package com.lc.scan.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lc.scan.R;

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
    private Context mContext;
    private boolean isLoadingMore = false;
    private ImageView mArrow;
    private ProgressBar mPbHeader;
    private RotateAnimation mUpAnimation;
    private RotateAnimation mDownAnimation;

    private int currentState = 0;
    private final static int PULL_REFRESH = 0;
    private final static int RELEASE_REFRESH = 1;
    private final static int REFRESHING = 2;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public void setRefreshListener(RefreshListener listener){
        mRefreshListener = listener;
    }

    private void initView() {
        setOnScrollListener(this);
        initHeaderView();
        initFooterView();
        initRotateAnimation();
    }

    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.fragment_header, null);
        mTvHeader = headerView.findViewById(R.id.tv_header);
        mArrow = headerView.findViewById(R.id.iv_arraw);
        mPbHeader = headerView.findViewById(R.id.pb_header);
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

    private void initRotateAnimation(){
        mUpAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mUpAnimation.setDuration(300);
        mUpAnimation.setFillAfter(true);

        mDownAnimation = new RotateAnimation(-180, -360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mDownAnimation.setDuration(300);
        mDownAnimation.setFillAfter(true);
    }

    public void completeRefresh(){
        if(isLoadingMore){
            footerView.setPadding(0, -footerView.getMeasuredHeight(), 0, 0);
            isLoadingMore = false;
        }else {
            headerView.setPadding(0, -headerHight, 0, 0);
            mArrow.setVisibility(View.VISIBLE);
            mPbHeader.setVisibility(View.GONE);
            currentState = PULL_REFRESH;
            mTvHeader.setText("下拉刷新");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = (int)(ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if(currentState == REFRESHING){
                    break;
                }
                int deltaY = (int)(ev.getY() - downY);
                int padding = -headerHight + deltaY;
                if(padding > -headerHight && getFirstVisiblePosition() == 0) {
                    mPbHeader.setVisibility(View.GONE);
                    mArrow.setVisibility(View.VISIBLE);
                    if(padding >= headerHight){
                        padding = headerHight;
                    }
                    headerView.setPadding(0, padding, 0, 0);
                    if(padding < headerHight/2 && currentState == RELEASE_REFRESH){
                        mTvHeader.setText("下拉刷新");
                        mArrow.startAnimation(mDownAnimation);
                        currentState = PULL_REFRESH;
                    }else if(padding > headerHight/2 && currentState == PULL_REFRESH){
                        mTvHeader.setText("松开刷新");
                        mArrow.startAnimation(mUpAnimation);
                        currentState = RELEASE_REFRESH;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mRefreshListener != null && getFirstVisiblePosition() == 0) {
                    if(currentState == RELEASE_REFRESH){
                        currentState = REFRESHING;
                        mRefreshListener.onPullRefresh();
                        mTvHeader.setText("正在刷新");
                        mPbHeader.setVisibility(View.VISIBLE);
                        mArrow.clearAnimation();
                        mArrow.setVisibility(View.GONE);
                    }else if(currentState == PULL_REFRESH){
                        completeRefresh();
                    }
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
