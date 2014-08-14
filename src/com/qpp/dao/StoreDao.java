package com.qpp.dao;

import com.qpp.model.TStore;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class StoreDao extends BaseDao<TStore> {
    public StoreDao() {
        super(TStore.class);
    }
    public void save(TStore store) {
        super.save(store);
    }
    public TStore getById(String id){
        return super.getById(id,TStore.class);
    }
    public void update(TStore store){
        super.update(store);
    }
    public TStore getBySql(String sql){
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
