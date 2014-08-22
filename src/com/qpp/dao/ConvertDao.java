package com.qpp.dao;

import com.qpp.model.TConvert;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class ConvertDao extends BaseDao<TConvert> {
    public ConvertDao() {
        super(TConvert.class);
    }
}
