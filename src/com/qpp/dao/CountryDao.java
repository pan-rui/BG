package com.qpp.dao;

import com.qpp.model.TCountry;
import org.hibernate.annotations.Parent;
import org.springframework.stereotype.Repository;
import sun.awt.SunHints;

/**
 * Created by admin on 2014/7/29.
 */
@Repository
public class CountryDao extends BaseDao<TCountry> {
    public CountryDao(){
        super(TCountry.class);
    }

}
