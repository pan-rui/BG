package com.qpp.service.album.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qpp.dao.album.PictureInfoDao;
import com.qpp.model.PictureInfo;
import com.qpp.service.album.PictureInfoService;

@Service
public class PictureInfoServiceImpl implements PictureInfoService {
	
	@Resource
	private PictureInfoDao pictureInfoDao;

	@Override
	public Long addPicture(PictureInfo pic) {
		pictureInfoDao.save(pic);
		return pic.getPhotoId();
	}

	@Override
	public PictureInfo getPictureById(long id) {
		return pictureInfoDao.getById(id);
	}
	
	@Override
	public List<PictureInfo> getPicturesByFolderId(long folderId) {
		return pictureInfoDao.getPicturesByFolderId(folderId);
	}

	@Override
	public boolean updatePicture(PictureInfo pic) {
		boolean flag = true;
		try {
			pictureInfoDao.update(pic);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean deletePicture(PictureInfo pic) {
		boolean flag = true;
		try {
			pictureInfoDao.delete(pic);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	public List<PictureInfo> getPicturesByTagIds(long[] tagIds) {
		return pictureInfoDao.getPicturesByTagIds(tagIds);
	}

	@Override
	public List<PictureInfo> getPicturesByUserId(long userId) {
		return pictureInfoDao.getPicturesByUserId(userId);
	}

}
