package com.qpp.dao;

import com.qpp.model.TStoreOrder;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class StoreOrderDao extends BaseDao<TStoreOrder> {
    public StoreOrderDao() {
        super(TStoreOrder.class);
    }
    public void save(TStoreOrder storeOrder) {
        super.save(storeOrder);
    }
    public TStoreOrder getById(String id){
        return super.getById(id,TStoreOrder.class);
    }
    public void update(TStoreOrder storeOrder){
        super.update(storeOrder);
    }
    public TStoreOrder getBySql(String sql){
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
