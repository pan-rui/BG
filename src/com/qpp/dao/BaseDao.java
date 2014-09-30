package com.qpp.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport{
    private Class priClass;
    @Autowired
    protected HibernateTemplate hibernateTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ComboPooledDataSource dataSource;
    public BaseDao() {

    }

    public BaseDao(Class poClass) {
        this.priClass = poClass;
    }

    public void save(T po) {
        hibernateTemplate.save(po);
    }
    public void save(String name,T po) {
        hibernateTemplate.save(name,po);
    }

    public String getTableByClass(Class cls){
        SessionFactory factory = getHibernateTemplate().getSessionFactory();
        AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(cls);
        return classMetadata.getTableName();
    }

    public void delete(T po) {
        hibernateTemplate.delete(po);
    }

    public void update(T po) {
        hibernateTemplate.update(po);
    }
    public void update(String name,T po) {
        hibernateTemplate.update(name, po);
    }

    public List<T> getAll(){
        return hibernateTemplate.loadAll(priClass);
    }
    public List<T> getAll(Class pclass){
        return hibernateTemplate.loadAll(pclass);
    }
    public List<T> getAllByPage(Class pclass,int from,int size){
        return getsByQueryPage("from "+pclass.getSimpleName(),from,size);
        //return hibernateTemplate.loadAll(pclass);
    }

    public List<T> getsByCriteria(DetachedCriteria criteria){
        return hibernateTemplate.findByCriteria(criteria);
    }



    public T getById(final Serializable id) {
        return this.getById(id, priClass);
    }
    public T getById(final Serializable id, final Class poClass) {
        return (T) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                return session.get(poClass, id);
            }
        });
    }

    
    public T getByQuery(final String hqlString) {
        return (T) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hqlString);
                return query.uniqueResult();
            }
        }
        );
    }

    
    public List<T> getsByQuery(final String hqlString) {
        return (List<T>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(hqlString);
                return query.list();
            }
        }
        );
    }

    public List<T> getsByQueryPage(final String sqlString, final int start,final int end) {
        return (List<T>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sqlString);
                if (start>=0)
                    query.setFirstResult(start);
                if (end>=0)
                    query.setMaxResults(end);
                return query.list();
            }
        }
        );
    }

    public List<T> getsByExample(T obj) {
        return (List<T>) hibernateTemplate.findByExample(obj);
    }


    public Object getObjectBySQL(final String sqlString) {
        return (Object) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sqlString);
                return query.uniqueResult();
            }
        }
        );
    }
    public Object getObjectByQuery(final String sqlString) {
        return (Object) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sqlString);
                return query.uniqueResult();
            }
        }
        );
    }

    public T execBySQL(final String sqlString) {
        return (T) hibernateTemplate.execute(new HibernateCallback() {
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
        return (T) hibernateTemplate.execute(new HibernateCallback() {
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
    public List<T> getsBySQL(final String sqlString, final Class poClass) {
        return (List<T>) hibernateTemplate.execute(new HibernateCallback() {
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
        int userCount = (Integer) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Connection conn = SessionFactoryUtils.getDataSource(hibernateTemplate.getSessionFactory()).getConnection();
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
    public void update(String tableName, Map data)
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
                if(i == len-1)
                {
                    col.append(key);
                    col.append("=?");
                }
                else if(i<len-1)
                {
                    col.append(key);
                    col.append("=?,");
                }else
                    col.append(" where "+key+"=?");
                i++;
            }
            sql.append("update ");
            sql.append(tableName);
            sql.append(" set ");
            sql.append(col.toString());
//            sql.append(" where ?=?");
            //System.out.println(sql);
            stmt = conn.prepareStatement(sql.toString());
            i = 1;
            for(Object key : data.keySet()){
                stmt.setObject(i, data.get(key));
                i++;
            }
//            stmt.setString(i, id);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        finally
        {
            this.close(conn,stmt,null);
        }
    }

    public int delete(String property, Object value) {
        List<T> list=hibernateTemplate.findByNamedQuery(property,value);
        hibernateTemplate.deleteAll(list);
        return  list.size();
    }

}
