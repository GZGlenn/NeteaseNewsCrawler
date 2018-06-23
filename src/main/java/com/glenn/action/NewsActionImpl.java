package com.glenn.action;

import com.glenn.service.NewsServiceImpl;
import com.glenn.util.DateUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;

public class NewsActionImpl implements NewsAction {

    public static final int DUEMONTH = 3;

    public static final long MAXNUM = 1000000;

    public NewsServiceImpl newsService;

    public NewsActionImpl() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"./applicationContext.xml"});
        BeanFactory factory = (BeanFactory) context;
        newsService = (NewsServiceImpl) factory.getBean("newsService");
    }

    @Override
    public boolean isNeedDelete() {
        long totalNum = 0;
        Date calDate = getDueDate();
        while (totalNum < MAXNUM && calDate.compareTo(DateUtil.parseDate(DateUtil.today())) <= 0) {
            int dateInt = Integer.valueOf(DateUtil.formatDate(calDate).replaceAll("-", ""));
            totalNum += newsService.getNumByDs(dateInt);
            calDate = DateUtil.offsiteDate(calDate, Calendar.DAY_OF_MONTH, 1);
        }

        if (totalNum >= MAXNUM) return true;
        else return false;
    }

    @Override
    public void deleteDueNews() {
        Date dueThre = getDueDate();
        Date curDelDate = DateUtil.offsiteDate(dueThre, Calendar.MONTH, -1);
        while(curDelDate.compareTo(dueThre) < 0) {
            int dateInt = Integer.valueOf(DateUtil.formatDate(curDelDate).replaceAll("-", ""));
            newsService.delete(dateInt);
            curDelDate = DateUtil.offsiteDate(curDelDate, Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public void deleteBatchNews() {
        Date delDate = getDueDate();
        int dateInt = Integer.valueOf(DateUtil.formatDate(delDate).replaceAll("-", ""));
        newsService.delete(dateInt);
    }

    private Date getDueDate() {
        String todayStr = DateUtil.today();
        Date dueDate = DateUtil.offsiteDate(DateUtil.parseDate(todayStr), Calendar.MONTH, -DUEMONTH);
        return dueDate;
    }
}
