package com.glenn.crawler.entity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class NeteaseNewsResult extends BaseResult {

    private String title;
    private String content;
    private String category;
    private String url;
    private ArrayList<String> imgUrls;


    public NeteaseNewsResult() {};

    public NeteaseNewsResult(String title, String content, String category, String url, ArrayList<String> imgUrls) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.url = url;
        this.imgUrls = imgUrls;
    }

    public NeteaseNewsResult(String title, String content, String category, String url) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.url = url;
    }

    public NeteaseNewsResult(NeteaseNewsResult result) {
        this.category = result.category;
        this.content = result.content;
        this.title = result.title;
        this.url = result.url;
        this.imgUrls = (ArrayList<String>) result.imgUrls.clone();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    @Override
    public void parse(String str) {
        Gson gson = new Gson();
        NeteaseNewsResult result = gson.fromJson(str, NeteaseNewsResult.class);

    }
}
