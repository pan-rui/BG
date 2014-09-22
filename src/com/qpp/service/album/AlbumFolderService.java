package com.qpp.service.album;

import java.util.List;

import com.qpp.model.AlbumFolder;

public interface AlbumFolderService {

    public List<AlbumFolder> getAlbumFoldersByUid(long uid);

	public Long addAlbumFolder (AlbumFolder albumFolder);
	
	public AlbumFolder getAlbumFolderById (long albumId);
	
	public boolean updateAlbumFolder (AlbumFolder albumFolder);
	
	public boolean deleteAlbumFolder (AlbumFolder albumFolder);
}
