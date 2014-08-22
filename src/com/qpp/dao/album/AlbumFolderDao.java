package com.qpp.dao.album;

import com.qpp.dao.BaseDao;
import com.qpp.model.AlbumFolder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumFolderDao extends BaseDao<AlbumFolder> {
	
	public List<AlbumFolder> getAlbumFoldersByAlumId (long albumId) {
		
		Session session = this.getSession();
		String hql = "select af from AlbumFolder af left join af.children ch where af.albumId=:albumId";
		Query query = session.createQuery(hql);
		query.setLong("albumId", albumId);
		List<AlbumFolder> list = query.list();
		return list;
	}
	
	public AlbumFolder getAlbumFolderById (long id) {
		AlbumFolder albumFolder = getHibernateTemplate().get(AlbumFolder.class, id);
		return albumFolder;
	}
}
