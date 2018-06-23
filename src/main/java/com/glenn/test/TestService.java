package com.glenn.test;

import com.glenn.entity.NewsEntity;
import com.glenn.service.NewsServiceImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestService {

    public static void main(String[] args) {
        TestService testService = new TestService();
        testService.testAddOneAndRemove();
        testService.testDeleteAll();
    }

    public void testAddOneAndRemove() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"./applicationContext.xml"});
        BeanFactory factory = (BeanFactory) context;

        NewsServiceImpl newsService = (NewsServiceImpl) factory.getBean("newsService");

        NewsEntity news = new NewsEntity();
        news.setContent("content");
        news.setImgs("");
        news.setUrl("http://www.baidu.com");
        news.setCategory("category");
        news.setPublicTime("20180601");
        news.setTitle("title");
        news.setDs(20180601);

        System.out.println(news);

        newsService.addOrUpdate(news);

        System.out.println(newsService.isExists(news));


        NewsEntity news1 = new NewsEntity();
        news1.setContent("content_1");
        news1.setImgs("");
        news1.setUrl("http://www.baidu.com");
        news1.setCategory("category");
        news1.setPublicTime("20180601");
        news1.setTitle("title");
        news1.setDs(20180601);

        newsService.addOrUpdate(news1);

        newsService.delete(news1);


        System.out.println(newsService.isExists(news1));
    }

    public void testDeleteAll() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"applicationContext.xml"});
        BeanFactory factory = (BeanFactory) context;

        NewsServiceImpl newsService = (NewsServiceImpl) factory.getBean("newsService");

        for (int i = 0 ; i < 10 ; i++) {
            NewsEntity news = new NewsEntity();
            news.setContent("content_" + i);
            news.setImgs("");
            news.setUrl("http://www.baidu.com");
            news.setCategory("category");
            news.setPublicTime("20180601_" + i);
            news.setTitle("title");
            news.setDs(20180601);

            newsService.addOrUpdate(news);
        }

        System.out.println(newsService.getNumByDs(20180601));

        newsService.delete(20180601);

        System.out.println(newsService.getNumByDs(20180601));
    }

}
