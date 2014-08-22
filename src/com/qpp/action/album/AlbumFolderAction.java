package com.qpp.action.album;

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
import com.qpp.model.AlbumFolder;
import com.qpp.model.BaseReturn;
import com.qpp.service.album.AlbumFolderService;
import com.qpp.util.JsonTool;

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
	
	@RequestMapping(value="/album/getAlbumFolders/{albumId}", method=RequestMethod.GET)
	@ResponseBody
	public BaseReturn getAlbumFoldersByAlbumId (@PathVariable("albumId")long albumId, HttpServletRequest request, HttpServletResponse response) {
		List<AlbumFolder> albumFolderList = albumFolderService.getAlbumFoldersByAlbumId(albumId);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(JsonTool.jsonByObjectDirecdt(albumFolderList, new String[]{"tags"}));
		for (AlbumFolder albumFolder : albumFolderList) {
			logger.info("folder name:" + albumFolder.getName());
			logger.info("AlbumId:" + albumFolder.getAlbumId());
			logger.info("userId:" + albumFolder.getUserId());
			logger.info("folder createDate:" + albumFolder.getCreateDate());
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/album/addAlbumFolder", method=RequestMethod.POST)
	@ResponseBody
	public BaseReturn createAlbumFolder (HttpServletRequest request, HttpServletResponse response, @RequestBody AlbumFolder albumFolder) {
		Long albumFolderId = albumFolderService.addAlbumFolder(albumFolder);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(albumFolderId);
		logger.info("AlbumFolder Id:" + albumFolderId);
		return baseReturn;
	}
	
	@RequestMapping(value="/album/modifyAlbumFolder/{folderId}", method=RequestMethod.PUT)
	@ResponseBody
	public BaseReturn modifyAlbumFolder (HttpServletRequest request, HttpServletResponse response, @PathVariable long folderId, @RequestBody AlbumFolder albumFolder) {
		AlbumFolder folder = albumFolderService.getAlbumFolderById(folderId);
		boolean flag = false;
		if(folder != null) {
			albumFolder.setId(folderId);
			albumFolder.setCreateDate(folder.getCreateDate());
			flag = albumFolderService.updateAlbumFolder(albumFolder);
		}
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(flag);
		logger.info("AlbumFolder update Success:" + flag);
		return baseReturn;
	}
	
	@RequestMapping(value="/album/removeAlbumFolder/{folderId}", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseReturn removeAlbumFolder (@PathVariable("folderId")long folderId, HttpServletRequest request, HttpServletResponse response) {
		AlbumFolder folder = new AlbumFolder();
		folder.setId(folderId);
		boolean flag = albumFolderService.deleteAlbumFolder(folder);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(flag);
		logger.info("AlbumFolder delete Success:" + flag);
		return baseReturn;
	}
}
