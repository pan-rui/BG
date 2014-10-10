package com.qpp.service.user.impl;

import com.qpp.dao.RoleDao;
import com.qpp.model.TRole;
import com.qpp.service.user.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by qpp on 10/8/2014.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Override
    public void save(TRole role) {
        roleDao.save(role);
    }

    @Override
    public TRole query(long id) {
       return roleDao.getById(id);
    }

    @Override
    public void delete(TRole role) {
        roleDao.delete(role);
    }

    @Override
    public void update(TRole role) {
        roleDao.update(role);
    }
}
