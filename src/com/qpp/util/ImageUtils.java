package com.qpp.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.log4j.Logger;

/**
 * image handle tools
 * @author Kevin Liu 2014/8/18
 *
 */
public class ImageUtils {
	private static Logger logger = Logger.getLogger(ImageUtils.class);
	
	public static byte[] resize (byte[] file_buff, String fileExtName, int width, int height) {
		byte[] buff = null;
		try {
			InputStream is = new ByteArrayInputStream(file_buff);
			BufferedImage originalImage = ImageIO.read(is);
			double scale = 0;
			if (originalImage.getWidth() > originalImage.getHeight()) {
				scale = (new Integer(width)).doubleValue() / originalImage.getWidth();
				height = (int)(scale * originalImage.getHeight());
			} else {
				scale = (new Integer(height)).doubleValue() / originalImage.getHeight();
				width = (int)(scale * originalImage.getWidth());
			}
			BufferedImage tag= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);     
			tag.getGraphics().drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			tag.getGraphics().dispose();
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs); 
            ImageIO.write(tag, fileExtName,imOut);
            buff = bs.toByteArray();
            imOut.close();
            bs.close();
		} catch (IOException e) {
			logger.error("read image fails", e);
		}
		return buff;
	}
}
