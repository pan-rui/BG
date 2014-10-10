package com.qpp.dao;

import com.qpp.model.TUserRole;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class UserRoleDao extends BaseDao<TUserRole> {
    public UserRoleDao() {
        super(TUserRole.class);
    }
}
