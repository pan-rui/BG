package com.qpp.dao;

import com.qpp.model.TCartItem;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class CartItemDao extends BaseDao<TCartItem> {
    public CartItemDao() {
        super(TCartItem.class);
    }
    public void save(TCartItem cartItem) {
        super.save(cartItem);
    }
    public TCartItem getById(String id){
        return super.getById(id,TCartItem.class);
    }
    public void update(TCartItem cartItem){
        super.update(cartItem);
    }
    public TCartItem getBySql(String sql){
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
