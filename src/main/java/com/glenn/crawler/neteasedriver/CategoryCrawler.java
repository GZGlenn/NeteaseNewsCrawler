package com.glenn.crawler.neteasedriver;

import com.glenn.util.WebDriverUtil;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CategoryCrawler {

    public static int minNum = 2000;

    public void run(String rootUrl, String saveRoot, String categoryInfo) {
        WebDriverUtil webDriverUtil = new WebDriverUtil();
        webDriverUtil.get(rootUrl);
        webDriverUtil.maxwindow();

        int curNum = webDriverUtil.findElementsByClass("data_row news_article clearfix ").size();

        while (curNum <= minNum) {
            webDriverUtil.scrollEnd();
            webDriverUtil.click("load_more_btn");
            curNum = webDriverUtil.findElementsByClass("data_row news_article clearfix ").size();
        }

        List<WebElement> webElements = webDriverUtil.findElementsByClass("data_row news_article clearfix ");

        saveCrawlElements(webElements, saveRoot, categoryInfo);
    }

    public void saveCrawlElements(List<WebElement> webElements, String saveRoot, String categoryInfo) {
        for (WebElement element : webElements) {
//            element.findElement("a").click();
        }


    }
}
