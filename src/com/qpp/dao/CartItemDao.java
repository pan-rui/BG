package com.qpp.dao;

import com.qpp.model.TCartItem;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class CartItemDao extends BaseDao<TCartItem> {
    public CartItemDao() {
        super(TCartItem.class);
    }
}
