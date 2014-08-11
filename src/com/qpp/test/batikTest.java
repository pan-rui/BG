package com.qpp.test;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
   
/**  
 * svg转换工具类(以下方法开发足够用了)  
 * @param svg  
 * @param pdf  
 * @throws IOException  
 * @throws TranscoderException  
 */ 
public class batikTest {   
    //svg文件转成   
    public static void convertSvgFile2Pdf(File svg, File pdf) throws IOException,TranscoderException    
    {   
        InputStream in = new FileInputStream(svg);   
        OutputStream out = new FileOutputStream(pdf);   
        out = new BufferedOutputStream(out);   
        convert2Pdf(in, out);   
    }   
    public static void convert2Pdf(InputStream in, OutputStream out)throws IOException, TranscoderException   
    {   
        Transcoder transcoder = new PDFTranscoder();   
        try {   
            TranscoderInput input = new TranscoderInput(in);   
            try {   
                TranscoderOutput output = new TranscoderOutput(out);   
                transcoder.transcode(input, output);   
            } finally {   
                out.close();   
            }   
        } finally {   
            in.close();   
        }   
    }   
    //svg转为png   
    public static void convertSvgFile2Png(File svg, File pdf) throws IOException,TranscoderException    
    {   
        InputStream in = new FileInputStream(svg);   
        OutputStream out = new FileOutputStream(pdf);   
        out = new BufferedOutputStream(out);   
        convert2PNG(in, out);   
    }   
    public static void convert2PNG(InputStream in, OutputStream out)throws IOException, TranscoderException   
    {   
        Transcoder transcoder = new PNGTranscoder();   
        try {   
            TranscoderInput input = new TranscoderInput(in);   
            try {   
                TranscoderOutput output = new TranscoderOutput(out);   
                transcoder.transcode(input, output);   
            } finally {   
                out.close();   
            }   
        } finally {   
            in.close();   
        }   
    }   
    //字符串转成pdf   
    public static void convertStr2Pdf(String svg, File pdf) throws IOException,TranscoderException    
    {   
        InputStream in = new ByteArrayInputStream(svg.getBytes());   
        OutputStream out = new FileOutputStream(pdf);   
        out = new BufferedOutputStream(out);   
        convert2Pdf(in, out);   
    }
    
    public static void convertSvgFileToPdf (File svg, File pdf) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document svgXmlDoc = builder.parse(svg);
		Element element = svgXmlDoc.getDocumentElement();
		String height = element.getAttribute("height");
		String width = element.getAttribute("width");
//		Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		DOMSource source2 = new DOMSource(svgXmlDoc);
//		FileOutputStream fOut = new FileOutputStream(svg);
//		try { 
//			transformer.transform(source2, new StreamResult(fOut)); 
//		} finally { 
//			fOut.close(); 
//		}
//		
		SVGConverter converter = new SVGConverter();
		if (height != null && !"".equals(height)) {
			converter.setHeight(Float.parseFloat(height));
		} else {
			converter.setHeight(1600);
		}
		
		if (width != null && !"".equals(width)) {
			converter.setWidth(Float.parseFloat(width));
		} else {
			converter.setWidth(1000);
		}
		
		converter.setDestinationType(DestinationType.PDF);
		converter.setSources(new String[] { svg.toString() });
		converter.setDst(pdf);
		converter.execute();
	}
    
    public static void BatikTest1(){
    	DOMImplementation domImpl=GenericDOMImplementation.getDOMImplementation();
        String svgNamespaceURI =SVGDOMImplementation.SVG_NAMESPACE_URI;
        Document document=domImpl.createDocument(svgNamespaceURI, "svg", null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        svgGenerator.setPaint(Color.yellow);  
        svgGenerator.fill(new Rectangle(0, 0, 100, 30));           
        svgGenerator.setPaint(Color.black);  
        svgGenerator.drawString("12345", 20, 20);
        boolean useCSS = false; // we want to use CSS style attribute
        try {
	        Writer out = new OutputStreamWriter(System.out, "UTF-8");
	        svgGenerator.stream(out, useCSS);			
		} catch (Exception e) {
		}
    }
    public static void BatikTest(){
    	DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
    	String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    	Document doc = impl.createDocument(svgNS, "svg", null);
    	Element svgRoot = doc.getDocumentElement();
    	// Set the width and height attributes on the root 'svg' element.
    	svgRoot.setAttributeNS(null, "width", "400");
    	svgRoot.setAttributeNS(null, "height", "450");
    	Element rectangle = doc.createElementNS(svgNS, "rect");
    	rectangle.setAttributeNS(null, "x", "10");
    	rectangle.setAttributeNS(null, "y", "20");
    	rectangle.setAttributeNS(null, "width", "100");
    	rectangle.setAttributeNS(null, "height", "50");
    	rectangle.setAttributeNS(null, "fill", "red");
    	// Attach the rectangle to the root 'svg' element.
    	svgRoot.appendChild(rectangle);
    	
    	org.dom4j.io.DOMReader   xmlReader   =   new   org.dom4j.io.DOMReader(); 
        System.out.println(xmlReader.read(doc).asXML());     	
    	
    }
    public static void main(String[] args) {
    	try {
    		//batikTest.convertSvgFile2Pdf(new File("d:\\work\\pdf\\batik3D.svg"), new File("d:\\temp\\batik3D.pdf"));	
    		//batikTest.convertSvgFile2Png(new File("d:\\temp\\test.svg"), new File("d:\\temp\\test.png"));
    		//batikTest.convertSvgFile2Pdf(new File("d:\\temp\\test.svg"), new File("d:\\temp\\test.pdf"));
    		//batikTest.BatikTest1();
    		
    		File svgFile = new File("text_1.svg");
    		convertSvgFileToPdf (svgFile, new File("test.pdf"));
    		
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		
	}
}       
