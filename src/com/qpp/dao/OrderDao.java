package com.qpp.dao;

import com.qpp.model.TOrder;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/14/2014.
 */
@Repository
public class OrderDao extends BaseDao<TOrder> {
    public OrderDao() {
        super(TOrder.class);
    }

    public void save(TOrder TOrder) {
        super.save(TOrder);
    }
    public TOrder getById(String id){
       return super.getById(id,TOrder.class);
    }
    public void update(TOrder TOrder){
        super.update(TOrder);
    }
    public TOrder getBySql(String sql){
        return super.getBySQL(sql);
    }
    public void update(String tableName, Map newData, String cond){
        try {
            super.update(tableName,newData,cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean insert(TOrder TOrder){
        return super.insert(TOrder);
    }

    public int delete(String property, Object value) {
        return super.delete(property, value);
    }

}
