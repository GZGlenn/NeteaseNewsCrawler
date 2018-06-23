package com.glenn.service;

import com.glenn.entity.NewsEntity;
import com.glenn.entity.NewsEntityPK;

public interface NewsService {

    public boolean delete(NewsEntity news);

    public boolean delete(int ds);

    public boolean addOrUpdate(NewsEntity news);

    public boolean isExists(NewsEntity news);

    public boolean isExists(NewsEntityPK pk);

    public long getNumByDs(int ds);
}
