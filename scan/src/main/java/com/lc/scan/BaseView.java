package com.lc.scan;

/**
 * Created by lichao on 2018/1/9.
 */

public interface BaseView<T> {
    void showDatas(T datas);

    void showLoadErr();

    void showMore(T datas);

    void showMoreErr();

    void refreshData(T datas);
}
