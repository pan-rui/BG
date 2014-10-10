package com.qpp.service.user.impl;

import com.qpp.dao.UserRoleDao;
import com.qpp.model.TUserRole;
import com.qpp.service.user.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by qpp on 10/8/2014.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleDao userRoleDao;
    @Override
    public void authorization(TUserRole userRole) {
        userRoleDao.save(userRole);
    }

    @Override
    public TUserRole queryAuth(long id) {
       return userRoleDao.getById(id);
    }

    @Override
    public void cancelAuth(TUserRole userRole) {
        userRoleDao.delete(userRole);
    }

    @Override
    public void updateAuth(TUserRole userRole) {
        userRoleDao.update(userRole);
    }
}
