package com.qpp.action.album;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.PictureInfo;
import com.qpp.service.album.PictureInfoService;
import com.qpp.util.FastdfsUtils;
import com.qpp.util.JsonTool;
/**
 * picture managerment
 * @author kevin liu 2014/7/29
 *
 */
@Controller
public class PictureAction extends BaseAction {
	
	private Logger logger = Logger.getLogger(PictureAction.class);
	
	@Autowired
	private PictureInfoService pictureInfoService;
	
	@RequestMapping(value="/product/addPicture")
	@ResponseBody
	public BaseReturn uploadImage (HttpServletRequest request, HttpServletResponse response) throws Exception {
		long userId = 1234;
		float totalSize = getFileSizeByUserId(userId);
		BaseReturn baseReturn = new BaseReturn();
		if (totalSize >= 500f) {
			baseReturn.setErrMessage("your upload files size has more than 500 M.");
			return baseReturn;
		}
		String fileName = "E:\\workspace\\PdfTest\\res\\araraNegative.jpg";
		File file = new File(fileName);
		String fileId = FastdfsUtils.uploadFile(fileName);
		if (fileId != null) {
			PictureInfo pic = new PictureInfo();
			BufferedImage image = ImageIO.read(file);
			int width = image.getWidth();
			int height = image.getHeight();
			pic.setWidth(width);
			pic.setHeight(height);
			pic.setName("bird");
			String photoId = fileId.substring(0, fileId.lastIndexOf("."));
			String photoExp = fileId.substring(fileId.lastIndexOf(".") + 1);
			pic.setPhotoId(photoId);
			pic.setPhotoExp(photoExp);
			pic.setCreateDate(new Date());
			Map<String, String> map = new HashMap<String, String>();
			map.put("photoId", photoId);
			map.put("photoExp", photoExp);
			logger.info(photoId);
			String pictureId = pictureInfoService.addPicture(pic);
			if (pictureId != null && !"".equals(pictureId)) {
				baseReturn.setData(map);
				logger.info("pictureId:" + pictureId);
			} else {
				baseReturn.setErrMessage("Photo save error");
			}
		} else {
			baseReturn.setErrMessage("Photo upload fail");
		}
	    return baseReturn;
	}
	
	@RequestMapping(value="/product/getPicsByFolderId")
	@ResponseBody
	public BaseReturn getPicturesByFolderId (HttpServletRequest request, HttpServletResponse response) {
		long folderId = 1;
		List<PictureInfo> picList = pictureInfoService.getPicturesByFolderId(folderId);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(picList);
		for (PictureInfo picture : picList) {
			logger.info("picId:" + picture.getPhotoId());
			logger.info("picName:" + picture.getName());
			logger.info("picWidth:" + picture.getWidth());
			logger.info("picHeight:" + picture.getHeight());
			logger.info("userId:" + picture.getUserId());
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/product/modifyPicture")
	@ResponseBody
	public BaseReturn modifyPicture (HttpServletRequest request, HttpServletResponse response) {
		PictureInfo pic = pictureInfoService.getPictureById("g1/M00/D7/D1/wKgaD1PhpCaAKCTLAABCLmk2WZU478");
		BaseReturn baseReturn = new BaseReturn();
		if (pic != null) {
			pic.setName("test");
			pic.setModifyDate(new Date());
			boolean flag = pictureInfoService.updatePicture(pic);
			baseReturn.setData(flag);
			logger.info("update picture:" + flag);
		} else {
			baseReturn.setErrMessage("This picture is not exist");
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/product/removePicture")
	@ResponseBody
	public BaseReturn removePicture (HttpServletRequest request, HttpServletResponse response) {
		PictureInfo pic = pictureInfoService.getPictureById("g1/M00/D7/D1/wKgaD1PhpCaAKCTLAABCLmk2WZU478");
		BaseReturn baseReturn = new BaseReturn();
		if (pic != null) {
			boolean flag = pictureInfoService.deletePicture(pic);
			logger.info("delete picture success:" + flag);
			if (flag) {
				String filePath = pic.getPhotoId() + "." + pic.getPhotoExp();
				try {
					FastdfsUtils.deleteFile(filePath);
				} catch (Exception e) {
					logger.error("delete file on fastdfsUtils fail", e);
				}
			}
			baseReturn.setData(flag);
		} else {
			baseReturn.setErrMessage("This picture is not exist");
		}
		return baseReturn;
	}
	
	@SuppressWarnings("unused")
	private float getFileSizeByUserId (long userId) throws Exception {
		List<PictureInfo> picList = pictureInfoService.getPicturesByUserId(userId);
		float totalSize = 0f;
		for (PictureInfo picture : picList) {
			String filePath = picture.getPhotoId() + "." + picture.getPhotoExp();
			float fileSize = FastdfsUtils.getFileSize(filePath);
			totalSize = totalSize + fileSize;
		}
		return totalSize;
	}

	@RequestMapping(value="/product/getPicsByTagIds")
	@ResponseBody
	public BaseReturn getPicturesByTagIds (HttpServletRequest request, HttpServletResponse response) {
		List<Long> tagIds = new ArrayList<Long>();
		tagIds.add(1L);
		tagIds.add(2L);
		List<PictureInfo> picList = pictureInfoService.getPicturesByTagIds(tagIds);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(picList);
//	    String[] attrs = new String[] {"tags"};
		//baseReturn.setData(JsonTool.jsonByObjectDirecdt(picList, null));
		for (PictureInfo picture : picList) {
			logger.info("picId:" + picture.getPhotoId());
			logger.info("picName:" + picture.getName());
			logger.info("picWidth:" + picture.getWidth());
			logger.info("picHeight:" + picture.getHeight());
			logger.info("userId:" + picture.getUserId());
		}
		return baseReturn;
	}
}
