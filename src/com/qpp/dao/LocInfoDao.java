package com.qpp.dao;

import com.qpp.model.TLocinfo;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2014/7/29.
 */
public class LocInfoDao extends BaseDao<TLocinfo> {
    public LocInfoDao(){
        super(TLocinfo.class);
    }
}
