package com.qpp.action.album;

import com.qpp.action.BaseAction;
import com.qpp.model.AlbumFolder;
import com.qpp.model.BaseReturn;
import com.qpp.service.album.AlbumFolderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value="/album", method=RequestMethod.GET)
    @ResponseBody
    public BaseReturn getAlbumFolders (HttpServletRequest request) {
        long uid=super.getUserId(request);
        List<AlbumFolder> albumFolderList = albumFolderService.getAlbumFoldersByUid(uid);
        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setData(albumFolderList);
        return baseReturn;
    }

	@RequestMapping(value="/album/{albumId}", method=RequestMethod.GET)
	@ResponseBody
	public BaseReturn getAlbumFoldersByAlbumId (@PathVariable("albumId")long albumId, HttpServletRequest request) {
		AlbumFolder albumFolder = albumFolderService.getAlbumFolderById(albumId);
		BaseReturn baseReturn = new BaseReturn();
        if (albumFolder!=null && albumFolder.getUserId()==super.getUserId(request))
		    baseReturn.setData(albumFolder);
        else{
            baseReturn.setResult(100);
            baseReturn.setErrMessage(getMessage(request,"data.empty",null));
        }
		return baseReturn;
	}
	
	@RequestMapping(value="/album", method=RequestMethod.POST)
	@ResponseBody
	public BaseReturn createAlbumFolder (HttpServletRequest request, @RequestBody AlbumFolder albumFolder) {
        albumFolder.setUserId(super.getUserId(request));
        albumFolder.setCreateDate(new Date());
		Long albumFolderId = albumFolderService.addAlbumFolder(albumFolder);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(albumFolderId);
		return baseReturn;
	}
	
	@RequestMapping(value="/album/{albumId}", method=RequestMethod.PUT)
	@ResponseBody
	public BaseReturn modifyAlbumFolder (HttpServletRequest request,@PathVariable long albumId, @RequestBody AlbumFolder albumFolder) {
		AlbumFolder folder = albumFolderService.getAlbumFolderById(albumId);
		BaseReturn baseReturn = new BaseReturn();
		if(folder != null && folder.getUserId()==super.getUserId(request)) {
			albumFolder.setId(albumId);
            albumFolder.setUserId(super.getUserId(request));
			albumFolder.setModifyDate(folder.getCreateDate());
			albumFolderService.updateAlbumFolder(albumFolder);
    		baseReturn.setData(super.getMessage(request, "album.folder.modify.success", null));
		}else{
            baseReturn.setResult(100);
            baseReturn.setErrMessage(getMessage(request,"data.empty",null));
        }
		return baseReturn;
	}

	@RequestMapping(value="/album/{albumId}", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseReturn removeAlbumFolder (@PathVariable long albumId, HttpServletRequest request) {
        AlbumFolder folder = albumFolderService.getAlbumFolderById(albumId);
        BaseReturn baseReturn = new BaseReturn();
        if(folder != null && folder.getUserId()==super.getUserId(request)) {
            albumFolderService.deleteAlbumFolder(folder);
            baseReturn.setData(super.getMessage(request, "album.folder.modify.success", null));
        }else{
            baseReturn.setResult(100);
            baseReturn.setErrMessage(getMessage(request,"data.empty",null));
        }
		return baseReturn;
	}
}
