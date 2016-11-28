package com.lc.androidapp.bean;

/**
 * Created by lichao on 2016/11/28.
 */
public class ZhiHuDaily {
    public Stories[] getStories() {
        return stories;
    }

    public void setStories(Stories[] stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Top_Stories[] getTop_Stories() {
        return top_Stories;
    }

    public void setTop_Stories(Top_Stories[] topStories) {
        this.top_Stories = topStories;
    }

    private String date;
    private Top_Stories[] top_Stories;
    private Stories[] stories;
}
