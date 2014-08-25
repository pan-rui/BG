package com.qpp.service.user.impl;

import com.danga.MemCached.MemCachedClient;
import com.qpp.dao.BaseDao;
import com.qpp.dao.CartItemDao;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by qpp on 8/21/2014.
 */
public class CartJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap=jobExecutionContext.getMergedJobDataMap();
        BaseDao dao = (BaseDao) dataMap.get("dao");
        MemCachedClient mem= (MemCachedClient) dataMap.get("mem");
        String key = (String) dataMap.get("key");
        Object obj=mem.get(key);
        System.out.println("Spring映射属性 dao \t"+(dao instanceof CartItemDao));
        System.out.println("Spring映射属性 memCachedClient \t"+(mem instanceof MemCachedClient));
        System.out.println("要存储的对象个数:"+((ArrayList)obj).size());
        if(obj instanceof List){
            for(Object ob:(ArrayList)obj)
                dao.save(obj);
        }else
            dao.save(obj);
//        System.err.println(userId);
    }
}
