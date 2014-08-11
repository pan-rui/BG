package com.qpp.emboridery.base;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.qpp.ps.ReqText;
import com.qpp.util.BaseXML;
import com.qpp.util.HttpClientTool;

public class EmborideryTool {
	private EmboriderySet baseSet;

	public EmborideryTool() {
	}
	public EmborideryTool(EmboriderySet vbaseSet) {
		baseSet=vbaseSet;
	}
	/**
	 * 
	 * @param 
	 * @element 分别为TextElement、CompoundElement、TemplatesElement
	 * @return
	 * 
	 */

	public String render(ReqUrlBase element){
		String imgUrl=element.getRequestUrl(baseSet.getDomain()+TextElement.renderPath+element.getUrlPath());
		return imgUrl;
	}
	/**
	 * 
	 * @param element.Format:PXF,DST,TCF,PES
	 * @return
	 * 
	 */
	public String generate(ReqUrlBase element){		
		String imgUrl=element.getRequestUrl(baseSet.getDomain()+TextElement.generatePath+element.getUrlPath());
		return imgUrl;
	}
	public String getInfo(ReqUrlBase element){		
		String imgUrl=element.getRequestUrl(baseSet.getDomain()+TextElement.infoPath+element.getUrlPath());
		String xmlResult=HttpClientTool.getDocumentAt(imgUrl);
    	BaseXML baseOrder=new BaseXML(xmlResult);
    	String retStr=baseOrder.getVariant("NumStitches","Info");
		return retStr;
	}
	public String listCommand(EmBase element){
		String imgUrl=element.getRequestUrl(baseSet.getDomain()+element.getUrlPath());
		return imgUrl;
	}

	public static void main(String[] args) {
		FileSystemXmlApplicationContext cxt =new FileSystemXmlApplicationContext(System.getProperty("user.dir")+"/WebContent/WEB-INF/spring/applicationContext-baseSet.xml");
		EmboriderySet baseSet=cxt.getBean(EmboriderySet.class);
		EmborideryTool tool=new EmborideryTool(baseSet);
		
		ReqText req=new ReqText();
		req.Text="Hello,QPP";
		TextElement element=new TextElement(req);//PXF,DST,TCF,PES
		String ret=tool.getInfo(element);
		System.out.println(ret);
		//tool.generate(element);

		//EmListFonts element=new EmListFonts();
//		element.setType("ftEmbroidery");
//		element.setUrlPath(ReqUrlBase.listFonts);
//		String ret=tool.listCommand(element);
//		System.out.println("url:"+ret);
	}

}
