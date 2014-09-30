package com.qpp.service.user.impl;

import com.qpp.dao.*;
import com.qpp.model.*;
import com.qpp.service.user.UserService;
import com.qpp.util.Email;
import com.qpp.util.MD5;
import com.qpp.util.TriggerUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private UserPassHistoryDao userPassHistoryDao;
    @Resource
    private AvailableAppDao availableAppDao;
    @Resource
    private OpenidInfoDao openidInfoDao;
    public static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

//    @Resource
//    public MemCachedClient memcachedClient;
    @Override
    public BaseReturn checkLogin(String user, String password) {
        BaseReturn result = new BaseReturn();
        TUser tuser = null;
        if (patten.matcher(user).matches()) {
            tuser = userDao.getBySQL("select * from t_user where email='" + user + "'");
        } else
            tuser = userDao.getBySQL("select * from t_user where name='" + user + "'");
        long passordId=tuser.getCurrentPassword();
        TUserPassHistory userPassHistory = userPassHistoryDao.getById(passordId);
        if (tuser != null && userPassHistory.getPassword().equals(md5.getMD5ofStr(password))) {
            result.setResult(0);
            result.setData(tuser);
        } else {
            result.setResult(1);
            result.setErrMessage("err.user.login.check");
        }
        return result;
    }

    public BaseReturn otherLogin(Map<String,Object> data) {
//        BaseReturn result = new BaseReturn();
        String openId=null,siteId=null;
        if(data.containsKey("openId")) openId = String.valueOf(data.remove("openId"));
        if(data.containsKey("siteId")) siteId = String.valueOf(data.remove("siteId"));
        TOpenidInfo openidInfo=openidInfoDao.getBySQL("select * from t_openidInfo where openId='" + openId + "' and siteId='" + siteId + "'");
        if(openidInfo!=null)
           return new BaseReturn(0,openidInfo);
        else {
            TUser user = new TUser();
            try {
                BeanUtils.populate(user, data);
                user.setRegisterType(20);
                Date date = new Date();
                user.setCtime(date);
                user.setUtime(date);
                user.setStatus("1");
                userDao.save(user);
//                long userId=Long.parseLong(exists(userDao, "select oid from t_user where ctime='" + new Timestamp(date.getTime()) + "'"));
                TUser tUser=userDao.getBySQL("select * from t_user where ctime='" + new Timestamp(date.getTime()).toString().replaceFirst("\\.\\d{1,3}",".0") + "'");
                openidInfo = new TOpenidInfo(openId, siteId, tUser.getOid());
                openidInfoDao.save(openidInfo);
                return new BaseReturn(0,tUser);
            } catch (Exception e) {
                e.printStackTrace();
                return new BaseReturn("err.user.login.unknow", 1);
            }
        }
    }
