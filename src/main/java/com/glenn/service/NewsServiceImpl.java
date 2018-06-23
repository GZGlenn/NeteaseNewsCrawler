package com.glenn.service;

import com.glenn.dao.NewsDao;
import com.glenn.entity.NewsEntity;
import com.glenn.entity.NewsEntityPK;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

    //自动注入userDao，也可以使用@Autowired
    @Resource
    private NewsDao newsDao;


    @Override
    public boolean delete(NewsEntity news) {
        this.newsDao.delete(news);
        return true;
    }

    @Override
    public boolean delete(int ds) {
        this.newsDao.delete(ds);
        return true;
    }

    @Override
    public boolean addOrUpdate(NewsEntity news) {
        this.newsDao.addOrUpdate(news);
        return true;
    }

    @Override
    public boolean isExists(NewsEntity news) {
        NewsEntityPK pk = new NewsEntityPK();
        pk.setUrl(news.getUrl());
        pk.setPublicTime(news.getPublicTime());
        return this.isExists(pk);
    }

    @Override
    public boolean isExists(NewsEntityPK pk) {
        NewsEntity result = this.newsDao.getNews(pk);
        if (result == null) return false;
        else return true;
    }

    @Override
    public long getNumByDs(int ds) {
        return this.newsDao.getNewsList(ds).size();
    }
}
