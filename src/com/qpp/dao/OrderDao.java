package com.qpp.dao;

import com.qpp.model.TOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/14/2014.
 */
@Repository
public class OrderDao extends BaseDao<TOrder> {
    public OrderDao() {
        super(TOrder.class);
    }

}
