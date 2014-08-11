package com.qpp.service.common;

import com.qpp.dao.CountryDao;
import com.qpp.model.TCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by admin on 2014/8/6.
 */
@Service
public class CommonDataService {
   @Autowired
    private CountryDao countryDao;

    @Cacheable(value="commonData")
    public List<TCountry> getAllCountry(){
        return countryDao.getsByQuery("from TCountry");
    }
    @Cacheable(value="commonData",key="#countryCode")
    public TCountry getCountry(String countryCode){
        TCountry country=countryDao.getById(countryCode);
        return country;
    }
}
