package com.qpp.dao;

import com.qpp.model.TStore;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class StoreDao extends BaseDao<TStore> {
    public StoreDao() {
        super(TStore.class);
    }
}
