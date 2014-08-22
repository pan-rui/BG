package com.qpp.dao;

import com.qpp.model.TAppInfo;
import com.qpp.model.TShippinginfo;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2014/7/29.
 */
@Repository
public class ShippingInfoDao extends BaseDao<TShippinginfo> {
    public ShippingInfoDao(){
        super(TShippinginfo.class);
    }
}
