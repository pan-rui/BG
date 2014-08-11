package com.qpp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.springframework.stereotype.Controller;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.qpp.model.Picture;
import com.qpp.model.Text;
import com.qpp.model.UnitModel;
import com.qpp.util.FastJsonUtils;

/**
 * gengerate svg 
 * @author Kevin Liu
 *
 */
@Controller
public class SvgGenereate {
	
	/**
	 * generate svg by unitList
	 * @param unitList
	 * @throws Exception
	 */
	public static void generateSvg(List<UnitModel> unitList) throws Exception {
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = "http://www.w3.org/2000/svg";
		for (int i = 0; i < unitList.size(); i++) {
			Document doc = impl.createDocument(svgNS, "svg", null);
			Element svgRoot = doc.getDocumentElement();
			UnitModel model = unitList.get(i);
			List<Picture> picList = model.getPicList();
			for (int j = 0; j < picList.size(); j++) {
				Picture pic = picList.get(j);
				Element picElement = doc.createElement("image");
				handlePic (picElement, pic);
				svgRoot.appendChild(picElement);
			}
			
			List<Text> textList = model.getTextList();
			Map<String, String> fontMap = new HashMap<String, String>();
			for (int j = 0; j < textList.size(); j++) {
				Text text = textList.get(j);
				Element textElement = doc.createElement("text");
				String fontUrl = text.getFontCode();
				String fontName = "";
				if(fontUrl != null && !"".equals(fontUrl)) {
					fontName = fontUrl.substring(0,fontUrl.lastIndexOf("."));
					fontName = fontName.substring(fontName.lastIndexOf("/") + 1);
					System.out.println(fontName);
					fontMap.put(fontName, fontUrl);
				}
				handleText (textElement, text, fontName);
				svgRoot.appendChild(textElement);
			}
			
			generateDefs(svgRoot, doc, fontMap);
			File file = new File("text_f.svg");
			transform(doc, file);
		}
	}
	
	/**
	 * generate svg by json
	 * @param json
	 * @throws Exception
	 */
	public static void testSvg(String json) throws Exception {
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = "http://www.w3.org/2000/svg";
		UnitModel unit = FastJsonUtils.json2Object(json, UnitModel.class);
		Document doc = impl.createDocument(svgNS, "svg", null);
		Element svgRoot = doc.getDocumentElement();
		List<Picture> picList = unit.getPicList();
		for (int i = 0; i < picList.size(); i++) {
			Picture pic = picList.get(i);
			Element picElement = doc.createElement("image");
			handlePic(picElement, pic);
			svgRoot.appendChild(picElement);
		}
		
		List<Text> textList = unit.getTextList();
		Map<String, String> fontMap = new HashMap<String, String>();
		for (int i = 0; i < textList.size(); i++) {
			Text text = textList.get(i);
			Element textElement = doc.createElement("text");
			String fontUrl = text.getFontCode();
			String fontName = "";
			if(fontUrl != null && !"".equals(fontUrl)) {
				fontName = fontUrl.substring(0,fontUrl.lastIndexOf("."));
				fontName = fontName.substring(fontName.lastIndexOf("/") + 1);
				System.out.println(fontName);
				fontMap.put(fontName, fontUrl);
			}
			handleText (textElement, text, fontName);
			svgRoot.appendChild(textElement);
		}
		generateDefs(svgRoot, doc, fontMap);
		File file = new File("text_1.svg");
		transform (doc, file);
	}
	
	/**
	 * generate defs 
	 * @param svgRoot
	 * @param doc
	 * @param fontMap
	 */
	public static void generateDefs (Element svgRoot, Document doc, Map<String, String> fontMap) {
		Element defs = doc.createElement("defs");
		svgRoot.appendChild(defs);
		
		Element style = doc.createElement("style");
		style.setAttribute("type", "text/css");
		StringBuilder sb = new StringBuilder();
		for (String fontName : fontMap.keySet()) {
			sb.append("  @font-face {\n");
			sb.append("  font-family:");
			sb.append("\"" + fontName + "\";\n");
			sb.append("  src: url('" + fontMap.get(fontName)  + "');\n");
			sb.append("}");
		}
		style.setTextContent(sb.toString());
		defs.appendChild(style);
	}
	
	/**
	 * handle picture
	 * @param picElement
	 * @param pic
	 */
	public static void handlePic (Element picElement, Picture pic) {
		picElement.setAttribute("x", pic.getX()+"");
		picElement.setAttribute("y", pic.getY()+"");
		picElement.setAttribute("width", pic.getWidth()+"");
		picElement.setAttribute("height", pic.getHeight()+"");
		picElement.setAttribute("xlink:href", "file:///" + pic.getPicUrl());
		String rotate = "rotate(-" + pic.getAngle() + ", " + pic.getX() +", " + pic.getY() + ")";
		picElement.setAttribute("transform", rotate);
	}
	
	/**
	 * handle text
	 * @param textElement
	 * @param text
	 */
	public static void handleText (Element textElement, Text text, String fontName) {
		textElement.setAttribute("x", text.getX()+"");
		textElement.setAttribute("y", text.getY()+"");
		
		StringBuilder fontStyle = new StringBuilder();
		fontStyle.append("font-family:");
		fontStyle.append("'" + fontName + "'");
		fontStyle.append(";font-size:");
		fontStyle.append(text.getFontSize() + "px");
		if (text.isBold()) {
			fontStyle.append(";font-weight:bold");
		} 
		
		if (text.isItalic()) {
			fontStyle.append(";font-style:italic");
		}
		
		fontStyle.append(";fill:");
		fontStyle.append(text.getColor());
		
		textElement.setAttribute("style", fontStyle.toString());
		
		String rotate = "";
		if (text.getAngle() >= 0) {
			rotate = "rotate(-" + text.getAngle() + ", " + text.getX() +", " + text.getY() + ")";
		} else {
			rotate = "rotate("+ Math.abs(text.getAngle()) + ", " + text.getX() +", " + text.getY() + ")";
		}
		textElement.setAttribute("transform", rotate);
		textElement.setTextContent(text.getText());
	}
	
	/**
	 * To generate the file
	 * @param doc
	 * @param file
	 * @throws Exception
	 */
	public static void transform (Document doc, File file) throws Exception {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transFormer = transFactory.newTransformer();

		DOMSource domSource = new DOMSource(doc);
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileOutputStream out = new FileOutputStream(file);     
		StreamResult xmlResult = new StreamResult(out);
		transFormer.transform(domSource, xmlResult);
		
	}
	
	public static void main(String[] args) throws Exception {
		List<Picture> picList = new ArrayList<Picture>();
		Picture pic = new Picture();
		pic.setX(30);
		pic.setY(400);
		pic.setAngle(30);
		pic.setHeight(300);
		pic.setWidth(200);
		pic.setPicUrl("E:\\workspace\\PdfTest\\res\\arara.jpg");
		picList.add(pic);
		
		List<Text> textList = new ArrayList<Text>();
		Text text = new Text();
		text.setBold(false);
		text.setItalic(true);
		text.setColor("#ff0000");
		text.setFontCode("Helvetica");
		text.setX(50);
		text.setY(500);
		text.setFontSize(12);
		text.setAngle(270);
		text.setText("你好");
		textList.add(text);
		
		Text text2 = new Text();
		text2.setBold(true);
		text2.setItalic(false);
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
		//generateSvg(unitList);
		String json = FastJsonUtils.object2json(unitModel);
		System.out.println(json);
	}
}
