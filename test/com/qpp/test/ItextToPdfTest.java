package com.qpp.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qpp.model.Picture;
import com.qpp.model.Text;
import com.qpp.model.UnitModel;

@RunWith(SpringJUnit4ClassRunner.class)
public class ItextToPdfTest extends BaseTest {
	
	@Autowired
	private PdfGenerateTest test;
	
	@Test
	public void testGeneratePdf() {
		List<Picture> picList = new ArrayList<Picture>();
		Picture pic = new Picture();
		pic.setX(30);
		pic.setY(200);
		pic.setAngle(30);
		pic.setHeight(300);
		pic.setWidth(200);
		pic.setPicUrl("e:/123.png");
		picList.add(pic);
		
		List<Text> textList = new ArrayList<Text>();
		Text text = new Text();
		text.setBold(false);
		text.setItalic(false);
		text.setFontCode("Helvetica");
		text.setColor("#ff0000");
		text.setX(50);
		text.setY(500);
		text.setFontSize(12);
		text.setAngle(270);
		text.setText("你好");
		textList.add(text);
		
		Text text2 = new Text();
		text2.setBold(true);
		text2.setItalic(true);
		text2.setFontCode("Helvetica");
		text2.setColor("#00ff00");
		text2.setX(550);
		text2.setY(500);
		text2.setFontSize(12);
		text2.setAngle(30);
		text2.setText("world");
		textList.add(text2);
		
		UnitModel unitModel = new UnitModel();
		unitModel.setTextList(textList);
		unitModel.setPicList(picList);
		unitModel.setBackPic("d:\\back.jpg");
		List<UnitModel> unitList = new ArrayList<UnitModel>();
		unitList.add(unitModel);
		
		try {
			test.generatePdf(unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
