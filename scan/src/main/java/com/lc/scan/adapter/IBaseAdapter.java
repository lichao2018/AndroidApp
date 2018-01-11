package com.lc.scan.adapter;

/**
 * Created by lichao on 2018/1/11.
 */

public interface IBaseAdapter<T> {
    void updateDatas(T datas);

    void addDatas(T datas);

    void clearDatas();
}
