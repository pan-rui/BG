package com.qpp.dao;

import com.qpp.model.TStateInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class StateInfoDao extends BaseDao<TStateInfo> {
    public StateInfoDao() {
        super(TStateInfo.class);
    }
    public void save(TStateInfo stateInfo) {
        super.save(stateInfo);
    }
    public TStateInfo getById(String id){
        return super.getById(id,TStateInfo.class);
    }
    public void update(TStateInfo stateInfo){
        super.update(stateInfo);
    }
    public TStateInfo getBySql(String sql){
        return super.getBySQL(sql);
    }
    public void update(String tableName, Map newData, String cond){
        try {
            super.update(tableName,newData,cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int delete(String property, Object value) {
        return super.delete(property, value);
    }
}
