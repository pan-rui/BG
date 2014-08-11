package com.qpp.dao;

import com.qpp.model.TAppInfo;
import com.qpp.model.TAppRight;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 2014/7/29.
 */
public class AppRightDao extends BaseDao<TAppRight> {
    public AppRightDao(){
        super(TAppRight.class);
    }
    public boolean getAppRight(int role,String url){
        List<TAppRight> list=getsByQuery("from TAppRight where role="+role+" and charindex(url,'"+url+"')=1");
        if (list.size()>0)
            return true;
        else
            return false;
    }
}
