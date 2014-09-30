package com.qpp.action.album;

import com.qpp.action.BaseAction;
import com.qpp.model.AlbumFolder;
import com.qpp.model.BaseReturn;
import com.qpp.model.PictureInfo;
import com.qpp.model.TextInfo;
import com.qpp.service.album.AlbumFolderService;
import com.qpp.service.album.PictureInfoService;
import com.qpp.util.FastdfsUtils;
import com.qpp.util.JsonTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.AttributedString;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private AlbumFolderService albumFolderService;

	@RequestMapping(value="/picture/{folderId}", method=RequestMethod.POST)
	@ResponseBody
	public BaseReturn uploadImage (HttpServletRequest request,@PathVariable(value="folderId")long folderId,@RequestParam(value = "file") MultipartFile file) throws Exception {
        BaseReturn baseReturn = new BaseReturn();
        if (!checkFolderOwner(folderId,request)){
            baseReturn.setResult(100);
            baseReturn.setErrMessage(getMessage(request,"data.empty",null));
        }
		String fileName = file.getOriginalFilename();
		String fileId = FastdfsUtils.uploadFile(fileName, file.getInputStream());
		logger.info("UserId:"+super.getUserId(request)+",fileName"+fileName+",fileId"+fileId);
		if (fileId != null) {
			BufferedImage image = ImageIO.read(file.getInputStream());
			PictureInfo pic = getPicInfo(fileId, image);
            pic.setName(fileName);
            pic.setFolderId(folderId);
			Long pictureId = pictureInfoService.addPicture(pic);
			if (pictureId != null && !"".equals(pictureId)) {
				baseReturn.setData(super.getMessage(request, "album.photo.upload.success", new String[]{fileId}));
				logger.info("pictureId:" + pictureId);		 
			} else {
				baseReturn.setErrMessage(super.getMessage(request, "err.album.photo.save.failed", null));
				logger.info("fileId==>>>" + fileId);
			}
		} else {
			baseReturn.setErrMessage(super.getMessage(request, "err.album.photo.upload.failed", null));
		}
	    return baseReturn;
	}
	
	@RequestMapping(value="/picture/{folderId}", method=RequestMethod.GET)
	@ResponseBody
	public BaseReturn getPicturesByFolderId (@PathVariable("folderId")long folderId, HttpServletRequest request, HttpServletResponse response) {
        BaseReturn baseReturn = new BaseReturn();
        if (!checkFolderOwner(folderId,request)){
            baseReturn.setResult(100);
            baseReturn.setErrMessage(getMessage(request,"data.empty",null));
        }
        List<PictureInfo> picList = pictureInfoService.getPicturesByFolderId(folderId);
		//baseReturn.setData(JsonTool.jsonByObjectDirecdt(picList, new String[]{"pictures"}));
        baseReturn.setData(picList);
		return baseReturn;
	}
	
	@RequestMapping(value="/picture/{picId}", method=RequestMethod.PUT)
	@ResponseBody
	public BaseReturn modifyPicture (HttpServletRequest request, HttpServletResponse response, @PathVariable("picId") long picId, @RequestBody PictureInfo pictureInfo) {
        BaseReturn baseReturn = new BaseReturn();
		PictureInfo picture = pictureInfoService.getPictureById(picId);
		if (picture != null) {
			pictureInfo.setPhotoId(picId);
			pictureInfo.setMasterFileId(picture.getMasterFileId());
			pictureInfo.setPhotoExp(picture.getPhotoExp());
			pictureInfo.setCreateDate(picture.getCreateDate());
			pictureInfo.setModifyDate(new Date());
			boolean flag = pictureInfoService.updatePicture(pictureInfo);
			if (flag) {
				baseReturn.setData(super.getMessage(request, "album.photo.modify.success", null));
			} else {
				baseReturn.setData(super.getMessage(request, "album.photo.modify.fail", null));
			}
			logger.info("update picture:" + flag);
		} else {
			baseReturn.setErrMessage(super.getMessage(request, "err.album.photo.not.exist", null));
		}
		return baseReturn;
	}
	
	@RequestMapping(value="/picture/{picId}", method=RequestMethod.DELETE)
	@ResponseBody
	public BaseReturn removePicture ( @PathVariable("picId") long picId, HttpServletRequest request, HttpServletResponse response) {
		BaseReturn baseReturn = new BaseReturn();
		PictureInfo picture = pictureInfoService.getPictureById(picId);
		if (picture != null && picture.getUserId()==super.getUserId(request)) {
			boolean flag = pictureInfoService.deletePicture(picture);
			logger.info("delete picture success:" + flag);
			if (flag) {
				baseReturn.setData(super.getMessage(request, "album.photo.delete.success", null));
				String filePath = picture.getPhotoId() + "." + picture.getPhotoExp();
				try {
					FastdfsUtils.deleteFile(filePath);
				} catch (Exception e) {
					logger.error("delete file on fastdfsUtils fail", e);
				}
			} else {
				baseReturn.setErrMessage(super.getMessage(request, "album.photo.delete.fail", null));
			}
		} else {
			baseReturn.setErrMessage(super.getMessage(request, "err.album.photo.not.exist", null));
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

	@RequestMapping(value="/picsByTagIds/{tagIds}", method=RequestMethod.GET)
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
	
	@RequestMapping(value="/picture/text", method=RequestMethod.POST)
	@ResponseBody
	public BaseReturn textGeneratePic (HttpServletRequest request, HttpServletResponse response, @RequestBody TextInfo textInfo) throws Exception {
		BaseReturn baseReturn = new BaseReturn();
		byte[] buff = null;
		String fileId = null;
		try {
			buff = graphicsGeneration(textInfo, "png");
			fileId = FastdfsUtils.uploadFileBybyte(buff, "png", null);
			if (fileId != null) {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(buff));
				PictureInfo pic = getPicInfo(fileId, image);
				Long pictureId = pictureInfoService.addPicture(pic);
				if (pictureId != null && !"".equals(pictureId)) {
					baseReturn.setData(super.getMessage(request, "album.photo.upload.success", new String[]{fileId}));
					logger.info("pictureId:" + pictureId);
				} else {
					baseReturn.setErrMessage(super.getMessage(request, "err.album.photo.save.failed", null));
					logger.info("fileID:" + fileId);
				}
			} else {
				baseReturn.setErrMessage(super.getMessage(request, "text.generate.photo.fail", null));
			}
		} catch (Exception e) {
			baseReturn.setErrMessage(super.getMessage(request, "text.generate.photo.fail", null));
			logger.info("upload image exception", e);
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
		String masterFileId = fileId.substring(0, fileId.lastIndexOf("."));
		String photoExp = fileId.substring(fileId.lastIndexOf(".") + 1);
		pic.setMasterFileId(masterFileId);
		pic.setPhotoExp(photoExp);
		pic.setCreateDate(new Date());
		return pic;
	}
    public boolean checkFolderOwner(long albumId,HttpServletRequest request){
        AlbumFolder albumFolder = albumFolderService.getAlbumFolderById(albumId);
        BaseReturn baseReturn = new BaseReturn();
        if (albumFolder!=null && albumFolder.getUserId()==super.getUserId(request))
            return true;
        else
            return false;
    }
}
