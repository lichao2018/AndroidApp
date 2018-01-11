package com.lc.scan.ui;

/**
 * Created by lichao on 2018/1/9.
 */

public interface IBaseView<T> {
    void showDatas(T datas);

    void showLoadErr();

    void showMore(T datas);

    void showMoreErr();

    void refreshData(T datas);

    void showLoading();

    void hideLoading();
}
