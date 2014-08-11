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
import com.qpp.model.AlbumFolder;
import com.qpp.model.BaseReturn;
import com.qpp.service.album.AlbumFolderService;

/**
 * album folder managerment
 * @author kevin liu 2014/7/31
 *
 */
@Controller
public class AlbumFolderAction  extends BaseAction  {

	private Logger logger = Logger.getLogger(AlbumFolderAction.class);
	
	@Autowired
	private AlbumFolderService albumFolderService;
	
	@RequestMapping(value="/album/getAlbumFolders")
	@ResponseBody
	public BaseReturn getAlbumFoldersByAlbumId (HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("albumId");
		long albumId = 0;
		if (id != null) {
			albumId = Long.parseLong(id);
		} else {
			albumId = 1;
		}
		List<AlbumFolder> albumFolderList = albumFolderService.getAlbumFoldersByAlbumId(albumId);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(albumFolderList);
		for (AlbumFolder albumFolder : albumFolderList) {
			logger.info("folder name:" + albumFolder.getName());
			logger.info("AlbumId:" + albumFolder.getAlbumId());
			logger.info("userId:" + albumFolder.getUserId());
			logger.info("folder createDate:" + albumFolder.getCreateDate());
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/album/addAlbumFolder")
	@ResponseBody
	public BaseReturn createAlbumFolder (HttpServletRequest request, HttpServletResponse response) {
		AlbumFolder folder = new AlbumFolder();
		folder.setName("ssss");
		folder.setAlbumId(1L);
		folder.setCreateDate(new Date());
		folder.setUserId(null);
		Long albumFolderId = albumFolderService.addAlbumFolder(folder);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(albumFolderId);
		logger.info("AlbumFolder Id:" + albumFolderId);
		return baseReturn;
	}
	
	@RequestMapping(value="/album/modifyAlbumFolder")
	@ResponseBody
	public BaseReturn modifyAlbumFolder (HttpServletRequest request, HttpServletResponse response) {
		AlbumFolder folder = new AlbumFolder();
		folder.setId(5L);
		folder.setName("tttt");
		boolean flag = albumFolderService.updateAlbumFolder(folder);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(flag);
		logger.info("AlbumFolder update Success:" + flag);
		return baseReturn;
	}
	
	@RequestMapping(value="/album/removeAlbumFolder")
	@ResponseBody
	public BaseReturn removeAlbumFolder (HttpServletRequest request, HttpServletResponse response) {
		AlbumFolder folder = new AlbumFolder();
		folder.setId(5L);
		boolean flag = albumFolderService.deleteAlbumFolder(folder);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(flag);
		logger.info("AlbumFolder delete Success:" + flag);
		return baseReturn;
	}
}
