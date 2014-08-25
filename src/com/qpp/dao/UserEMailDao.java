package com.qpp.dao;

import com.qpp.model.TUserEmail;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class UserEMailDao extends BaseDao<TUserEmail> {
    public UserEMailDao() {
        super(TUserEmail.class);
    }
}
