package com.qpp.dao;

import com.qpp.model.TUser;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class UserDao extends BaseDao<TUser> {
    public UserDao() {
        super(TUser.class);
    }
}
