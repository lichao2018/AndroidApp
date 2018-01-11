package com.lc.scan.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.lc.scan.bean.BaseGankResponse;
import com.lc.scan.bean.Gank;
import com.lc.scan.callback.HttpCallback;
import com.lc.scan.net.OkHttpApi;
import com.lc.scan.ui.IBaseView;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lichao on 2018/1/10.
 */

public class MeituPresenter implements IBasePresenter {

    private IBaseView mView;
    private int pageCount = 1;
    private Context mContext;

    public MeituPresenter(Context context, IBaseView view){
        mContext = context;
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
                for(int i = 0; i < gankList.size(); i ++){
                    Drawable drawable = null;
                    try {
                        drawable = Glide.with(mContext).load(gankList.get(i).getUrl()).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                        gankList.get(i).setGirlWidth(drawable.getIntrinsicWidth()/3);
                        gankList.get(i).setGirlHeight(drawable.getIntrinsicHeight()/3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

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
