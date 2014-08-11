package com.qpp.service.market;

import com.qpp.model.TOrder;

/**
 * Created by qpp on 7/14/2014.
 */

public interface OrderService {
    boolean save(TOrder TOrder);
    TOrder getById(String id);
    boolean update(TOrder TOrder);
    TOrder getBySql(String sql);
//    boolean updateOnMap(String tableName,Map<String,Object> newData,String cond);
}
