package com.qpp.dao;

import com.qpp.model.TConvert;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class ConvertDao extends BaseDao<TConvert> {
    public ConvertDao() {
        super(TConvert.class);
    }
    public void save(TConvert convert) {
        super.save(convert);
    }
    public TConvert getById(String id){
        return super.getById(id,TConvert.class);
    }
    public void update(TConvert convert){
        super.update(convert);
    }
    public TConvert getBySql(String sql){
        return super.getBySQL(sql);
    }
    public void update(String tableName, Map newData, String cond){
        try {
            super.update(tableName,newData,cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int delete(String property, Object value) {
        return super.delete(property, value);
    }
}
