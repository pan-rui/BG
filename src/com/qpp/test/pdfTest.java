package com.qpp.test;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class pdfTest {
	public static void readTest(){
		//Rectangle pSize=new Rectangle(144,90);
		//Document doc=new Document(pSize,5,5,5,5);
		Document doc=new Document(PageSize.A4,5,5,5,5);		
	    //Document doc = new Document();
        try {
            // 定义输出位置并把文档对象装入输出对象中
        	PdfWriter writer=PdfWriter.getInstance(doc, new FileOutputStream("d:\\temp\\hello.pdf"));
            doc.open();
            doc.add(new Paragraph("Hello,Gary"));
    		doc.add(new Paragraph("Hello World2", FontFactory.getFont(FontFactory.COURIER, 20,BaseColor.RED)));
            doc.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void hello(){
		//Rectangle pSize=new Rectangle(144,90);
		//Document doc=new Document(pSize,5,5,5,5);
		Document doc=new Document(PageSize.A4,5,5,5,5);
	    //Document doc = new Document();
        try {
            // 定义输出位置并把文档对象装入输出对象中
        	PdfWriter writer=PdfWriter.getInstance(doc, new FileOutputStream("d:\\temp\\hello.pdf"));
            // 打开文档对象
            doc.open();
            BaseFont bfHei = BaseFont.createFont("d:\\temp\\msyh.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfHei);
            doc.add(new Paragraph("Hello,Gary1",font));

        	//PdfContentByte cb = writer.getDirectContent();//前景
            PdfContentByte cb = writer.getDirectContentUnder();//背景
//        	cb.moveTo(0, 0);
//        	cb.lineTo(300, 300);
//        	cb.stroke();
//        	cb.setFontAndSize(bfHei, 12);
//        	cb.showTextAligned(PdfContentByte.ALIGN_CENTER,"Back Text",200,200,0);
            
    		doc.add(new Paragraph("Hello World1", FontFactory.getFont(FontFactory.COURIER, 20,new BaseColor(255,0,0))));
    		doc.add(new Paragraph("Hello World undeline", FontFactory.getFont(FontFactory.COURIER, 20,Font.UNDERLINE)));
    		
    		Chunk chunk = new Chunk("Hello world", FontFactory.getFont(FontFactory.COURIER, 20, new BaseColor(255, 0, 0)));
    		chunk.setBackground(new BaseColor(255,255,0));
    		doc.add(chunk);

    		Image image = Image.getInstance("d:\\temp\\t1.png");
    		image.setAlignment(Image.ALIGN_CENTER);
    		image.setAbsolutePosition(0, 0);
    		cb.addImage(image);
            //doc.add(image);
//    		Paragraph p1 = new Paragraph("left");
//    		LineSeparator UNDERLINE = new LineSeparator(1, 60, null, Element.ALIGN_CENTER,0); 
//    		p1.add(new Chunk(UNDERLINE));   
//    		doc.add(p1);
    		
            doc.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		pdfTest.hello();
	}
}
