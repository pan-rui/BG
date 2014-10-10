package com.qpp.service.user;

import com.qpp.model.TRole;

/**
 * Created by qpp on 10/9/2014.
 */
public interface RoleService {
    public void save(TRole role);

    public TRole query(long id);

    public void delete(TRole role);

    public void update(TRole role);
}
