package com.qpp.service.album;

import java.util.List;

import com.qpp.model.Album;

public interface AlbumService {
	
	public List<Album> getAlbumsByUserId (long userId);
	
	public Long addAlbum (Album album);
	
	public boolean updateAlbum (Album album);
	
	public Album getAlbumById (long id);
	
	public boolean removeAlbum (Album album);
		
}
