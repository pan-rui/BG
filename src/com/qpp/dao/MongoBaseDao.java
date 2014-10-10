package com.qpp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by SZ_it123 on 2014/9/22.
 */
@SuppressWarnings("unchecked")
public class MongoBaseDao<T> {
    @Autowired
    protected MongoTemplate mongoTemplate;

    public void save(T po) {
        mongoTemplate.save(po);
    }

    public void delete(T po) {
        mongoTemplate.remove(po);
    }
    public List getAll(T po){
        return mongoTemplate.findAll(po.getClass());
    }
    public List getAll(T po,int from,int size){
        if (from>0 || size>0){
            Query query=new Query();
            if (from>=0)
                query.skip(from);
            if (size>0)
                query.limit(size);
            return mongoTemplate.find(query,po.getClass());
        }
        return mongoTemplate.findAll(po.getClass());
    }

}
