package com.qpp.dao;

import com.qpp.model.TScore;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class ScoreDao extends BaseDao<TScore> {
    public ScoreDao() {
        super(TScore.class);
    }
}
