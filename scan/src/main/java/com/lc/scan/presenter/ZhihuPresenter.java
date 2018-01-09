package com.lc.scan.presenter;

import com.google.gson.Gson;
import com.lc.scan.BasePresenter;
import com.lc.scan.BaseView;
import com.lc.scan.bean.ZhihuNews;
import com.lc.scan.bean.ZhihuStory;
import com.lc.scan.callback.HttpCallback;
import com.lc.scan.net.OkHttpApi;

import java.util.List;

/**
 * Created by lichao on 2018/1/9.
 */

public class ZhihuPresenter implements BasePresenter{
    private BaseView mView;
    private ZhihuNews mZhihuNews;
    private int latestId;

    public ZhihuPresenter(BaseView view){
        mView = view;
    }

    @Override
    public void loadData() {
        OkHttpApi.getStringFromServer("http://news-at.zhihu.com/api/4/news/latest", new HttpCallback() {
            @Override
            public void onResult(String result) {
                Gson gson = new Gson();
                mZhihuNews = gson.fromJson(result, ZhihuNews.class);
                latestId = mZhihuNews.getStories().get(1).getId();
                mView.showDatas(mZhihuNews.getStories().subList(1, mZhihuNews.getStories().size()-1));
            }

            @Override
            public void onFailure(Exception e) {
                mView.showLoadErr();
            }
        });
    }

    @Override
    public void loadMore() {
        OkHttpApi.getStringFromServer("http://news-at.zhihu.com/api/4/news/before/" + mZhihuNews.getDate(), new HttpCallback() {
            @Override
            public void onResult(String result) {
                Gson gson = new Gson();
                mZhihuNews = gson.fromJson(result, ZhihuNews.class);
                mView.showMore(mZhihuNews.getStories());
            }

            @Override
            public void onFailure(Exception e) {
                mView.showMoreErr();
            }
        });
    }

    @Override
    public void updateData() {
        OkHttpApi.getStringFromServer("http://news-at.zhihu.com/api/4/news/latest", new HttpCallback() {
            @Override
            public void onResult(String result) {
                Gson gson = new Gson();
                ZhihuNews zhihuNews = gson.fromJson(result, ZhihuNews.class);
                List<ZhihuStory> zhihuStories = zhihuNews.getStories();
                for(int i = 0; i < zhihuStories.size(); i ++){
                    if(zhihuStories.get(i).getId() == latestId){
                        if(i != 0) {
                            latestId = zhihuStories.get(0).getId();
                            mView.refreshData(zhihuStories.subList(0, i - 1));
                        }else{
                            mView.refreshData(null);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                mView.showMoreErr();
            }
        });
    }
}
