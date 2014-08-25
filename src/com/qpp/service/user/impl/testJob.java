package com.qpp.service.user.impl;

import com.danga.MemCached.MemCachedClient;
import com.qpp.dao.BaseDao;
import com.qpp.dao.CartItemDao;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.awt.*;

/**
 * Created by qpp on 8/21/2014.
 */
public class testJob extends QuartzJobBean {
    private BaseDao dao;
    private Object obj;
    private String userId;
@Resource
protected MemCachedClient memcachedClient;

    public BaseDao getDao() {
        return dao;
    }

    public void setDao(BaseDao dao) {
        this.dao = dao;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void run(){
        System.out.println(dao instanceof CartItemDao);
        System.out.println(obj instanceof List);
        System.out.println(((List)obj).size());
        System.out.println(userId);
        dao.save(obj);
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
