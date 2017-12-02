package com.lc.scan.callback;

/**
 * Created by lichao on 2017/11/19.
 */

public interface HttpCallback {
    public void onResult(String result);

    public void onFailure(Exception e);
}
