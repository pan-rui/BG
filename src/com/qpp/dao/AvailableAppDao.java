package com.qpp.dao;

import com.qpp.model.TAvailableApp;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class AvailableAppDao extends BaseDao<TAvailableApp> {
    public AvailableAppDao() {
        super(TAvailableApp.class);
    }
}
