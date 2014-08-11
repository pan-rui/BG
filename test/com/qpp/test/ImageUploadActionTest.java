package com.qpp.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qpp.action.image.ImageUploadAction;


@RunWith(SpringJUnit4ClassRunner.class)
public class ImageUploadActionTest extends BaseTest {

	@Autowired
	private ImageUploadAction imageUploadAction;
	
	private MockMultipartHttpServletRequest  request = new MockMultipartHttpServletRequest ();
	
	private MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Test
	public void testAddImage() throws FileNotFoundException, IOException {
		request.addFile(new MockMultipartFile("imagePic", "123.png", "image/png", new FileInputStream("e:\\123.png")));
		imageUploadAction.uploadImage(request, response);
	}
}
