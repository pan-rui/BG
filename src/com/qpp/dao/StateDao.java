package com.qpp.dao;

import com.qpp.model.TState;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2014/7/29.
 */
@Repository
public class StateDao extends BaseDao<TState> {
    public StateDao(){
        super(TState.class);
    }
    public TState getState(TState tStatePK) {
        TState tState =getById(tStatePK);
        //TState tState = getByQuery("from TState where countryCode='" + tStatePK.getCountryCode() + "' and stateCode='" + tStatePK.getStateCode() + "'");
        return tState;
    }
}
