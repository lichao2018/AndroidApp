package com.lc.scan.bean;

import java.util.List;

/**
 * Created by lichao on 2017/12/19.
 */

public class BaseGankResponse {
    boolean error;
    List<Gank> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Gank> getResults() {
        return results;
    }

    public void setResults(List<Gank> results) {
        this.results = results;
    }
}
