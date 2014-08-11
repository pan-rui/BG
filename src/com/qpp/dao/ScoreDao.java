package com.qpp.dao;

import com.qpp.model.TScore;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class ScoreDao extends BaseDao<TScore> {
    public ScoreDao() {
        super(TScore.class);
    }
    public void save(TScore score) {
        super.save(score);
    }
    public TScore getById(String id){
        return super.getById(id,TScore.class);
    }
    public void update(TScore score){
        super.update(score);
    }
    public TScore getBySql(String sql){
        return super.getBySQL(sql);
    }
    public void update(String tableName, Map newData, String cond){
        try {
            super.update(tableName,newData,cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean insert(TScore score){
        return super.insert(score);
    }

    public int delete(String property, Object value) {
        return super.delete(property, value);
    }
}
