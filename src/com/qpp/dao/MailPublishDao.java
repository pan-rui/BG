package com.qpp.dao;

import com.qpp.model.TMailPublish;
import org.springframework.stereotype.Repository;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class MailPublishDao extends BaseDao<TMailPublish> {
    public MailPublishDao() {
        super(TMailPublish.class);
    }
}
