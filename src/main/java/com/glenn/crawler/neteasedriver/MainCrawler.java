package com.glenn.crawler.neteasedriver;

import com.glenn.action.NewsActionImpl;
import com.glenn.crawler.basedriver.RawCrawler;
import com.glenn.entity.NewsEntity;
import com.glenn.service.NewsServiceImpl;
import com.glenn.util.DateUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MainCrawler extends RawCrawler {

    public final static String rootUrl = "http://news.163.com/";

    @Override
    public void crawl(String url) {
        crawl(url, "gbk");
    }

    @Override
    public void crawl(String url, String charset) {
        int addTime = Integer.valueOf(DateUtil.today().replaceAll("-", ""));

        Document mainHtml = getDocumentFromUrl(url, charset);
        HashMap<String, String> categoryInfoMap;
        try {
            categoryInfoMap = parseCategoryDocumentInfo(mainHtml);
        } catch (Exception ex) {
            categoryInfoMap = getCategoryDocumentInfo();
        }

        for (HashMap.Entry<String, String> categoryEntry : categoryInfoMap.entrySet()) {
            try {
                System.out.println(categoryEntry.getKey() + " ==> " + categoryEntry.getValue());
                CategoryCrawler categoryCrawler = new CategoryCrawler();
                ArrayList<NewsEntity> newsList = categoryCrawler.run(categoryEntry.getValue());
                saveResult(newsList, addTime);
            } catch (Exception e) {
                System.out.println("error => " + e.getMessage());
            }
        }
    }

    public HashMap<String, String> getCategoryDocumentInfo() {
        HashMap<String, String> result = new HashMap<>();
        result.put("新闻", "http://news.163.com");
        result.put("体育", "http://sports.163.com");
        result.put("NBA", "http://sports.163.com/nba/");
        result.put("娱乐", "http://ent.163.com");
        result.put("财经", "http://money.163.com");
        result.put("股票", "http://money.163.com/stock/");
        result.put("汽车", "http://auto.163.com");
        result.put("科技", "http://tech.163.com");
        result.put("手机", "http://mobile.163.com");
        result.put("数码", "http://digi.163.com");
        result.put("女人", "http://lady.163.com");
        result.put("旅游", "http://travel.163.com");
        result.put("房产", "http://house.163.com");
        result.put("家居", "http://home.163.com");
        result.put("教育", "http://edu.163.com");
        result.put("读书", "http://book.163.com");
        result.put("健康", "http://jiankang.163.com");
        result.put("彩票", "http://caipiao.163.com/#from=dh");
        result.put("车险", "http://baoxian.163.com/?from=dh");
        result.put("海淘", "http://rd.da.netease.com/redirect?t=5802fb18cf033c80&amp;p=e17af55c&amp;proId=1024&amp;target=https%3A%2F%2Fwww.kaola.com%2F%3Ftag%3Dbe3d8d027a530881037ef01d304eb505");
        result.put("理财", "https://www.lmlc.com/web/activity/bind_index.html?from=tgn163dh2");
        result.put("艺术", "http://art.163.com");
        return result;
    }


    public HashMap<String, String> parseCategoryDocumentInfo(Document html) {
        HashMap<String, String> result = new HashMap<>();
        Elements categoryInfos = null;
        int tryNum = 3;

        do {
            try {
                categoryInfos = html.select("div.N-nav-channel").select(".JS_NTES_LOG_FE").first().children().tagName("a");
                Thread.currentThread().sleep(1000);
            } catch (Exception e) {
                tryNum++;
            }
        } while (categoryInfos == null && tryNum < 3);


        for (Element categoryInfo : categoryInfos) {
            String url = categoryInfo.attr("href");
            String name = categoryInfo.text();
            if (name.contains("图集") || name.contains("全") ||
                    name.contains("视频") || name.contains("直播") || name.contains("本地")) {
                continue;
            }
            result.put(name, url);
        }
        return result;
    }

    @Override
    public HashMap<String, String> getHttpHeader() {
        return null;
    }

    public void saveResult(ArrayList<NewsEntity> result, int addTime) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"./applicationContext.xml"});
        BeanFactory factory = (BeanFactory) context;
        NewsServiceImpl newsService = (NewsServiceImpl) factory.getBean("newsService");

        for (NewsEntity news : result) {
            news.setDs(addTime);
            newsService.addOrUpdate(news);
        }
    }


    public static void main(String[] args) {
        MainCrawler crawler = new MainCrawler();
        crawler.crawl(MainCrawler.rootUrl);
    }

}

