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
import com.qpp.util.FastJsonUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class SvgTest extends BaseTest {
	
	@Autowired
	private SvgGenereate svgGenereate;
	
	@Test
	public void testGenerateSvg() {
		List<Picture> picList = new ArrayList<Picture>();
		Picture pic = new Picture();
		pic.setX(10);
		pic.setY(15);
		pic.setAngle(0);
		pic.setHeight(300);
		pic.setWidth(200);
		pic.setPicUrl("E:\\workspace\\PdfTest\\res\\arara.jpg");
		
		Picture pic2 = new Picture();
		pic2.setX(15);
		pic2.setY(120);
		pic2.setAngle(0);
		pic2.setHeight(20);
		pic2.setWidth(20);
		pic2.setPicUrl("E:\\workspace\\PdfTest\\res\\ball.png");
		picList.add(pic);
		picList.add(pic2);
		
		List<Text> textList = new ArrayList<Text>();
		Text text = new Text();
		text.setBold(false);
		text.setItalic(true);
		text.setColor("#ff0000");
		text.setFontCode("WebContent/WEB-INF/font/MSYHBD.TTF");
		text.setX(20);
		text.setY(100);
		text.setFontSize(20);
		text.setAngle(0);
		text.setText("你好");
		textList.add(text);
		
		Text text2 = new Text();
		text2.setBold(true);
		text2.setItalic(false);
		text2.setFontCode("WebContent/WEB-INF/font/SIMFANG.TTF");
		text2.setColor("#00ff00");
		text2.setX(50);
		text2.setY(210);
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
			svgGenereate.generateSvg(unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testJsonToSvg () throws Exception {
		
		//一张图片
		String json1 ="{\"picList\":[{\"angle\":0,\"height\":300,\"picUrl\":\"E:\\\\workspace\\\\PdfTest\\\\res\\\\arara.jpg\",\"width\":200,\"x\":10,\"y\":15}],\"textList\":[{\"angle\":0,\"bold\":false,\"color\":\"#ff0000\",\"fontCode\":\"WebContent/WEB-INF/font/MSYHBD.TTF\",\"fontSize\":20,\"italic\":true,\"text\":\"你好\",\"x\":20,\"y\":100},{\"angle\":30,\"bold\":true,\"color\":\"#00ff00\",\"fontCode\":\"WebContent/WEB-INF/font/SIMFANG.TTF\",\"fontSize\":20,\"italic\":false,\"text\":\"world\",\"x\":50,\"y\":200}],\"unitNumber\":0,\"unitPrice\":0}";
		//两张图片
		String json2 = "{\"picList\":[{\"angle\":0,\"height\":300,\"picUrl\":\"E:\\\\workspace\\\\PdfTest\\\\res\\\\arara.jpg\",\"width\":200,\"x\":10,\"y\":15},{\"angle\":0,\"height\":20,\"picUrl\":\"E:\\\\workspace\\\\PdfTest\\\\res\\\\ball.png\",\"width\":20,\"x\":15,\"y\":120}],\"textList\":[{\"angle\":0,\"bold\":false,\"color\":\"#ff0000\",\"fontCode\":\"WebContent/WEB-INF/font/MSYHBD.TTF\",\"fontSize\":20,\"italic\":true,\"text\":\"你好\",\"x\":20,\"y\":100},{\"angle\":30,\"bold\":true,\"color\":\"#00ff00\",\"fontCode\":\"WebContent/WEB-INF/font/SIMFANG.TTF\",\"fontSize\":20,\"italic\":false,\"text\":\"world\",\"x\":50,\"y\":200}],\"unitNumber\":0,\"unitPrice\":0}";
		
		svgGenereate.testSvg(json1);
	}
}
