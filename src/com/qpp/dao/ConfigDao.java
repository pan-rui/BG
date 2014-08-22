package com.qpp.dao;

import com.qpp.model.TConfig;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class ConfigDao extends BaseDao<TConfig> {
    public ConfigDao() {
        super(TConfig.class);
    }
}
