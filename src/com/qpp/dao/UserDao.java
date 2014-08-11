package com.qpp.dao;

import com.qpp.model.TUser;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class UserDao extends BaseDao<TUser> {
    public UserDao() {
        super(TUser.class);
    }
    public void save(TUser user) {
        super.save(user);
    }
    public TUser getById(String id){
        return super.getById(id,TUser.class);
    }
    public void update(TUser user){
        super.update(user);
    }
    public TUser getBySql(String sql){
        return super.getBySQL(sql);
    }
    public void update(String tableName, Map newData, String cond){
        try {
            super.update(tableName,newData,cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean insert(TUser user){
        return super.insert(user);
    }

    public int delete(String property, Object value) {
        return super.delete(property, value);
    }
    public void delete(TUser user){
        super.delete(user);
    }
}
