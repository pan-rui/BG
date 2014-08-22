package com.qpp.action.album;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qpp.action.BaseAction;
import com.qpp.model.Album;
import com.qpp.model.BaseReturn;
import com.qpp.service.album.AlbumService;
import com.qpp.util.JsonTool;

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
	
	@RequestMapping(value="/product/getUserAlbums/{userId}",method= RequestMethod.GET)
	@ResponseBody
	public BaseReturn getUserAlbums (@PathVariable("userId")long userId,HttpServletRequest request, HttpServletResponse response) {
		logger.info("userId==>" + userId);
		List<Album> albumList = albumService.getAlbumsByUserId(userId);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(JsonTool.jsonByObjectDirecdt(albumList, new String[]{"pictures"}));
		for (Album album : albumList) {
			logger.info("albumId:" + album.getId());
			logger.info("albunName:" + album.getName());
			logger.info("albumType:" + album.getType());
			logger.info("albumcreateDate:" + album.getCreateDate());
			logger.info("album folder size:" + album.getFolders().size());
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/product/createAlbum", method=RequestMethod.POST)
	@ResponseBody
	public BaseReturn createUserAlbum (HttpServletRequest request, HttpServletResponse response, @RequestBody Album album) {
		Long albumid = albumService.addAlbum(album);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(albumid);
		logger.info("=============albumId:" + albumid);
		return baseReturn;
	}
	
	@RequestMapping(value="/product/modifyAlbum/{albumId}", method=RequestMethod.PUT)
	@ResponseBody
	public BaseReturn modifyAlbum (HttpServletRequest request, HttpServletResponse response, @PathVariable long albumId, @RequestBody Album album) {
		Album albumOld = albumService.getAlbumById(albumId);
		boolean flag = false;
		if (albumOld != null) {
			album.setId(albumId);
			album.setCreateDate(albumOld.getCreateDate());
			flag = albumService.updateAlbum(album);
		}
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(flag);
		logger.info("album update:" + flag);
		return baseReturn;
	}
	
	@RequestMapping(value="/product/removeAlbum/{albumId}", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseReturn removeAlbum (@PathVariable("albumId")long albumId, HttpServletRequest request, HttpServletResponse response) {
		Album album = albumService.getAlbumById(albumId);
		boolean flag = albumService.removeAlbum(album);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(flag);
		logger.info("album remove:" + flag);
		return baseReturn;
	}
}
