package com.qpp.action.album;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qpp.action.BaseAction;
import com.qpp.model.Album;
import com.qpp.model.BaseReturn;
import com.qpp.service.album.AlbumService;

/**
 * photo managerment
 * @author kevin liu 2014/7/29
 *
 */
@Controller
public class AlbumAction  extends BaseAction {
	
	private Logger logger = Logger.getLogger(AlbumAction.class);
	
	@Autowired
	private AlbumService albumService;
	
	@RequestMapping(value="/product/getUserAlbums")
	@ResponseBody
	public BaseReturn getUserAlbums (HttpServletRequest request, HttpServletResponse response) {
		String id = (String) request.getParameter("userId");
		long userId = 0;
		if (id != null) {
			userId = Long.parseLong(id);
		} else {
			userId = 2345;
		}
		List<Album> albumList = albumService.getAlbumsByUserId(userId);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(albumList);
		for (Album album : albumList) {
			logger.info("albumId:" + album.getId());
			logger.info("albunName:" + album.getName());
			logger.info("albumType:" + album.getType());
			logger.info("albumcreateDate:" + album.getCreateDate());
			logger.info("album folder size:" + album.getFolders().size());
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/product/createAlbum")
	@ResponseBody
	public BaseReturn createUserAlbum (HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("userId");
		long userId = 0;
		if (id != null) {
			userId = Long.parseLong(id);
		} else {
			userId = 2345;
		}
		
		Album album = new Album();
		album.setName("test");
		album.setUserId(userId);
		album.setCreateDate(new Date());
		Long albumid = albumService.addAlbum(album);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(albumid);
		logger.info("=============albumId:" + albumid);
		return baseReturn;
	}
	
	@RequestMapping(value="/product/modifyAlbum")
	@ResponseBody
	public BaseReturn modifyAlbum (HttpServletRequest request, HttpServletResponse response) {
		Album album = albumService.getAlbumById(4);
		album.setName("test1");
		album.setModifyDate(new Date());
		boolean flag = albumService.updateAlbum(album);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(flag);
		logger.info("album update:" + flag);
		return baseReturn;
	}
	
	@RequestMapping(value="/product/removeAlbum")
	@ResponseBody
	public BaseReturn removeAlbum (HttpServletRequest request, HttpServletResponse response) {
		Album album = albumService.getAlbumById(4);
		boolean flag = albumService.removeAlbum(album);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(flag);
		logger.info("album remove:" + flag);
		return baseReturn;
	}
}
