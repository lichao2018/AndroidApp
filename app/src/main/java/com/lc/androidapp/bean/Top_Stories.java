package com.lc.androidapp.bean;

/**
 * Created by lichao on 2016/11/28.
 */
public class Top_Stories {
    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;

    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setGa_prefix(String ga_prefix){
        this.ga_prefix = ga_prefix;
    }
    public String getGa_prefix(){
        return this.ga_prefix;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
}
