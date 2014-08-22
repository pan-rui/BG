package com.qpp.action.album;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.AttributedString;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;















import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.PictureInfo;
import com.qpp.model.TextInfo;
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
	
	@RequestMapping(value="/product/addPicture", method=RequestMethod.POST)
	@ResponseBody
	public BaseReturn uploadImage (HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
//		File file = new File("");
		String fileName = file.getOriginalFilename();
		logger.info("file name===>>>" + fileName);
		BaseReturn baseReturn = new BaseReturn();
		String fileId = FastdfsUtils.uploadFile(fileName, file.getInputStream());
		logger.info(fileId);
		if (fileId != null) {
			BufferedImage image = ImageIO.read(file.getInputStream());
			PictureInfo pic = getPicInfo(fileId, image);
			Map<String, String> map = new HashMap<String, String>();
			map.put("photoId", pic.getPhotoId());
			map.put("photoExp", pic.getPhotoExp());
			String pictureId = pictureInfoService.addPicture(pic);
			if (pictureId != null && !"".equals(pictureId)) {
				baseReturn.setData(map);
				logger.info("pictureId:" + pictureId);
			} else {
				baseReturn.setErrMessage("Photo save error, fileId:" + fileId);
			}
		} else {
			baseReturn.setErrMessage("Photo upload fail");
		}
	    return baseReturn;
	}
	
	@RequestMapping(value="/product/getPicsByFolderId/{folderId}", method=RequestMethod.GET)
	@ResponseBody
	public BaseReturn getPicturesByFolderId (@PathVariable("folderId")long folderId, HttpServletRequest request, HttpServletResponse response) {
		List<PictureInfo> picList = pictureInfoService.getPicturesByFolderId(folderId);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(JsonTool.jsonByObjectDirecdt(picList, new String[]{"pictures"}));
		for (PictureInfo picture : picList) {
			logger.info("picId:" + picture.getPhotoId());
			logger.info("picName:" + picture.getName());
			logger.info("picWidth:" + picture.getWidth());
			logger.info("picHeight:" + picture.getHeight());
			logger.info("userId:" + picture.getUserId());
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/product/modifyPicture", method=RequestMethod.PUT)
	@ResponseBody
	public BaseReturn modifyPicture (HttpServletRequest request, HttpServletResponse response, @RequestBody PictureInfo pictureInfo) {
		BaseReturn baseReturn = new BaseReturn();
		boolean flag = pictureInfoService.updatePicture(pictureInfo);
		baseReturn.setData(flag);
		logger.info("update picture:" + flag);
		return baseReturn;
	}
	
	@RequestMapping(value="/product/removePicture", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseReturn removePicture (HttpServletRequest request, HttpServletResponse response, @RequestBody PictureInfo pictureInfo) {
		BaseReturn baseReturn = new BaseReturn();
		boolean flag = pictureInfoService.deletePicture(pictureInfo);
		logger.info("delete picture success:" + flag);
		if (flag) {
			String filePath = pictureInfo.getPhotoId() + "." + pictureInfo.getPhotoExp();
			try {
				FastdfsUtils.deleteFile(filePath);
			} catch (Exception e) {
				logger.error("delete file on fastdfsUtils fail", e);
			}
		}
		baseReturn.setData(flag);
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

	@RequestMapping(value="/product/getPicsByTagIds/{tagIds}", method=RequestMethod.GET)
	@ResponseBody
	public BaseReturn getPicturesByTagIds (@PathVariable("tagIds")long[] tagIds, HttpServletRequest request, HttpServletResponse response) {
		logger.info("tagIds===>>" + tagIds.length);
		List<PictureInfo> picList = pictureInfoService.getPicturesByTagIds(tagIds);
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(JsonTool.jsonByObjectDirecdt(picList, new String[]{"pictures"}));
		for (PictureInfo picture : picList) {
			logger.info("picId:" + picture.getPhotoId());
			logger.info("picName:" + picture.getName());
			logger.info("picWidth:" + picture.getWidth());
			logger.info("picHeight:" + picture.getHeight());
			logger.info("userId:" + picture.getUserId());
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/product/text", method=RequestMethod.POST)
	@ResponseBody
	public BaseReturn textGeneratePic (HttpServletRequest request, HttpServletResponse response, @RequestBody TextInfo textInfo) throws Exception {
		BaseReturn baseReturn = new BaseReturn();
		byte[] buff = null;
		String fileId = null;
		try {
			buff = graphicsGeneration(textInfo, "png");
			fileId = FastdfsUtils.uploadFileBybyte(buff, "png", null);
		} catch (Exception e) {
			baseReturn.setErrMessage(e.toString());
			logger.info("upload image exception", e);
		}
		
		if (fileId != null) {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(buff));
			PictureInfo pic = getPicInfo(fileId, image);
			Map<String, String> map = new HashMap<String, String>();
			map.put("photoId", pic.getPhotoId());
			map.put("photoExp", pic.getPhotoExp());
			String pictureId = pictureInfoService.addPicture(pic);
			if (pictureId != null && !"".equals(pictureId)) {
				baseReturn.setData(map);
				logger.info("pictureId:" + pictureId);
			} else {
				baseReturn.setErrMessage("Photo save error, fileId:" + fileId);
			}
		} else {
			baseReturn.setErrMessage("Photo upload fail");
		}
		return baseReturn;
	}
	
	private byte[] graphicsGeneration(TextInfo textInfo, String fileExt) throws Exception {
		int width = Integer.parseInt(textInfo.getWidth());
		int height = Integer.parseInt(textInfo.getHeight());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(string2Color(textInfo.getColor()));
		int style = 0;
		if (textInfo.getIsBold()) {
			style = 1;
			if (textInfo.getIsItalic()) {
				style = 1|2;
			} 
		} else {
			if (textInfo.getIsItalic()) {
				style = 2;
			} 
		}
		
		int fontSize = Integer.parseInt(textInfo.getFontSize());
		Font font = new Font(textInfo.getFontFamily(), style, fontSize);
		graphics.setFont(font);
		AttributedString as = new AttributedString(textInfo.getText());
		as.addAttribute(TextAttribute.FONT, font);
		if (textInfo.getTextDecoration() != null && !"".equals(textInfo.getTextDecoration())) {
			as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		}
		
		FontMetrics metrics = graphics.getFontMetrics(); 
		
		int textX = 0;
		int textY = metrics.getHeight();
		System.out.println(metrics.stringWidth(textInfo.getText()));
		System.out.println(textY);
		
		if (textInfo.getTextAlign() != null) {
			if ("left".equalsIgnoreCase(textInfo.getTextAlign())) {
				textX = 0;
			} else if ("right".equalsIgnoreCase(textInfo.getTextAlign())) {
				textX = Integer.parseInt(textInfo.getWidth()) -  metrics.stringWidth(textInfo.getText());
			} else {
				textX = (Integer.parseInt(textInfo.getWidth()) -  metrics.stringWidth(textInfo.getText())) / 2;
			}
		}
		graphics.rotate(60, textX, textY);
		graphics.drawString(as.getIterator(), textX, textY);
		graphics.dispose();
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ImageOutputStream imOut = ImageIO.createImageOutputStream(bs); 
        ImageIO.write(image, fileExt,imOut);
        byte[] buff = bs.toByteArray();
        imOut.close();
        bs.close();
        return buff;
	}
	
	private Color string2Color(String str) {  
        int i = Integer.parseInt(str.substring(1), 16);  
        return new Color(i);  
    }  
	
	private PictureInfo getPicInfo (String fileId, BufferedImage image) {
		PictureInfo pic = new PictureInfo();
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
		return pic;
	}
}
