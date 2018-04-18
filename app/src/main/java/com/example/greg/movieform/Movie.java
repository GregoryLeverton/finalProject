package com.example.greg.movieform;

import java.util.ArrayList;

/**
 * Created by Tran on 2018-04-12.
 */

public class Movie {

    private String title;
    private String actors;
    private String length;
    private String desc;
    private int rating;
    private String url;
    private int id;

    public Movie(){

        this.title="";
        this.actors= "";
        this.length="";
        this.desc="";
        this.rating=0;
        this.url="";
        this.id=0;

    }

    public Movie(int id, String title, String actors, String length, String desc, int rating, String url){

        this.title=title;
        this.actors= actors;
        this.length=length;
        this.desc=desc;
        this.rating=rating;
        this.url=url;
        this.id=id;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
