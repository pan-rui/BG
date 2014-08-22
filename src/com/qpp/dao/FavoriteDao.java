package com.qpp.dao;

import com.qpp.model.TFavorite;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class FavoriteDao extends BaseDao<TFavorite> {
    public FavoriteDao() {
        super(TFavorite.class);
    }
}
