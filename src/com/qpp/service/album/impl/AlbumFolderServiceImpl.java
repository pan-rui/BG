package com.qpp.service.album.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qpp.dao.album.AlbumFolderDao;
import com.qpp.model.AlbumFolder;
import com.qpp.service.album.AlbumFolderService;

@Service
public class AlbumFolderServiceImpl implements AlbumFolderService {
	
	@Resource
	private AlbumFolderDao albumFolderDao;

	@Override
	public List<AlbumFolder> getAlbumFoldersByAlbumId(long albumId) {
		return albumFolderDao.getAlbumFoldersByAlumId(albumId);
	}

	@Override
	public Long addAlbumFolder(AlbumFolder albumFolder) {
		albumFolderDao.save(albumFolder);
		return albumFolder.getId();
	}

	@Override
	public boolean updateAlbumFolder(AlbumFolder albumFolder) {
		boolean flag = true;
		try {
			albumFolderDao.update(albumFolder);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean deleteAlbumFolder(AlbumFolder albumFolder) {
		boolean flag = true;
		try {
			albumFolderDao.delete(albumFolder);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	
}
