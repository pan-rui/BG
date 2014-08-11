package com.qpp.dao;

import com.qpp.model.TAppInfo;
import com.qpp.model.TShippinginfo;

/**
 * Created by admin on 2014/7/29.
 */
public class ShippingInfoDao extends BaseDao<TShippinginfo> {
    public ShippingInfoDao(){
        super(TShippinginfo.class);
    }
}
