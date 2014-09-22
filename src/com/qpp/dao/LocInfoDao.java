package com.qpp.dao;

import com.qpp.model.TLocationType;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2014/7/29.
 */
@Repository
public class LocInfoDao extends BaseDao<TLocationType> {
    public LocInfoDao(){
        super(TLocationType.class);
    }
}
