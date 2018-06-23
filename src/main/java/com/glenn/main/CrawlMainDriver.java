package com.glenn.main;

import com.glenn.action.NewsActionImpl;
import com.glenn.crawler.neteasedriver.MainCrawler;

public class CrawlMainDriver {

    public static void main(String[] args) {

        MainCrawler crawler = new MainCrawler();
        crawler.crawl(MainCrawler.rootUrl);

        NewsActionImpl actionImpl = new NewsActionImpl();
        actionImpl.deleteDueNews();
        if (actionImpl.isNeedDelete()) actionImpl.deleteBatchNews();
    }
}
