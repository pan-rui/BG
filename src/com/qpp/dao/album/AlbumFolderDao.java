package com.qpp.dao.album;

import com.qpp.dao.BaseDao;
import com.qpp.model.AlbumFolder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumFolderDao extends BaseDao<AlbumFolder> {
	public AlbumFolderDao() {
		super(AlbumFolder.class);
	}

    public List<AlbumFolder> getAlbumFoldersByUid (long uid) {
        return super.getsByQuery("from AlbumFolder where userId="+uid);
    }
	public List<AlbumFolder> getAlbumFoldersByAlumId (long albumId) {
        return super.getsByQuery("from AlbumFolder where id="+albumId);
	}
}
