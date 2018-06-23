package com.glenn.crawler.basedriver;

import com.glenn.util.HttpUtil;
import com.glenn.util.LogUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public abstract class RawCrawler {

    public abstract void crawl(String url);

    public abstract void crawl(String url, String charset);


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

    public abstract HashMap<String, String> getHttpHeader();

}
