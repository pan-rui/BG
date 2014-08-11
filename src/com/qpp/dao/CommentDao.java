package com.qpp.dao;

import com.qpp.model.TComment;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class CommentDao extends BaseDao<TComment>{
    public CommentDao(){super(TComment.class);}
    public void save(TComment comment) {
        super.save(comment);
    }
    public TComment getById(String id){
        return super.getById(id,TComment.class);
    }
    public void update(TComment comment){
        super.update(comment);
    }
    public TComment getBySql(String sql){
        return super.getBySQL(sql);
    }
    public void update(String tableName, Map newData, String cond){
        try {
            super.update(tableName,newData,cond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean insert(TComment comment){
        return super.insert(comment);
    }

    public int delete(String property, Object value) {
        return super.delete(property, value);
    }
}
