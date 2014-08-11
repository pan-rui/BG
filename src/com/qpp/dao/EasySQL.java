package com.qpp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("all")
public class EasySQL extends HibernateDaoSupport{

	public double Sum(String Field,String Table) {
    	return this.Sum(Field, Table,"");
    }
	public double Sum(String Field,String Table,String Condition) {
    	final String  queryStr;
    	if (Condition.equals(""))
    		queryStr="select sum("+Field+") as ss from "+Table;
    	else
    		queryStr="select sum("+Field+") as ss from "+Table+" where "+Condition;
    	Double userCount = (Double) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Double doInHibernate(Session session)
                            throws HibernateException, SQLException {
                    	Double ss=0.0;
                        try {
                        	ss=(Double)session.createSQLQuery(queryStr).addScalar("ss",Hibernate.DOUBLE).uniqueResult();
                            }
                        finally {
                            if (session != null)
                                session.close();
                        }
                        return ss==null?0.0:ss;
                    }
                });
        return userCount;    	
      }	
	public long Count(String Table) {
    	return this.Count(Table,"");
    }	
	public long Count(String Table,String Condition) {
    	final String  queryStr;
    	if (Condition.equals(""))
    		queryStr="select count(*) as ss from "+Table;
    	else
    		queryStr="select count(*) as ss from "+Table+" where "+Condition;
    	long userCount = (Long) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Long doInHibernate(Session session)
                            throws HibernateException, SQLException {
                    	Long ss=0L;
                        try {
                        	ss=(Long)session.createSQLQuery(queryStr).addScalar("ss",Hibernate.LONG).uniqueResult();
                            }
                        finally {
                            if (session != null) 
                                session.close();
                        }
                        return ss==null?0L:ss;
                    }
                });
        return userCount;    	
      }	

	public String Concat(String Field,String Table,String Condition) {
    	final String  queryStr;
    	if (Condition.equals(""))
    		queryStr="select group_concat("+Field+") from "+Table;
    	else
    		queryStr="select group_concat("+Field+") from "+Table+" where "+Condition;
    	String userCount = (String) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Connection conn = session.connection();
                        PreparedStatement statement = null;
                        ResultSet rs = null;
                        String cc="";
                        try {
                            statement = conn.prepareStatement(queryStr);
 			               rs = statement.executeQuery();
			               while(rs.next()){
			            	   cc=rs.getString(1);}
                            }
                        finally {
                            if (statement != null)
                                statement.close();
                            if (session != null) {
                                session.close();
                            }
                        }
                        return cc;
                    }
                });
        return userCount;    	
      }
	public boolean exec(String sql) {
    	final String  queryStr;
   		queryStr=sql;
    	String userCount = (String) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Connection conn = session.connection();
                        PreparedStatement statement = null;
                        boolean cc=false;
                        try {
                            statement = conn.prepareStatement(queryStr);
                            statement.execute();
                            cc=true;}
                        finally {
                            if (statement != null)
                                statement.close();
                            if (session != null) {
                                session.close();
                            }
                        }
                        return String.valueOf(cc);
                    }
                });
        return true;    	
      }	

	public int getKeyBySQL(String sql) {
    	final String  queryStr;
   		queryStr=sql;
   		int userCount = (Integer) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Connection conn = session.connection();
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

	
    public String[] ListStr(String Field,String Table,String Condition) {
    	String  retStr=this.Concat(Field, Table, Condition);
        return retStr.split(",");
      }
    public String getServerSet(String key){
    	return this.Concat("value","SERVERSET","col='"+key+"'");
    }
    public void setServerSet(String key,String value){
    	String ret=this.Concat("value","SERVERSET","col='"+key+"'");
    	if (ret==null || ret.equals(""))
    		this.exec("insert into SERVERSET values('"+key+"','"+value+"')");
    	else
    		this.exec("update SERVERSET set value ='"+value+"' where col='"+key+"'");    	
    }

}
