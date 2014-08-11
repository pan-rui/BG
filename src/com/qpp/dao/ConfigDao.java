package com.qpp.dao;

import com.qpp.model.TConfig;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class ConfigDao extends BaseDao<TConfig> {
    public ConfigDao() {
        super(TConfig.class);
    }
    public void save(TConfig config) {
        super.save(config);
    }
    public TConfig getById(String id){
        return super.getById(id,TConfig.class);
    }
    public void update(TConfig config){
        super.update(config);
    }
    public TConfig getBySql(String sql){
        return super.getBySQL(sql);
    }
    public void update(String tableName, Map newData, String cond){
        try {
            super.update(tableName,newData,cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean insert(TConfig config){
        return super.insert(config);
    }

    public int delete(String property, Object value) {
        return super.delete(property, value);
    }
}
