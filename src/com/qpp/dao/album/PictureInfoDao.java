package com.qpp.dao.album;

import com.qpp.dao.BaseDao;
import com.qpp.model.PictureInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PictureInfoDao extends BaseDao<PictureInfo> {
	
	public PictureInfoDao() {
		super(PictureInfo.class);
	}
	
	public List<PictureInfo> getPicturesByFolderId (long folderId) {
		Session session = this.getSession();
		String hql = "select pic from PictureInfo pic left join pic.tags f where pic.folderId = :folderId";
		Query query = session.createQuery(hql);
		query.setLong("folderId", folderId);
		List<PictureInfo> picList = query.list();
		return picList;
	}
	
	public List<PictureInfo> getPicturesByUserId (long userId) {
		Session session = this.getSession();
		String hql = "select pic from PictureInfo pic where pic.userId = :userId";
		Query query = session.createQuery(hql);
		query.setLong("userId", userId);
		List<PictureInfo> picList = query.list();
		return picList;
	}
	
	public List<PictureInfo> getPicturesByTagIds (long[] tagIds) {
		Session session = this.getSession();
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct pic from PictureInfo pic ");
		if (tagIds != null && tagIds.length >0) {
			sb.append(" join pic.tags tag where (");
			for (int i = 0; i< tagIds.length; i++) {
				sb.append(" tag.id = ").append(tagIds[i]);

				if( i != tagIds.length -1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
		}
		Query query = session.createQuery(sb.toString());
		List<PictureInfo> picList = query.list();
		return picList;
	}
}
