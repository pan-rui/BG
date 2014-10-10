package com.qpp.service.user;

import com.qpp.model.TUserRole;

/**
 * Created by qpp on 10/8/2014.
 */
public interface UserRoleService {

    public void authorization(TUserRole userRole);

    public TUserRole queryAuth(long id);

    public void cancelAuth(TUserRole userRole);

    public void updateAuth(TUserRole userRole);
}
