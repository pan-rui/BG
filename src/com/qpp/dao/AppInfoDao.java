package com.qpp.dao;

import com.qpp.model.TAppInfo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.Cache;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

/**
 * Created by admin on 2014/7/29.
 */
public class AppInfoDao extends BaseDao<TAppInfo> {
    public AppInfoDao(){
        super(TAppInfo.class);
    }
/*
    public String insertApp(String appId){
        //return super.getObjectBySQL("select p_newApp('"+appId+"')").toString();
        return super.getObjectBySQL("exec p_newApp '"+appId+"'").toString();
    }
*/
    public String insertApp(final String appId) {
        Object appKey = getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        String obj="";
                        try {
                            Query query=session.getNamedQuery("p_newApp").setParameter(0,appId);
                            obj=query.list().get(0).toString();
                        }
                        catch(Exception e){e.printStackTrace();}
                        finally {
                            if (session != null)
                                session.close();
                        }
                        return obj;
                    }
                });
        return appKey.toString();
    }
}
