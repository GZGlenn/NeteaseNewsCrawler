package com.glenn.test;

import com.glenn.action.NewsActionImpl;
import com.glenn.entity.NewsEntity;
import com.glenn.service.NewsServiceImpl;
import com.glenn.util.DateUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;

public class TestAction {

    public static void main(String[] args) {
        prepareData();
        testAction();
    }

    public static void prepareData() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"applicationContext.xml"});
        BeanFactory factory = (BeanFactory) context;

        NewsServiceImpl newsService = (NewsServiceImpl) factory.getBean("newsService");

        Date todayDate = DateUtil.parse(DateUtil.today());

        Date startDate = DateUtil.offsiteDate(todayDate, Calendar.MONTH, -4);
        Date endDate = DateUtil.offsiteDate(todayDate, Calendar.MONTH, -2);

        Date curDate = startDate;
        while (curDate.compareTo(endDate) <=0) {
            NewsEntity news = new NewsEntity();
            news.setContent("content");
            news.setImgs("");
            news.setUrl("http://www.baidu.com");
            news.setCategory("category");
            news.setPublicTime(DateUtil.formatDate(curDate));
            news.setTitle("title");
            news.setDs(Integer.valueOf(DateUtil.formatDate(curDate).replaceAll("-", "")));

            newsService.addOrUpdate(news);

            curDate = DateUtil.offsiteDate(curDate, Calendar.DAY_OF_MONTH, 1);
        }
    }

    public static void testAction() {
        NewsActionImpl action = new NewsActionImpl();
        action.deleteDueNews();
        if (action.isNeedDelete()) {
            action.deleteBatchNews();
        }
    }
}
