package com.glenn.crawler.basedriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.glenn.util.HttpUtil;
import com.glenn.util.LogUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class BaseCrawler {

    public void crawlData(String mainURL, String resultRoot) {
        crawlData(mainURL, resultRoot, "utf-8");
    }

    public void crawlData(String mainURL, String saveRoot, String charset) {


        HashMap<String, String> categoryWebMap = getCategoryFromIndexWeb(mainURL, charset);

        for (HashMap.Entry<String, String> entry : categoryWebMap.entrySet()) {
            Document html = getDocumentFromUrl(entry.getValue(), charset);
            if (html == null) continue;
            int pageNum = getPageNum(html);

            for (int pageIdx = 1; pageIdx <= pageNum; pageIdx++) {
                if (pageIdx != 1) {
                    String pageUrl = getPageUrl(html);
                    html = getDocumentFromUrl(pageUrl, charset);
                }
                if (html == null) continue;

                ArrayList<String> contentUrls = getContentUrlFromSinglePage(html);
                for (String contentUrl : contentUrls) {
                    Document contentHtml = getDocumentFromUrl(contentUrl, charset);
//                    BaseResult result = parseContentInfo(contentHtml);
//                    saveResult(result, saveRoot, entry.getKey(), pageIdx);
                }

                LogUtil.getInstance().printLog(entry.getKey() + " : " + pageIdx, LogUtil.LEVEL.INFO);
            }

            return;
        }

    }

    public HashMap<String, String> getCategoryFromIndexWeb(String mainURL, String charset) {
        Document html = getDocumentFromUrl(mainURL, charset);
        HashMap<String, String> categoryInfos = parseCategoryDocumentInfo(html);
        return categoryInfos;
    }

    public Document getDocumentFromUrl(String url, String charset) {
        Document html = null;
        try {
            HashMap<String, String> header = getHttpHeader();
            String response = HttpUtil.get(url, header, charset, 3);
            html = Jsoup.parse(response);
        } catch (IOException e) {
            LogUtil.getInstance().printLog(e.getMessage(), LogUtil.LEVEL.ERROR);
        }

        return html;
    }

    public abstract HashMap<String, String> parseCategoryDocumentInfo(Document html);

    public abstract HashMap<String, String> getHttpHeader();

    public abstract int getPageNum(Document html);

    public abstract String getPageUrl(Document html);

    public abstract ArrayList<String> getContentUrlFromSinglePage(Document html);
//
//    public abstract BaseResult parseContentInfo(Document html);
//
//    public abstract void saveResult(BaseResult result, String saveRoot, String categoryName, int pageIdx);

}
