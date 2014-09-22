package com.qpp.service.album;

import java.util.List;

import com.qpp.model.PictureInfo;

public interface PictureInfoService {
	
	public Long addPicture (PictureInfo pic);
	
	public PictureInfo getPictureById (long id);
	
	public List<PictureInfo> getPicturesByFolderId (long folderId);
	
	public List<PictureInfo> getPicturesByUserId (long userId);
	
	public boolean updatePicture (PictureInfo pic);
	
	public boolean deletePicture (PictureInfo pic);
	
	public List<PictureInfo> getPicturesByTagIds (long[] tagIds);
	
}
