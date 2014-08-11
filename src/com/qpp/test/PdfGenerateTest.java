package com.qpp.test;

import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.qpp.model.Picture;
import com.qpp.model.Text;
import com.qpp.model.UnitModel;

/**
 * itext generate pdf 
 * @author Kevin Liu
 *
 */
@Controller
public class PdfGenerateTest {
	
	/**
	 * generate pdf by modelList
	 * @param modelList
	 * @return
	 * @throws Exception
	 */
	public void generatePdf (List<UnitModel> modelList) throws Exception {
		Document doc = new Document(PageSize.A4,15,20,15,15);
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("d:\\test.pdf"));
		doc.open();
		PdfContentByte pcb = writer.getDirectContent();
		pcb.saveState();
		pcb.beginText();
		
		for (int i = 0; i < modelList.size(); i++) {
			UnitModel model = modelList.get(i);
			if(i != 0) {
				doc.newPage();
			}
			List<Picture> picList = model.getPicList();
			Image img = Image.getInstance(model.getBackPic());
			img.setAbsolutePosition(0, 0); 
			img.scaleAbsolute(PageSize.A4);
			doc.add(img);
			for (int j = 0; j < picList.size(); j++) {
				Picture pic = picList.get(j);
				Image im = Image.getInstance(pic.getPicUrl());
				im.setAbsolutePosition(pic.getX(), pic.getY());
				im.scaleAbsolute(pic.getWidth(), pic.getHeight());
				im.setRotationDegrees(pic.getAngle());
				doc.add(im);
			}
			
			List<Text> textList = model.getTextList();
			for (int j = 0; j < textList.size(); j++) {
				Text text = textList.get(j);
				BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",  false);
				pcb.setFontAndSize(bf, text.getFontSize());
				
				Font font = new Font(bf);
				if (text.isBold()) {
					if (text.isItalic()) {
						font.setStyle(Font.BOLDITALIC);
					} else {
						font.setStyle(Font.BOLD);
					}
				} else {
					if (text.isItalic()) {
						font.setStyle(Font.ITALIC);
					}
				}
				int rgb = Integer.parseInt(text.getColor().substring(1),16);
				font.setColor(new BaseColor(rgb));
				Phrase hello = new Phrase(text.getText());
				hello.setFont(font);
				Phrase phrase = new Phrase(text.getText(), font);
				ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT, phrase, text.getX(), text.getY(), text.getAngle());
			}
			
		}
		pcb.endText();
		pcb.restoreState();
		doc.close();
	}
}
