package com.qpp.dao;

import com.qpp.model.TFavorite;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class FavoriteDao extends BaseDao<TFavorite> {
    public FavoriteDao() {
        super(TFavorite.class);
    }
    public void save(TFavorite favorite) {
        super.save(favorite);
    }
    public TFavorite getById(String id){
        return super.getById(id,TFavorite.class);
    }
    public void update(TFavorite favorite){
        super.update(favorite);
    }
    public TFavorite getBySql(String sql){
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
