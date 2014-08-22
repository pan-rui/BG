package com.qpp.service.common;

import com.qpp.dao.CountryDao;
import com.qpp.dao.StateDao;
import com.qpp.model.TCountry;
import com.qpp.model.TState;
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
    @Autowired
    private StateDao stateDao;

    @Cacheable(value="commonData")
    public List<TCountry> getAllCountry(){
        return countryDao.getsByQuery("from TCountry");
    }
    @Cacheable(value="commonData",key="#countryCode")
    public TCountry getCountry(String countryCode){
        TCountry country=countryDao.getById(countryCode);
        return country;
    }
    public List<TState> getAlState(){
         return stateDao.getsByQuery("from TState");
    }
    @Cacheable(value="commonData",key="#tStatePK.countryCode+#tStatePK.stateCode")
    public TState getState(TState tStatePK){
        TState tState=stateDao.getById(tStatePK);
        //TState tState=stateDao.getByQuery("from TState where countryCode='"+tStatePK.getCountryCode()+"' and stateCode='"+tStatePK.getStateCode()+"'");
        return tState;
    }

}
