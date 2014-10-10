package com.qpp.dao;

import com.qpp.model.TUserPassHistory;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class UserPassHistoryDao extends BaseDao<TUserPassHistory> {
    public UserPassHistoryDao() {
        super(TUserPassHistory.class);
    }
}
