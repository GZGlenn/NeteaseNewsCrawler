package com.glenn.dao;

import com.glenn.entity.NewsEntity;
import com.glenn.entity.NewsEntityPK;

import java.util.List;

public interface NewsDao {

    public void addOrUpdate(NewsEntity news);

    public void delete(NewsEntity news);

    public void delete(int ds);

    public NewsEntity getNews(NewsEntityPK pk);

    public List<NewsEntity> getNewsList(int ds);
}
