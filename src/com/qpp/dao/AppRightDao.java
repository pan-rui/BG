package com.qpp.dao;

import com.qpp.model.TAppRight;
import com.qpp.model.VAppApi;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by admin on 2014/7/29.
 */
@Repository
public class AppRightDao extends BaseDao<VAppApi> {
    public AppRightDao(){
        super(VAppApi.class);
    }
    public boolean getAppRight(long appId,String url){
       String sql=" from VAppApi where ((type=0 and oid={0})" +
                  "or (type=1 and oid in(select levelid from AppRole where appid={0})))" +
                  " and INSTR(apiurl,''{1}'')=1";
       sql= MessageFormat.format(sql,appId,url);
       List<VAppApi> list=getsByQuery(sql);
       if (list.size()>0)
           return true;
       else
           return false;
    }
}
