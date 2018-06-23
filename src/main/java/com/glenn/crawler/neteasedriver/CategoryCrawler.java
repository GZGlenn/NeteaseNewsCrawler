package com.glenn.crawler.neteasedriver;

import com.glenn.entity.NewsEntity;
import com.glenn.util.HttpUtil;
import com.glenn.util.WebDriverUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CategoryCrawler {

    public static int MINNUM = 1000;


    public ArrayList<NewsEntity> run(String rootUrl) {

        HashSet<String> contentUrls = getContentUrls(rootUrl);

        ArrayList<NewsEntity> result = getArrNewsInfo(contentUrls);

        return result;
    }

    HashSet<String> getContentUrls(String rootUrl) {
        HashSet<String> contentUrls = new HashSet<>();

        WebDriverUtil webDriverUtil = new WebDriverUtil();
        webDriverUtil.get(rootUrl);
        webDriverUtil.maxwindow();

        List<WebElement> elementList = webDriverUtil.findElementsByClass("na_pic");
        try {
            webDriverUtil.scrollToElement(elementList.get(elementList.size() - 1));
            webDriverUtil.click("load_more_btn");
            webDriverUtil.click("load_more_foot");
        } catch (Exception e) {
            webDriverUtil.scrollEnd();
        }

        int oldNum = 0;
        do {
            try {
                oldNum = contentUrls.size();
                List<WebElement> elements = webDriverUtil.findElementsByClass("na_pic");
                for (WebElement element : elements) {
                    contentUrls.add(element.getAttribute("href"));
                }
                webDriverUtil.scrollToElement(elementList.get(elementList.size() - 1));
                webDriverUtil.click("load_more_btn");
                webDriverUtil.click("load_more_foot");
            } catch (Exception e) {
                webDriverUtil.scrollEnd();
            }
        } while (contentUrls.size() <= MINNUM && contentUrls.size() != oldNum);

        webDriverUtil.quit();

        return contentUrls;
    }

    public ArrayList<NewsEntity> getArrNewsInfo(HashSet<String> urls) {
        ArrayList<NewsEntity> result = new ArrayList<>();
        for (String url : urls) {
            try {
                System.out.println(url);
                Thread.currentThread().sleep(3000);
                NewsEntity news = parseNewsByUrl(url);
                if (news != null && news.isCorrect()) {
                    result.add(news);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }

    public NewsEntity parseNewsByUrl(String url) throws Exception {
        NewsEntity entity = new NewsEntity();

        String response = HttpUtil.get(url, null, "gbk", 3);
        Document html = Jsoup.parse(response);

        // category
        String category = "";
        Elements elements = html.getElementsByClass("post_crumb").first().children().tagName("a");
        for (Element element : elements) {
            String str = element.text();
            if (str.contains("首页") || str.contains("正文")) continue;
            category += str + ">";
        }
        entity.setCategory(category.substring(0, category.length() - 1));

        // title
        String title = html.getElementsByClass("post_content_main").first().children().select("h1").text();
        entity.setTitle(title);

        // public_time
        String publicTime = "";
        String publicTimeText = html.getElementsByClass("post_time_source").text();
        String[] publicTimeSp = publicTimeText.split(" ");
        if (publicTimeSp.length == 1) publicTime = publicTimeSp[0];
        else if (publicTimeSp[1].length() < 8) publicTime = publicTimeSp[0];
        else publicTime = publicTimeSp[0] + " " + publicTimeSp[1].substring(0, 8);
        entity.setPublicTime(publicTime);

        // content
        String content = html.getElementById("endText").text();
        entity.setContent(content);

        // img
        int imgNum = 0;
        String commonImUrls = html.getElementById("endText").select("div.ep-source").select(".cDGray").select("img").first().attr("src");
        String imgUrls = "";
        Elements imgElements = html.getElementById("endText").select("img");
        for (Element imgElement : imgElements) {
            String tmp = imgElement.attr("src");
            if (!commonImUrls.equals(tmp) && !tmp.isEmpty()) {
                imgUrls += tmp + ";";
                imgNum++;
                if (imgNum >= 3) break;
            }
        }
        entity.setImgs(imgUrls.substring(0, imgUrls.length() - 1));

        // url
        entity.setUrl(url);

        return entity;
    }

    public static void main(String[] args) {
        String root = "http://news.163.com/";
//
        CategoryCrawler crawler = new CategoryCrawler();
//        crawler.run(root);

        try {
            crawler.parseNewsByUrl("http://mobile.163.com/18/0618/10/DKIVK1490011819H.html");
        } catch (Exception e) {

        }
    }


}
