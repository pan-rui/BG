package com.qpp.test.temp;

import com.alibaba.fastjson.JSON;
import com.qpp.dao.CartItemDao;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by qpp on 8/20/2014.
 */
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String instName=jobExecutionContext.getJobDetail().getKey().getName();
        String instGroup=jobExecutionContext.getJobDetail().getKey().getGroup();
//        JobDataMap dataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        JobDataMap dataMap=jobExecutionContext.getMergedJobDataMap();
        List<Map> list = (List<Map>) dataMap.get("cart");
        System.out.println("isDao\t"+(dataMap.get("dao") instanceof CartItemDao));
        System.out.println(list.get(0));
        try {
            FileWriter fw = new FileWriter(new File("d:/abccc.txt"), true);
            fw.append(JSON.toJSONString(list));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("instName: "+instName+"\tInstGroup: "+instGroup);
    }
}
