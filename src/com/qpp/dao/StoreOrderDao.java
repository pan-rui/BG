package com.qpp.dao;

import com.qpp.model.TStoreOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class StoreOrderDao extends BaseDao<TStoreOrder> {
    public StoreOrderDao() {
        super(TStoreOrder.class);
    }

}
