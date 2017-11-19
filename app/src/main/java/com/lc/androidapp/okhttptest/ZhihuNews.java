package com.lc.androidapp.okhttptest;

import java.util.List;

/**
 * Created by lichao on 2017/11/19.
 */

public class ZhihuNews {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuStory> getStories() {
        return stories;
    }

    public void setStories(List<ZhihuStory> stories) {
        this.stories = stories;
    }

    public List<ZhihuStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<ZhihuStory> top_stories) {
        this.top_stories = top_stories;
    }

    private String date;
    private List<ZhihuStory> stories;
    private List<ZhihuStory> top_stories;
}
