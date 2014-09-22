package com.qpp.dao;

import com.qpp.model.TMailPublish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qpp on 7/30/2014.
 */
@Repository
public class MailPublishDao extends BaseDao<TMailPublish> {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public MailPublishDao() {
        super(TMailPublish.class);
    }

    public List<String> queryAddr(String sql,String field) {
        List<Map<String,Object>> resultList=jdbcTemplate.queryForList(sql);
        List<String> list = new ArrayList<String>();
        for (Map<String, Object> map : resultList)
            list.add(String.valueOf(map.get(field)));
        return list;
    }

    public List<TMailPublish> getsByQueryPage(String sqlString, int start, int end) {
        String sql=sqlString+" limit "+start+","+end;
       return jdbcTemplate.query(sql,new RowMapper<TMailPublish>() {
            @Override
            public TMailPublish mapRow(ResultSet resultSet, int i) throws SQLException {
                TMailPublish mailPublish = new TMailPublish();
                mailPublish.setId(resultSet.getInt("id"));
                mailPublish.setAttachment(resultSet.getString("attachment"));
                mailPublish.setSendDate(resultSet.getDate("sendDate"));
                mailPublish.setCc(resultSet.getString("cc"));
                mailPublish.setContent(resultSet.getString("content"));
                mailPublish.setIsPublish(resultSet.getString("isPublish"));
                mailPublish.setComment(resultSet.getString("comment"));
                mailPublish.setSubject(resultSet.getString("subject"));
                mailPublish.setCtime(resultSet.getDate("ctime"));
                mailPublish.setFromm(resultSet.getString("fromm"));
                return mailPublish;
            }
        });
    }
}
