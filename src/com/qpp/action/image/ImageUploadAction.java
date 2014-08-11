package com.qpp.action.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.qpp.action.BaseAction;

@Controller
public class ImageUploadAction extends BaseAction {
	
	public static final Logger LOGGER = Logger.getLogger(ImageUploadAction.class);
	
	@RequestMapping("/test/imageIndex")
	public String uploadImageIndex (HttpServletRequest request, HttpServletResponse response) {
		return "/test/imageIndex";
	}
	
	/**
	 * upload image file
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/test/addImage")
	public void uploadImage (HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		MultipartFile file = multipartRequest.getFile("imagePic");
		InputStream input = null;
		FileOutputStream outputSream = null;
		LOGGER.info("============contentType:" + file.getContentType());
		LOGGER.info("============originalFilename:" + file.getOriginalFilename());
		String message = "";
		//To determine the file type
		if ("image".equals(file.getContentType().split("/")[0])) {
			File path = new File("d:\\image");
			if(!path.exists()) {
				path.mkdir();
			}
			File uploadFile = new File("d:\\image\\" + file.getOriginalFilename());
			if(uploadFile.exists() && uploadFile.isFile()) {
				message = "The " + file.getOriginalFilename() + " has exist";
			} else {
				try {
					outputSream = new FileOutputStream(new File("d:\\image\\" + file.getOriginalFilename()));
					input = file.getInputStream();
					int len = 0;  
					byte[] buf = new byte[1024];  
					while ((len = input.read(buf, 0, 1024)) != -1) {  
						outputSream.write(buf, 0, len);  
					}  
					message = "upload " + file.getOriginalFilename() + " success";
				} catch (IOException e) {
					LOGGER.error("read file error:", e);
				} finally {
					try {
						outputSream.close();
						input.close();
					} catch (IOException e) {
						LOGGER.error("outputStream or input can't close:", e);
					}
				}
			}
		} else {
			message = "The " + file.getOriginalFilename() + " is not picture";
		}
		
		response.setContentType("application/json; charset=UTF-8");
		try {
			response.getOutputStream().print(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
