package com.glenn.dao;

import com.glenn.entity.NewsEntity;
import com.glenn.entity.NewsEntityPK;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
//出现Exception异常回滚
@Repository("newsDao") //进行注入
public class NewsDaoImpl implements NewsDao {
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public void addOrUpdate(NewsEntity news) {
        sessionFactory.getCurrentSession().saveOrUpdate(news);
    }

    @Override
    public void delete(NewsEntity news) {
        sessionFactory.getCurrentSession().delete(news);
    }

    @Override
    public void delete(int ds) {
        String hsql = "DELETE FROM NewsEntity n where n.ds=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        query.setInteger(0, ds);
        query.executeUpdate();
    }

    @Override
    public NewsEntity getNews(NewsEntityPK pk) {
        String hsql = "FROM NewsEntity n where n.url=? and n.publicTime=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        query.setString(0, pk.getUrl());
        query.setString(1, pk.getPublicTime());
        return (NewsEntity) query.uniqueResult();
    }

    @Override
    public List<NewsEntity> getNewsList(int ds) {
        String hsql = "FROM NewsEntity n where n.ds=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hsql);
        query.setInteger(0, ds);
        return query.list();
    }
}
