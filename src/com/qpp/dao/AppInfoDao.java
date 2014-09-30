package com.qpp.dao;

import com.qpp.model.AppKey;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2014/7/29.
 */
@Repository
public class AppInfoDao extends BaseDao<AppKey> {
    public AppInfoDao(){
        super(AppKey.class);
    }

    public List<AppKey> getUserApp(long userId){
        return super.getsByQuery("from AppKey where userId="+userId);
    }

    public String insertApp(String appId){
        return super.getObjectBySQL("select p_newApp('"+appId+"')").toString();
    }
 /*   public String insertApp(final String appId) {
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
    }*/
}
