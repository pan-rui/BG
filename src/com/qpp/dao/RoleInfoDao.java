package com.qpp.dao;

import com.qpp.model.TRoleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2014/8/21.
 */
@Repository
public class RoleInfoDao extends BaseDao<TRoleInfo> {
    public RoleInfoDao(){
        super(TRoleInfo.class);
    }
}
