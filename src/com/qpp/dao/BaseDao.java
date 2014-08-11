package com.qpp.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qpp.service.market.PaypalUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport {
    private Class priClass;

    @Resource
    private ComboPooledDataSource dataSource;
    public BaseDao() {
    }
    public BaseDao(Class poClass) {
        this.priClass = poClass;
    }

    public void save(T po) {
        getHibernateTemplate().save(po);
    }

    public void delete(T po) {
        getHibernateTemplate().delete(po);
    }

    public void update(T po) {
        getHibernateTemplate().update(po);
    }

    
    public T getById(final Serializable id) {
        return this.getById(id,priClass);
    }
    protected T getById(final Serializable id, final Class poClass) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                return session.get(poClass, id);
            }
        });
    }

    
    public T getByQuery(final String hqlString) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hqlString);
                return query.uniqueResult();
            }
        }
        );
    }

    
    public List<T> getsByQuery(final String hqlString) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hqlString);
                return query.list();
            }
        }
        );
    }

    public List<T> getsByQueryPage(final String sqlString, final int start,final int end) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sqlString).setFirstResult(start).setMaxResults(end);
                return query.list();
            }
        }
        );
    }

    public List<T> getsByExample(T obj) {
        return (List<T>) getHibernateTemplate().findByExample(obj);
    }


    public Object getObjectBySQL(final String sqlString) {
        return (Object) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sqlString);
                return query.uniqueResult();
            }
        }
        );
    }
    public Object getObjectByQuery(final String sqlString) {
        return (Object) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sqlString);
                return query.uniqueResult();
            }
        }
        );
    }

    public T execBySQL(final String sqlString) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.createSQLQuery(sqlString).executeUpdate();
                return null;
            }
        });
    }

    
    public T getBySQL(final String sqlString) {
        return this.getBySQL(sqlString,priClass);
    }
    protected T getBySQL(final String sqlString, final Class poClass) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sqlString).addEntity(poClass);
                return query.uniqueResult();
            }
        }
        );
    }

    
    public List<T> getsBySQL(final String sqlString) {
        return this.getsBySQL(sqlString,priClass);
    }
    protected List<T> getsBySQL(final String sqlString, final Class poClass) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sqlString).addEntity(poClass);
                return query.list();
            }
        }
        );
    }
    
    public int getKeyBySQL(String sql) {
        final String  queryStr;
        queryStr=sql;
        int userCount = (Integer) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Connection conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
                        Statement statement =conn.createStatement();
                        int retval=0;
                        try {
                            statement.executeUpdate(queryStr,Statement.RETURN_GENERATED_KEYS);
                            ResultSet rs=statement.getGeneratedKeys();
                            rs.next();
                            retval=rs.getInt(1);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            if (statement != null)
                                statement.close();
                            if (session != null) {
                                session.close();
                            }
                        }
                        return retval;
                    }
                });
        return userCount;
    }

    // jdbc:mysql://<host>:<port>/<database_name>
//    public boolean updateOnMap(String tableName, Map<String, Object> newData, String cond) {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        StringBuffer sql = new StringBuffer("update " + tableName + " set");
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://192.168.26.15:3306/test", "root", "!root@123456");
//            conn.setReadOnly(false);
////            stmt=conn.prepareStatement()
//            Set<Map.Entry<String, Object>> entry = newData.entrySet();
//            Iterator<Map.Entry<String, Object>> iterator = entry.iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, Object> KV = iterator.next();
//                if (!"".equals(KV.getValue()))
//                    sql.append(" " + KV.getKey() + "=" + KV.getValue());
//            }
//            sql.append(" " + cond);
//            pstmt = conn.prepareStatement(sql.toString());
//            int result = pstmt.executeUpdate();
//            conn.commit();
//            return result > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            logger.error("Update 失败，失败的SQL为:\t" + sql.toString());
//            try {
//                conn.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//        } finally {
//            try {
//                pstmt.close();
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return false;
//    }

    public boolean insert(T t) {
        StringBuffer sql = new StringBuffer("insert into t_" + getTableName(t.getClass().getName()));
        boolean result=false;
        StringBuffer fis = new StringBuffer(" (");
        StringBuffer vus = new StringBuffer(" (");
        Connection conn = getConnetion();
        PreparedStatement pstmt=null;
        Field[] fields = t.getClass().getDeclaredFields();
        int i = 0;
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Class type =field.getType();
                Object value=field.get(t);
                if (i != fields.length - 1) {
                    if (value != null) {
                        fis.append(field.getName() + ",");
                        if (type.isInstance(""))
                            vus.append("'" + value + "',");
                        else if(type.isInstance(new java.util.Date()))
                            vus.append("'"+PaypalUtil.dateFormat((java.util.Date)value) + "',");
                        else
                            vus.append(value + ",");
                    }
                } else {
                    if (!fis.toString().contains(")")) {
                        if(value!=null) {
                            fis.append(field.getName() + ")");
                            if (type.isInstance(""))
                                vus.append("'" + value + "')");
                            else if (type.isInstance(new java.util.Date()))
                                vus.append("'" + PaypalUtil.dateFormat((java.util.Date) value) + "')");
                            else
                                vus.append(value + ")");
                        }else{
                            fis=new StringBuffer(fis.substring(0,fis.length()-1)).append(")");
                            vus=new StringBuffer(vus.substring(0,vus.length()-1)).append(")");
                        }
                    }
                }

                i++;
            }
            sql.append(fis.toString()).append(" values").append(vus);
            pstmt=conn.prepareStatement(sql.toString());
            result=pstmt.execute();
            conn.commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            close(conn,pstmt,null);
        }
        return result;
    }

//    public List<T> query(){}

    public String getTableName(String className) {
        int index=className.lastIndexOf('.')+1;
        return className.substring(index,index+1).toLowerCase() + className.substring(index+1);
    }

    public Connection getConnetion() {
        Connection conn = null;
        try {
            Class.forName(dataSource.getDriverClass());
            conn = DriverManager.getConnection(dataSource.getJdbcUrl(), dataSource.getUser(), dataSource.getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void close(Connection conn, Statement pstmt, ResultSet rs) {

        try {
            if (conn != null)
                conn.close();
            if (pstmt != null)
                pstmt.close();
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(String tableName, Map data, String id)throws Exception
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        StringBuffer sql = new StringBuffer();
        try
        {
            conn = getConnetion();
            conn.setReadOnly(false);
            StringBuffer col = new StringBuffer();
            int i=1;
            int len = data.size();
            for(Object key : data.keySet())
            {
                if(i == len)
                {
                    col.append(key);
                    col.append("=?");
                }
                else
                {
                    col.append(key);
                    col.append("=?,");
                }
                i++;
            }
            sql.append("update ");
            sql.append(tableName);
            sql.append(" set ");
            sql.append(col.toString());
            sql.append(" where id=?");
            //System.out.println(sql);
            stmt = conn.prepareStatement(sql.toString());
            i = 1;
            for(Object key : data.keySet()){
                stmt.setObject(i, data.get(key));
                i++;
            }
            stmt.setString(i, id);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
        finally
        {
            this.close(conn,stmt,null);
        }
    }

    public int delete(String property, Object value) {
        List<T> list=getHibernateTemplate().findByNamedQuery(property,value);
        getHibernateTemplate().deleteAll(list);
        return  list.size();
    }

}
