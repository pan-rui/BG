package com.qpp.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.qpp.model.TMailPublish;
import com.qpp.util.Email;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by qpp on 8/22/2014.
 */
public class EmailJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap=jobExecutionContext.getMergedJobDataMap();
        Email mailUtil= (Email) dataMap.get("mailUtil");
        TMailPublish email = (TMailPublish) dataMap.get("email");
        List<String> addrs = (List<String>) dataMap.get("addrs");
        if(email.getAttachment()!=null&&!"".equals(email.getAttachment()))
            mailUtil.sendMail(email.getSubject(),email.getContent(), (String[]) addrs.toArray(), (String[])JSON.parseArray(email.getCc(),String.class).toArray());
        else
            mailUtil.sendMail(email.getSubject(),email.getContent(),(String[]) addrs.toArray(), (String[])JSON.parseArray(email.getCc(),String.class).toArray(),null,email.getAttachment());
        System.out.println("send success.....");
    }
}
