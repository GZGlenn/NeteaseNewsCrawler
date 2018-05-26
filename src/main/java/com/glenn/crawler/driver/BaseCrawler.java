package com.glenn.crawler.driver;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;

public abstract class BaseCrawler {

    public void crawlData(String mainURL, String resultRoot) {
        crawlData(mainURL, resultRoot, "utf-8");
    }

    public void crawlData(String mainURL, String resultRoot, String charset) {


        ArrayList<String> categoryWebUrls = getCategoryFromIndexWeb(mainURL, charset);

        for (String url : categoryWebUrls) {
//            getSubCategoryInfo(url, charset);
        }

        HashMap<String, String> pageInfoMap = getPageInfo();
        HashMap<String, String> detailInfoMap = getDetailInfo();


    }


    public ArrayList<String> getCategoryFromIndexWeb(String mainURL,String charset) {
        ArrayList<String> categoryInfos = new ArrayList<>();
        HashMap<String, String> header = getHttpHeader();
        HashMap<String, String> categoryInfoMap = getCategoryInfoMap();
        return categoryInfos;
    }


    public abstract HashMap<String, String> getHttpHeader();
    public abstract HashMap<String, String> getCategoryInfoMap();


    public abstract HashMap<String, String> getPageInfo();
    public abstract HashMap<String, String> getDetailInfo();





}
