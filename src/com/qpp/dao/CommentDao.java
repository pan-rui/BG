package com.qpp.dao;

import com.qpp.model.TComment;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class CommentDao extends BaseDao<TComment>{
    public CommentDao(){super(TComment.class);}
}
