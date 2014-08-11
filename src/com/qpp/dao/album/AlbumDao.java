package com.qpp.dao.album;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.qpp.dao.BaseDao;
import com.qpp.model.Album;

@Repository
public class AlbumDao extends BaseDao<Album>{
	
	private Logger logger = Logger.getLogger(AlbumDao.class);
	
	@SuppressWarnings("unchecked")
	public List<Album> getAlbumsByUserId (long userId) {
		Session session = this.getSession();
		String hql = "select album from Album album left join album.folders folder where album.userId = :userId";
		Query query=session.createQuery(hql);
		query.setLong("userId", userId);
		List<Album> list = query.list();
		logger.info("============" + list);
		return list;
	}
	
	public Album getAlbumById (long id) {
		Album album = getHibernateTemplate().get(Album.class, id);
		return album;
	}
}
