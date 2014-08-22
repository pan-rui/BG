package com.qpp.dao;

import com.qpp.model.TStateInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class StateInfoDao extends BaseDao<TStateInfo> {
    public StateInfoDao() {
        super(TStateInfo.class);
    }
}
