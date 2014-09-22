package com.qpp.service.user.impl;

import com.qpp.dao.*;
import com.qpp.model.*;
import com.qpp.service.user.UserService;
import com.qpp.util.Email;
import com.qpp.util.MD5;
import com.qpp.util.TriggerUtil;
import org.quartz.JobDataMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by qpp on 7/31/2014.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private Email mail;
    @Resource
    private ConfigDao configDao;
    private Pattern patten = Pattern.compile("^([\\w|-]+)@(\\w+)\\.(\\w+)$");
    private MD5 md5 = new MD5();
    @Resource
    private ScoreDao scoreDao;
    @Resource
    private ConvertDao convertDao;
    @Resource
    private MailPublishDao mailPublishDao;
    @Resource
    private UserEMailDao userEMailDao;
    @Resource
    private CountryDao countryDao;
    @Resource
    private JdbcTemplate jdbcTemplate;
    //    @Resource
//    private Email Mail;
//    @Resource
//    private MessageSourceResourceBundle messageSource;
    @Override
    public BaseReturn checkLogin(String user, String password) {
        BaseReturn result = new BaseReturn();
        TUser tuser = null;
        if (patten.matcher(user).matches()) {
            tuser = userDao.getBySQL("select * from t_user where email='" + user + "'");
        } else
            tuser = userDao.getBySQL("select * from t_user where name='" + user + "'");
        if (tuser != null && tuser.getPassword().equals(md5.getMD5ofStr(password))) {
            result.setResult(0);
//            result.setData(tuser);
        } else {
            result.setResult(1);
            result.setErrMessage("err.user.login.check");
        }
        return result;
    }

    @Override
    public BaseReturn checkRegister(String type, String value) {
        BaseReturn result = new BaseReturn();
        if ("name".equals(type)){
            if (!value.matches("^[\\w|-|_]{6,18}$") || Integer.parseInt(exists(userDao, "select count(1) from t_user where name='" + value + "'")) > 0)
                return new BaseReturn("err.user.register.username.verify", 1);
        }else{
            if (!patten.matcher(value).matches() || Integer.parseInt(exists(userDao, "select count(1) from t_user where email='" + value + "'")) > 0)
                return new BaseReturn("err.user.register.email.verify", 1);
        }
            result.setResult(0);
        return result;
    }

    @Override
    public BaseReturn emailActive(int userId, String time) {
        BaseReturn result = new BaseReturn();
        int interval = 7 * 24 * 3600 * 1000;
        TUser tuser = userDao.getById(userId);
        boolean valid = (Long.parseLong(time) - tuser.getCtime().getTime()) < interval;
        if (tuser != null && valid) {
            tuser.setStatus("1");
            userDao.update(tuser);
            result.setResult(0);
            result.setData(tuser);
        } else {
            result.setResult(1);
            result.setErrMessage("err.user.action.email.failed");
        }
        return result;
    }

    public String exists(BaseDao dao, String sql) {
        Object obj = dao.getObjectBySQL(sql);
        return String.valueOf(obj);
    }

    @Override
    public BaseReturn register(TUser user) {
        if (!user.getName().matches("^[\\w|\\-|_]{6,18}$") || Integer.parseInt(exists(userDao, "select count(1) from t_user where name='" + user.getName() + "'")) > 0)
            return new BaseReturn("err.user.register.username.verify", 1);
        if (!patten.matcher(user.getEmail()).matches() || Integer.parseInt(exists(userDao, "select count(1) from t_user where email='" + user.getEmail() + "'")) > 0)
            return new BaseReturn("err.user.register.email.verify", 1);
        if(!user.getPassword().matches("^.{8,18}$"))
            return new BaseReturn("err.user.register.password.verify", 1);
        if(!String.valueOf(user.getPhone()).matches("(^(\\d{3}-){2}\\d{4})|((^\\+\\d{2})?[13|15|14|18]+\\d{9})"))
            return new BaseReturn("err.user.register.phone.verify",1);
        if (!mailPublishDao.queryAddr("select CountryCode from qb_country_info","CountryCode").contains(user.getCountryCode()))
            return new BaseReturn("err.user.register.countryCode.verify", 1);
        //可选 邮编验证.....
        BaseReturn result = new BaseReturn();
        if ("c".equals(user.getType())) {
            //CGP用户....事项
        } else {
            //一般用户......
        }

        Date date = new Date();
//        String userId = RandomSymbol.getAllSymbol(16);
//        user.setId(userId);
        user.setStatus("0");
        user.setCtime(date);
        user.setUtime(date);
        user.setPassword(md5.getMD5ofStr(user.getPassword()));
        if(user.getAddress()!=null&&!"".equals(user.getAddress()))
            user.setAddress("[{'idDefault':true,'addr':'"+user.getAddress()+"'}]");
        try {
            userDao.save(user);
            result.setResult(0);
            result.setData(user);
            TConfig subject = configDao.getById("email.subject"); //TODO:需添加此配置在数据库中
            TConfig context = configDao.getById("email.context");
//            Mail.sendMail(getMessage(request,"email.subject",null),getMessage(request,"email.context",new Object[]{tuser.getId(),new Date().getTime()}),tuser.getEmail());
            mail.sendMail(MessageFormat.format(subject.getConfigValue(), null), MessageFormat.format(context.getConfigValue(), user.getId(), new Date().getTime()), new String[]{user.getEmail()});
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.register.failed");
            return result;
        }
        return result;
    }

    @Override
    public BaseReturn rePassword(String name, String userEmail) {
        BaseReturn result = new BaseReturn();
        if (name.matches("^[\\w|-|_]{6,18}$")) {
//            String email = mailPublishDao.queryAddr("select email from t_user where name='" + name + "'","email").get(0);
            String email=exists(userDao, "select email from t_user where name='" + name + "'");
            if (!email.equals(userEmail)) {
                result.setResult(1);
                result.setErrMessage("err.user.rePassword.verify");
                return result;
            }
        } else
            return new BaseReturn("err.user.register.username.verify",1);
//        else {
//            TUser tuser = userDao.getBySQL("select name,email from t_user where email='" + name + "'");
//            if (!tuser.getEmail().equals(userEmail)) {
//                result.setResult(0);
//                result.setErrMessage("err.user.rePassword.verify2");
//                return result;
//            }
//        }
        result.setResult(0);
        TConfig subject = configDao.getById("email.rePass.subject");//TODO:数据训预定义
        TConfig context = configDao.getById("email.rePass.context");
        mail.sendMail(MessageFormat.format(subject.getConfigValue(), null), MessageFormat.format(context.getConfigValue(), userEmail, new Date().getTime()), new String[]{userEmail});
        return result;
    }

    @Override
    public BaseReturn rePass(TUser user, String oldPass, String newPass) {
        BaseReturn result = new BaseReturn();
        if (!user.getPassword().equals(md5.getMD5ofStr(oldPass))) {
            result.setResult(1);
            result.setErrMessage("err.user.modify.Pass.oldPassword.verify");
            return result;
        }
//        user.setPassword(md5.getMD5ofStr(user.getPassword()));
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("password", md5.getMD5ofStr(newPass));
        dataMap.put("utime", new Date());
        dataMap.put("id", user.getId());
        try {
            userDao.update("t_user", dataMap);
            result.setResult(0);
            result.setData(user);
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.modify.Pass.failed");
            return result;
        }
        return result;
    }

    @Override
    public BaseReturn updateUser(TUser user) {
        BaseReturn result = new BaseReturn();
        Map<String, Object> dataMap = getMap(user);
        dataMap.put("utime", new Date());
        dataMap.put("id", user.getId());
        try {
            userDao.update("t_user", dataMap);
            result.setResult(0);
//            result.setData(user);
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.update.failed");
            return result;
        }
        return result;
    }

    @Override
    public BaseReturn queryUser(PageModel<TUser> page) {
//        BaseReturn result = new BaseReturn();
//        StringBuffer sql = new StringBuffer("select * from t_user");
//        if(page.getSearchValue()!=null&&!page.getSearchValue().toString().trim().equals(""))
//            sql.append(" where " + page.getSearchProperty() + " like '%" + page.getSearchValue(). toString() + "%'");
//        if(page.getOrderPropery()!=null&&!page.getOrderPropery().trim().equals(""))
//            sql.append(" order by "+page.getOrderPropery());
//        if(page.getIsDesc())
//            sql.append(" desc");
//        page.setRowCount(userDao.getKeyBySQL(sql.toString()));
//        page.setData(userDao.getsByQueryPage(sql.toString(), page.getStart(), page.getPageSize()));
////        result.setResult(page.getRowCount());
//        result.setData(page);
        return queryPage(page, "t_user", userDao);
    }

    public String joinPageSql(String searchProperty, String searchValue,String orderProperty, Boolean isDesc, int start, int end) {
        StringBuffer sql = new StringBuffer("select * from t_user");
        if (searchValue != null && !"".equals(searchValue))
            sql.append(" where "+searchProperty+" like '%"+searchValue+"%'");
        if(orderProperty!=null&&!"".equals(orderProperty))
            sql.append(" order by " + orderProperty);
        if(isDesc!=null&&isDesc)
            sql.append(" desc");
        sql.append(" limit " + start + "," + end);
        return sql.toString();
    }
    public BaseReturn queryUser(String searchProperty, String searchValue,String orderProperty, Boolean isDesc, int start, int end){
        try {
            List<TUser> list = userDao.getsBySQL(joinPageSql(searchProperty, searchValue, orderProperty, isDesc, start, end));
            return new BaseReturn(1, list);
        }catch (Exception e) {
            return new BaseReturn("err.user.query.faild", 0);
        }
    }


    @Override
    public BaseReturn queryScore(PageModel<TScore> page) {

        return queryPage(page, "t_score", scoreDao);
    }

    @Override
    public BaseReturn scoreProcess(TUser user, TGift gift, int count) {
        BaseReturn result = new BaseReturn();
        int needScore = gift.getPrice() * count;
//        if(user.getStatus()==0){
//            //未激活用户
//            result.setResult(0);
//            result.setErrMessage("抱歉,未激活用户无法享受此活动!");
//              return result;
//        }
        if (user.getScore() < needScore) {
            result.setResult(1);
            result.setErrMessage("err.user.score.convert.failed");
        }
        //更新单个字段
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("score", user.getScore() - needScore);
        dataMap.put("utime", new Date());
        dataMap.put("id", user.getId());
        userDao.update("t_user", dataMap);
        //新增积分记录
        TConvert convert = new TConvert();
        Date date = new Date();
//        convert.setId(RandomSymbol.getAllSymbol(16));
        convert.setCtime(date);
        convert.setTarget(gift);
        convert.setUserId(user);
        convert.setCount(count);
        convert.setType("fromScore");
        convert.setAmount(needScore);
        convertDao.save(convert);
        result.setResult(0);
        result.setData(user);
        return result;
    }

    public BaseReturn rePasswordd(String email, String password, String time) {
        BaseReturn result = new BaseReturn();
        if (Long.parseLong(time) + 24 * 60 * 60 * 1000 < new Date().getTime()) {
            result.setResult(1);
            result.setErrMessage("err.user.rePassword.expired");
            return result;
        }
        String newEmail = exists(userDao,"select email from t_user where email='" + email + "'");
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("password",md5.getMD5ofStr(password));
        dataMap.put("utime", new Date());
        dataMap.put("email", newEmail);
//        tuser.setPassword();
        try {
            userDao.update("t_user",dataMap);
            result.setResult(0);
//            result.setData(tuser);
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.rePassword.failed");
            return result;
        }
        return result;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param tableName
     * @param dao
     * @return
     */
    public BaseReturn queryPage(PageModel page, String tableName, BaseDao dao) {
        BaseReturn result = new BaseReturn();
        StringBuffer sql = new StringBuffer("select * from " + tableName);
        if (page.getSearchValue() != null && !page.getSearchValue().toString().trim().equals(""))
            sql.append(" where " + page.getSearchProperty() + "like '%" + page.getSearchValue().toString() + "%'");
        if (page.getOrderPropery() != null && !page.getOrderPropery().trim().equals(""))
            sql.append(" order by " + page.getOrderPropery());
        if (page.getIsDesc())
            sql.append(" desc");
        page.setRowCount(dao.getKeyBySQL(sql.toString()));
        page.setData(dao.getsByQueryPage(sql.toString(), page.getStart(), page.getPageSize()));
//        result.setResult(page.getRowCount());
        result.setData(page);
        return result;
    }

    public boolean update(String tableName, Map data) {
        try {
            userDao.update(tableName, data);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Map<String, Object> getMap(TUser user) {
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        Field[] fields = user.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object obj = field.get(user);
                if (obj != null && !"".equals(obj) && !(obj instanceof Set) && !obj.getClass().getName().startsWith("com.qpp.model")&&!"id".equals(field.getName()))
                    dataMap.put(field.getName(), obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new LinkedHashMap<String, Object>();
        }
        return dataMap;
    }

    public BaseReturn setUserStatus(int userId, String status) {
        BaseReturn result = new BaseReturn();
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("status", status);
        dataMap.put("utime", new Date());
        dataMap.put("id", userId);
        try {
            userDao.update("t_user", dataMap);
            result.setResult(0);
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.set.status.failed");
            return result;
        }
        return result;
    }

    public BaseReturn setUserType(int userId, String type) {
        BaseReturn result = new BaseReturn();
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("type", type);
        dataMap.put("utime", new Date());
        dataMap.put("id", userId);
        try {
            userDao.update("t_user", dataMap);
            result.setResult(0);
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.set.type.failed");
            return result;
        }
        return result;
    }

    public BaseReturn setUserAddr(int userId, String address) {
        BaseReturn result = new BaseReturn();
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("address", address);
        dataMap.put("utime", new Date());
        dataMap.put("id", userId);
        try {
            userDao.update("t_user", dataMap);
            result.setResult(0);
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.set.address.failed");
            return result;
        }
        return result;
    }

    public BaseReturn addEmail(TMailPublish email) {
        BaseReturn result = new BaseReturn();
//        email.put("sendDate", new Date(System.currentTimeMillis() + 3000));
        if (email.getCtime() == null)
            email.setCtime(new Date());
//        email.setId(RandomSymbol.getAllSymbol(16));
        try {
            mailPublishDao.getHibernateTemplate().saveOrUpdate(email);
//            System.out.println("...................");
            if ("1".equals(email.getIsPublish())) {
                List<String> addressList = mailPublishDao.queryAddr("select user.email from t_mailPublish mail join t_userEmail userE on mail.id=userE.emailId join t_user user on userE.userId=user.id where mail.id='" + email.getId() + "'","email");
//                List<String> addressList = hibernateTemplate.find("select user.email from t_mailPublish mail inner join fetch t_userEmail userE on mail.id=userE.emailId inner join fetch t_user user on userE.userId=user.id where mail.id=?", email.getId());
                if (addressList.size() > 0) {
                    JobDataMap dataMap = new JobDataMap();
                    dataMap.put("mailUtil", mail);
                    dataMap.put("email", email);
                    dataMap.put("addrs", addressList);
                    TriggerUtil.simpleTask(dataMap, EmailJob.class, email.getSendDate());
                }
            }
            result.setResult(0);
            result.setData(email);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(1);
            result.setErrMessage("err.user.add.emailPublish");
            return result;
        }
        return result;
    }

    public BaseReturn subscription(TUser user,int emailId) {
        BaseReturn result = new BaseReturn();
//        userEmail.setId(RandomSymbol.getAllSymbol(16));
        try {
            TMailPublish email = mailPublishDao.getById(emailId);
            TUserEmail userEmail=new TUserEmail(user,email);
            userEMailDao.save(userEmail);
            result.setResult(0);
            result.setData(user);
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.subscription.email");
            return result;
        }
        return result;
    }

    public BaseReturn cancelSubscript(int userId,int emailId) {
        BaseReturn result = new BaseReturn();
        try {
            userDao.execBySQL("delete t_userEmail where userId=" + userId + " and emailId=" + emailId);
//            userEMailDao.delete("id", userEmail.getId());
            result.setResult(0);
            result.setData(userId);
        } catch (Exception e) {
            result.setResult(1);
            return result;
        }
        return result;
    }

    public BaseReturn queryEmail(PageModel<TMailPublish> page, TUser user) {
        if (user.getType().equals("c"))
            return queryPage(page, "t_mailPublish", mailPublishDao);
        else if (user.getType().equals("m"))
            return queryPage(page, "t_mailPublish mail join t_userEmail userE on mail.id=userE.emailId join t_user user on user.id=userE.userId", mailPublishDao);
        else
            return new BaseReturn("err.user.query.emailPublish.verify",0);
    }

    public TUser getById(Serializable id) {
        return userDao.getById(id);
    }
}