//    @Override
//    public BaseReturn checkRegister(String type, String value) {
//        BaseReturn result = new BaseReturn();
//        if ("name".equals(type)){
//            if (!value.matches("^[\\w|-|_]{6,18}$") || Integer.parseInt(exists(userDao, "select count(1) from t_user where name='" + value + "'")) > 0)
//                return new BaseReturn("err.user.register.username.verify", 1);
//        }else{
//            if (!patten.matcher(value).matches() || Integer.parseInt(exists(userDao, "select count(1) from t_user where email='" + value + "'")) > 0)
//                return new BaseReturn("err.user.register.email.verify", 1);
//        }
//            result.setResult(0);
//        return result;
//    }

    @Override
    public BaseReturn emailActive(long userId, String time) {
        BaseReturn result = new BaseReturn();
        int interval = 7 * 24 * 3600 * 1000;
        TUser tuser = userDao.getById(userId);
        boolean valid = (Long.parseLong(time) - tuser.getCtime().getTime()) < interval;
        if (tuser != null && valid) {
//            tuser.setStatus("1");
            Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
            dataMap.put("status", "1");
            dataMap.put("oid", userId);
            userDao.update("t_user",dataMap);
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
    public BaseReturn register(Map<String,Object> data,long appId) {
        String password=null;
//        String openId=null;
//        String siteId=null;
//        if(data.containsKey("openId"))
//            openId = String.valueOf(data.remove("openId"));
//        if(data.containsKey("siteId"))
//            siteId = String.valueOf(data.remove("siteId"));
        if(data.containsKey("password"))
            password = String.valueOf(data.remove("password"));
        TUser user = new TUser();
        BaseReturn result = new BaseReturn();
        try {
            BeanUtils.populate(user,data);
//            Set<ConstraintViolation<TUser>> constraints= validator.validate(user);
//            for (ConstraintViolation<TUser> constraintViolation : constraints)

        if (!user.getName().matches("^[\\w|\\-|_]{6,18}$") || Integer.parseInt(exists(userDao, "select count(1) from t_user where name='" + user.getName() + "'")) > 0)
            return new BaseReturn("err.user.register.username.verify", 1);
        if (!patten.matcher(user.getEmail()).matches() || Integer.parseInt(exists(userDao, "select count(1) from t_user where email='" + user.getEmail() + "'")) > 0)
            return new BaseReturn("err.user.register.email.verify", 1);
        if(!password.matches("^[a-f0-9]{16,32}$"))
            return new BaseReturn("err.user.register.password.verify", 1);
        if(!String.valueOf(user.getPhone()).matches("(^(\\d{3}-){2}\\d{4})|((^\\+\\d{2})?[13|15|14|18|17]+\\d{9})"))
            return new BaseReturn("err.user.register.phone.verify",1);
        if (!mailPublishDao.queryAddr("select CountryCode from qb_country_info","CountryCode").contains(user.getCountryCode()))
            return new BaseReturn("err.user.register.countryCode.verify", 1);
        //可选 邮编验证.....
        Date date = new Date();
//        String userId = RandomSymbol.getAllSymbol(16);
//        user.setOid(userId);
        user.setStatus("0");
        user.setCtime(date);
        user.setUtime(date);
        if(appId!=0l)
            user.setRegisterType(30);
//            user.setType(appId);
//        }
//        else if(openId!=null&&siteId!=null) //第三方注册
//            user.setRegisterType(20);
//        if(user.getAddress()!=null&&!"".equals(user.getAddress()))
//            user.setAddress("[{'isDefault':true,'addr':'"+user.getAddress()+"'}]");
            userDao.save(user);
            long oid = Long.parseLong(exists(userDao, "select oid from t_user where email='" + user.getEmail()+"'"));
            TUserPassHistory userPassHistory=new TUserPassHistory(oid,date,md5.getMD5ofStr(password));
            userPassHistoryDao.save(userPassHistory); //保存变更密码记录
            TAvailableApp availableApp = new TAvailableApp(oid, appId);
            availableAppDao.save(availableApp); //保存用户来源
            long passOid=Long.parseLong(exists(userPassHistoryDao,"select oid from t_userPassHistory where userId='"+oid+"' and password='"+md5.getMD5ofStr(password)+"'"));
            Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); //更新用户密码记录
            dataMap.put("currentPassword", passOid);
            dataMap.put("oid", oid);
            userDao.update("t_user",dataMap);
//            if(user.getRegisterType()==20) { //保存第三方注册记录
//                TOpenidInfo openidInfo = new TOpenidInfo();
//                openidInfo.setOpenId(openId);
//                openidInfo.setSiteId(siteId);
//                openidInfo.setUserId(oid);
//                openidInfoDao.save(openidInfo);
//            }
            result.setResult(0);
            result.setData(user);
            TConfig subject = configDao.getById("email.subject"); //TODO:需添加此配置在数据库中
            TConfig context = configDao.getById("email.context");
//            Mail.sendMail(getMessage(request,"email.subject",null),getMessage(request,"email.context",new Object[]{tuser.getId(),new Date().getTime()}),tuser.getEmail());
            mail.sendMail(MessageFormat.format(subject.getConfigValue(), null), MessageFormat.format(context.getConfigValue(), user.getOid(),new Date().getTime()), new String[]{user.getEmail()});
        } catch (Exception e) {
//            e.printStackTrace();
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
        TConfig subject = configDao.getById("email.rePass.subject");//TODO:数据需预定义
        TConfig context = configDao.getById("email.rePass.context");
        mail.sendMail(MessageFormat.format(subject.getConfigValue(), null), MessageFormat.format(context.getConfigValue(), userEmail, new Date().getTime()), new String[]{userEmail});
        return result;
    }

    @Override
    public BaseReturn rePass(TUser user, String oldPass, String newPass) {
        BaseReturn result = new BaseReturn();
        TUserPassHistory userPassHistory=userPassHistoryDao.getById(user.getCurrentPassword());
        if (!userPassHistory.getPassword().equals(md5.getMD5ofStr(oldPass))) {
            result.setResult(1);
            result.setErrMessage("err.user.modify.Pass.oldPassword.verify");
            return result;
        }
//        user.setPassword(md5.getMD5ofStr(user.getPassword()));
        Date date = new Date();
        TUserPassHistory userPassHistory1=new TUserPassHistory(user.getOid(),date,md5.getMD5ofStr(newPass));
        try {
        userPassHistoryDao.save(userPassHistory1);
        long passId=Long.parseLong(exists(userPassHistoryDao,"select oid from t_userPassHistory where userId='"+user.getOid()+"' and ctime='"+new Timestamp(date.getTime()).toString().replaceFirst("\\.\\d{1,3}",".0")+"'"));
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("currentPassword",passId ); //TODO: currentPassWord->UserPassHistory
        dataMap.put("utime", date);
        dataMap.put("oid", user.getOid());
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
//        dataMap.put("id", user.getId());
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

//    @Override
//    public BaseReturn queryUser(PageModel<TUser> page) {
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
//        return queryPage(page, "t_user", userDao);
//    }

    public String joinPageSql(String searchProperty, String searchValue,String orderProperty, Boolean isDesc, int start, int end) {
        StringBuffer sql = new StringBuffer("select * from t_user");
        if (searchValue != null && !"".equals(searchValue))
            sql.append(" where "+searchProperty+" like '%"+searchValue+"%'");
        if(orderProperty!=null&&!"".equals(orderProperty)) {
            sql.append(" order by " + orderProperty);
            if (isDesc != null && isDesc)
                sql.append(" desc");
        }
        sql.append(" limit " + start + "," + end);
        return sql.toString();
    }
    public BaseReturn queryUser(String searchProperty, String searchValue,String orderProperty, Boolean isDesc, int from, int size){
        try {
            List<TUser> list = userDao.getsBySQL(joinPageSql(searchProperty, searchValue, orderProperty, isDesc, from, size));
            return new BaseReturn(0, list);
        }catch (Exception e) {
            return new BaseReturn("err.user.query.faild", 1);
        }
    }


//    @Override
//    public BaseReturn queryScore(PageModel<TScore> page) {
//
//        return queryPage(page, "t_score", scoreDao);
//    }

    @Override
    public BaseReturn scoreProcess(TUser user, TGift gift, int count) {
        BaseReturn result = new BaseReturn();
        int needScore = gift.getPrice() * count;
        if(user.getStatus().equals("0")){
            //未激活用户
            result.setResult(0);
            result.setErrMessage("err.user.not.active");
              return result;
        }
        if (user.getScore() < needScore) {
            result.setResult(1);
            result.setErrMessage("err.user.score.convert.failed");
        }
        Date date = new Date();
        //更新单个字段
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("score", user.getScore() - needScore);
        dataMap.put("utime", date);
        dataMap.put("oid", user.getOid());
        userDao.update("t_user", dataMap);
        //新增积分记录
        TConvert convert = new TConvert();
//        convert.setId(RandomSymbol.getAllSymbol(16));
        convert.setCtime(date);
        convert.setGiftId(gift.getId());
        convert.setUserId(user.getOid());
        convert.setCount(count);
        convert.setType("fromScore"); //TODO:兑换类型....
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
        long userId = Long.parseLong(exists(userDao, "select oid from t_user where email='" + email + "'"));
        Date date = new Date();
        TUserPassHistory passHistory = new TUserPassHistory(userId, date, password);
        userPassHistoryDao.save(passHistory); //保存密码变更记录
        long oid=Long.parseLong(exists(userPassHistoryDao,"select oid from t_userPassHistory where userId='"+userId+"' and ctime='"+new Timestamp(date.getTime()).toString().replaceFirst("\\.\\d{1,3}",".0")+"'"));
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("currentPassword",oid); //TODO:currentPassword->
        dataMap.put("utime", date);
        dataMap.put("email", email);
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
    public BaseReturn queryPage(PageModel page, String tableName, BaseDao dao,long userId) {
        BaseReturn result = new BaseReturn();
        StringBuffer sql = new StringBuffer("select * from " + tableName+" where user.oid='"+userId+"'");
        if (page.getSearchValue() != null && !page.getSearchValue().toString().trim().equals(""))
            sql.append(" and " + page.getSearchProperty() + "like '%" + page.getSearchValue().toString() + "%'");
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

    public BaseReturn setUserStatus(long userId, String status) {
        BaseReturn result = new BaseReturn();
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("status", status);
        dataMap.put("utime", new Date());
        dataMap.put("oid", userId);
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

    public BaseReturn setUserAddr(long userId, String address) {
        BaseReturn result = new BaseReturn();
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("address", address);
        dataMap.put("utime", new Date());
        dataMap.put("oid", userId);
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
                List<Object> addressList = mailPublishDao.queryAddr("select user.email from t_mailPublish mail join t_userEmail userE on mail.id=userE.emailId join t_user user on userE.userId=user.id where mail.id='" + email.getId() + "'","email");
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

    public BaseReturn subscription(long userId,long emailId) {
        BaseReturn result = new BaseReturn();
//        userEmail.setId(RandomSymbol.getAllSymbol(16));
        try {
//            TMailPublish email = mailPublishDao.getById(emailId);
            TUserEmail userEmail=new TUserEmail(userId,emailId);
            userEMailDao.save(userEmail);
            result.setResult(0);
//            result.setData(user);
        } catch (Exception e) {
            result.setResult(1);
            result.setErrMessage("err.user.subscription.email");
            return result;
        }
        return result;
    }

    public BaseReturn cancelSubscript(long userId,long emailId) {
        BaseReturn result = new BaseReturn();
        try {
            userDao.execBySQL("delete t_userEmail where userId=" + userId + " and emailId=" + emailId);
//            userEMailDao.delete("id", userEmail.getId());
            result.setResult(0);
//            result.setData(userId);
        } catch (Exception e) {
            result.setResult(1);
            return result;
        }
        return result;
    }

    public BaseReturn queryEmail(PageModel<TMailPublish> page, TUser user,long appId) {
//        TAvailableApp availableApp = availableAppDao.getBySQL("select * from t_availableApp where userId='" + user.getOid() + "'");
        if (appId == 1000)
            return queryPage(page, "t_mailPublish", mailPublishDao,user.getOid());
        else if (appId == 10000) //TODO:用户类型待定
            return queryPage(page, "t_mailPublish mail join t_userEmail userE on mail.id=userE.emailId join t_user user on user.id=userE.userId", mailPublishDao,user.getOid());
        else
            return new BaseReturn("err.user.query.emailPublish.verify", 0);
    }

    public TUser getById(Serializable id) {
        return userDao.getById(id);
    }

    @Override
    public BaseReturn delete(List<Object> userList) {
            for(Object user:userList) userDao.execBySQL("delete from t_user where oid='" + user + "'");
        return new BaseReturn(0,null);
    }
}
