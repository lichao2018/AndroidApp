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

    public Stories[] getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(Stories[] topStories) {
        this.top_stories = topStories;
    }

    private String date;
    private Stories[] top_stories;
    private Stories[] stories;
}
