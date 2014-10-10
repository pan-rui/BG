package com.qpp.dao;

import com.qpp.model.ApiLog;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by SZ_it123 on 2014/9/23.
 */
@Repository
public class ApiLogDao extends MongoBaseDao<ApiLog> {
    public List<ApiLog> getByDateRange(Date begin,Date end,int from,int size){
        Query query=new Query(Criteria.where("createDate").exists(true).andOperator(Criteria.where("createDate").gte(begin),Criteria.where("createDate").lte(end)));
        if (from>=0)
            query.skip(from);
        if (size>0)
            query.limit(size);
        return  mongoTemplate.find(query,ApiLog.class);
    }
    public List<ApiLog> getByDateRange(String appId,Date begin,Date end,int from,int size){
        Query query=new Query(Criteria.where("appId").is(appId).andOperator(Criteria.where("createDate").gte(begin),Criteria.where("createDate").lte(end)));
        if (from>=0)
            query.skip(from);
        if (size>0)
            query.limit(size);
        return  mongoTemplate.find(query,ApiLog.class);
    }
    public List<ApiLog> getByAppId(String appId,int from,int size){
        Query query=new Query(Criteria.where("appId").is(appId));
        if (from>=0)
            query.skip(from);
        if (size>0)
            query.limit(size);
        return  mongoTemplate.find(query,ApiLog.class);
    }


}
