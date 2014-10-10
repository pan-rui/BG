package com.qpp.dao;

import com.qpp.model.TOpenidInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class OpenidInfoDao extends BaseDao<TOpenidInfo> {
    public OpenidInfoDao() {
        super(TOpenidInfo.class);
    }
}
