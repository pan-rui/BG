package com.qpp.dao;

import com.qpp.model.TAddress;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class AddressDao extends BaseDao<TAddress> {
    public AddressDao() {
        super(TAddress.class);
    }
}
