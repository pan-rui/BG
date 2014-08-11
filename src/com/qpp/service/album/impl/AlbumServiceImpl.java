package com.qpp.service.album.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qpp.dao.album.AlbumDao;
import com.qpp.model.Album;
import com.qpp.service.album.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService {
	
	@Resource
	private AlbumDao albumDao;

	@Override
	public List<Album> getAlbumsByUserId(long userId) {
		return albumDao.getAlbumsByUserId(userId);
	}

	@Override
	public Long addAlbum(Album album) {
		albumDao.save(album);
		Long albumId = album.getId();
		return albumId;
	}

	@Override
	public boolean updateAlbum(Album album) {
		boolean flag = true;
		try {
			albumDao.update(album);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	public Album getAlbumById(long id) {
		return albumDao.getAlbumById(id);
	}

	@Override
	public boolean removeAlbum(Album album) {
		boolean flag = true;
		try {
			albumDao.delete(album);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}
