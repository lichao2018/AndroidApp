package com.lc.scan.presenter;

import com.google.gson.Gson;
import com.lc.scan.bean.BaseGankResponse;
import com.lc.scan.bean.Gank;
import com.lc.scan.callback.HttpCallback;
import com.lc.scan.net.OkHttpApi;
import com.lc.scan.ui.IBaseView;

import java.util.List;

/**
 * Created by lichao on 2018/1/10.
 */

public class MeituPresenter implements IBasePresenter {

    private IBaseView mView;
    private int pageCount = 1;

    public MeituPresenter(IBaseView view){
        mView = view;
    }

    @Override
    public void loadData() {
        mView.showLoading();
        OkHttpApi.getStringFromServer("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/" + pageCount, new HttpCallback() {
            @Override
            public void onResult(String result) {
                mView.hideLoading();
                Gson gson = new Gson();
                BaseGankResponse baseGankResponse = gson.fromJson(result, BaseGankResponse.class);
                List<Gank> gankList = baseGankResponse.getResults();
                if(pageCount == 1){
                    mView.refreshData(gankList);
                }else{
                    mView.showDatas(gankList);
                }
                pageCount++;
            }

            @Override
            public void onFailure(Exception e) {
                mView.hideLoading();
                mView.showLoadErr();
            }
        });
    }

    @Override
    public void loadMore() {
        loadData();
    }

    @Override
    public void updateData() {
        pageCount = 1;
        loadData();
    }
}
