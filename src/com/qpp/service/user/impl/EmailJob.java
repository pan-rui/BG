package com.qpp.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.qpp.model.TMailPublish;
import com.qpp.util.Email;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
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
        List<String> ccs = email.getCc() != null ? JSON.parseArray(email.getCc(), String.class) : new ArrayList<String>();
        String[] address = new String[addrs.size()];
        String[] ccss = new String[ccs.size()];
        if(email.getAttachment()!=null&&!"".equals(email.getAttachment()))
            mailUtil.sendMail(email.getSubject(),email.getContent(), addrs.toArray(address), ccs.toArray(ccss),null,email.getAttachment());
        else
            mailUtil.sendMail(email.getSubject(),email.getContent(), addrs.toArray(address), ccs.toArray(ccss));
        System.out.println("send success.....");
    }
}
