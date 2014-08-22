package com.qpp.dao;

import com.qpp.model.TOrderItem;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/14/2014.
 */
@Repository
public class OrderItemDao extends BaseDao<TOrderItem> {
    public OrderItemDao() {
        super(TOrderItem.class);
    }
}
