package com.qpp.dao;

import com.qpp.model.TOrderItem;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/14/2014.
 */
@Repository
public class OrderItemDao extends BaseDao<TOrderItem> {
    public OrderItemDao() {
        super(TOrderItem.class);
    }

    public void save(TOrderItem orderItem){
        super.save(orderItem);
    }
    public boolean  insert(TOrderItem orderItem){
        return super.insert(orderItem);
    }
    public TOrderItem getById(String id){
        return super.getById(id,TOrderItem.class);
    }
    public void update(TOrderItem comment){
        super.update(comment);
    }
    public TOrderItem getBySql(String sql){
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
