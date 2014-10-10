package com.qpp.test;

import com.qpp.action.user.UserAction;
import com.qpp.dao.MailPublishDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by qpp on 8/25/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/WEB-INF/spring/applicationContext-hibernate.xml","/WEB-INF/spring/spring-servlet.xml"})
public class SqlTest extends BaseTest {
    @Resource
    private MailPublishDao mailPublishDao;
    @Resource
    private UserAction userAction;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testBySqlObject() {
        Object obj= mailPublishDao.getObjectBySQL("select comment from t_mailPublish where id='yR8phHItsuvTO7gSb'");
//        mailPublishDao.getsByQuery("select new TMailPublish(id,subject,content,sendDate,ctime,isPublish) from t_mailPublish where id='yR8phHItsuvTO7gSb'");
//        Object obj = jdbcTemplate.queryForList("select id from t_mailPublish");
        System.out.println(obj.toString());
//        Assert.assertTrue(list.size()==8);
    }
    @Test
    public void testMessage(){

    }
}
