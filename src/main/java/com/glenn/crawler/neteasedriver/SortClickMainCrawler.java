package com.glenn.crawler.neteasedriver;

import com.glenn.crawler.basedriver.RawCrawler;
import com.glenn.crawler.entity.NeteaseNewsResult;
import com.glenn.util.DateUtil;
import com.glenn.util.FileUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SortClickMainCrawler extends RawCrawler {

    @Override
    public void crawl(String url, String saveRoot) {
        crawl(url, saveRoot, "utf-8");
    }

    @Override
    public void crawl(String url, String saveRoot, String charset) {
        Document mainHtml = getDocumentFromUrl(url, charset);
        HashMap<String, String> categoryInfoMap = parseCategoryDocumentInfo(mainHtml);
        for (HashMap.Entry<String, String> categoryEntry : categoryInfoMap.entrySet()) {
            HashSet<String> categoryContentUrls = getCategoryContent(categoryEntry, charset, 1000);
            saveResult(categoryContentUrls, charset, saveRoot);
        }
    }


    public HashMap<String, String> parseCategoryDocumentInfo(Document html) {
        HashMap<String, String> result = new HashMap<>();

        Elements subNaves = html.getElementsByClass("subNav");
        for (Element subNav : subNaves) {
            Elements hreves = subNav.children().select("a");
            for (Element href : hreves) {
                String url = href.attr("href");
                String name = href.text();
                if (name.contains("图集") || name.contains("新闻") || name.contains("全") || name.contains("视频")) {
                    continue;
                }

                result.put(name, url);
            }
        }

        return result;
    }

    @Override
    public HashMap<String, String> getHttpHeader() {
        return null;
    }

    public HashSet<String> getCategoryContent(HashMap.Entry<String, String> entry, String charset, int num) {
        HashSet<String> result = new HashSet<>();
        Document categoryMainHtml = getDocumentFromUrl(entry.getValue(), charset);

        int artitleNum = categoryMainHtml.getElementsByClass("ndi_main").first().children().select("data_row news_article clearfix ").size();
        while (artitleNum < num) {

            artitleNum = categoryMainHtml.getElementsByClass("ndi_main").first().children().select("data_row news_article clearfix ").size();
        }

        Elements articlesElements = categoryMainHtml.getElementsByClass("ndi_main").first().children().select("data_row news_article clearfix ");
        for (Element element : articlesElements) {
            result.add(element.children().select("a").first().attr("href"));
        }

        return result;
    }

    public ArrayList<String> getContentUrls(Document html, String charset) {
        ArrayList<String> result = new ArrayList<>();


        return result;
    }

    public void saveResult(HashSet<String> urls, String charset, String saveRoot) {
        for (String url : urls) {
            Document html = getDocumentFromUrl(url, charset);
            Elements classElements = html.getElementsByClass("post_crumb");
            if (classElements.isEmpty()) continue;
            classElements = classElements.first().children().select("a");
            if (classElements.size() < 3) continue;
            String className = classElements.get(1).text();

            Elements titleElements = html.getElementsByClass("post_content_main");
            if (titleElements.isEmpty()) continue;
            titleElements = titleElements.first().children().select("h1");
            if (titleElements.isEmpty()) continue;
            String title = titleElements.first().text();

            Elements contentElements = html.getElementsByClass("post_text");
            if (contentElements.isEmpty()) continue;
            contentElements = contentElements.first().children().select("p");
            String content = "";
            for (Element contentElement : contentElements) {
                content += contentElement.text() + "\n";
            }

            NeteaseNewsResult result = new NeteaseNewsResult();
            result.setCategory(className);
            result.setTitle(title);
            result.setContent(content);
            result.setUrl(url);

            saveResult(result, saveRoot);
        }
    }

    public void saveResult(NeteaseNewsResult result, String saveRoot) {
        String subRoot = saveRoot + "/" + result.getUrl();
        FileUtil.createIfNotExist(subRoot);
        String file = subRoot + "/" + DateUtil.now().split(" ")[0] + ".txt";
        FileUtil.appendFile(file, result.toString());
    }

}

