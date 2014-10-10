package com.qpp.dao;

import com.qpp.model.TRole;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class RoleDao extends BaseDao<TRole> {
    public RoleDao() {
        super(TRole.class);
    }
}
