package com.qpp.emboridery.base;

import java.util.ArrayList;
import java.util.List;

import com.qpp.ps.ComplexReqText;
import com.qpp.ps.ReqText;
import com.qpp.util.HttpClientTool;


public class CompoundElement extends ReqUrlBase {
	public String Layout;
	public String StackedAlignment;
	public int StackedSpacing;

	public CompoundElement(){
		this.setUrlPath("Compound");
	}

	public enum LayoutTypes{
		ltAbsolute,ltStackedHorizontal,ltStackedVertical
	}
	public enum StackedAlignmentTypes{
		satCenter,satNear,satFar
	}
	private List<ReqUrlBase> elements;
	private List<String> palette;
	private List<Short> needles;


	public List<ReqUrlBase> getElements() {
		return elements;
	}


	public void setElements(List<ReqUrlBase> elements) {
		this.elements = elements;
	}


	public List<String> getPalette() {
		return palette;
	}


	public void setPalette(List<String> palette) {
		this.palette = palette;
	}


	public List<Short> getNeedles() {
		return needles;
	}


	public void setNeedles(List<Short> needles) {
		this.needles = needles;
	}
	public CompoundElement(ComplexReqText complexReq){
		super(complexReq);
		this.setPalette(complexReq.Palette);
		this.setNeedles(complexReq.Needles);
		this.Layout=complexReq.Layout;
		this.StackedAlignment=complexReq.StackedAlignment;
		this.StackedSpacing=complexReq.StackedSpacing;
		ArrayList<ReqText> complex=complexReq.Elements;
		List<ReqUrlBase> list=new ArrayList<ReqUrlBase>();
		for(ReqText req:complex){
			list.add(new TextElement(req));
		}
		this.setElements(list);
		this.setUrlPath("Compound");
	}

	
	public String getRequestUrl(String urlPath){
		StringBuilder url=new StringBuilder();
		if (this.getElements()!= null && this.getElements().size()>0){
			List<String> elementStrings = new ArrayList<String>();
			for(ReqUrlBase e:this.getElements()){
				String urle = e.getRequestUrl(e.getUrlPath());
				elementStrings.add(urle);
			}
			url=HttpClientTool.addStringBuilderArrayParaToUrl(url,"Elements",elementStrings);
		}
		
		url=HttpClientTool.addStringBuilderArrayParaToUrl(url,"Palette",this.getPalette());
		url=HttpClientTool.addStringBuilderArrayParaToUrl(url,"Needles",this.getPalette());
		url=HttpClientTool.addStringBuilderParaToUrl(url,"Layout",this.Layout);
		if (this.StackedSpacing!=0)
			url=HttpClientTool.addStringBuilderParaToUrl(url,"StackedSpacing",this.StackedSpacing);
		url=HttpClientTool.addStringBuilderParaToUrl(url,"StackedAlignment",this.StackedAlignment);
		url=getRequestUrlFromBuilder(url);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(urlPath);
		urlAll.append(url.toString());		
		return urlAll.toString();
	}	
	public static void main(String[] args){
//		List<ReqUrlBase> list=new ArrayList<ReqUrlBase>();
//		TextElement person=new TextElement();
//		person.setText("gary");
//		list.add(person);
//		TextElement person1=new TextElement();
//		person1.setText("gary1");
//		person1.setY1(300);
//		person1.setY2(300);
//		list.add(person1);
//		CompoundElement comp=new CompoundElement();
//		comp.setElements(list);
//		System.out.println(JsonTool.getJsonString(comp));
//		
//		FileSystemXmlApplicationContext cxt =new FileSystemXmlApplicationContext(System.getProperty("user.dir")+"/WebContent/WEB-INF/spring/applicationContext-baseSet.xml");
//		EmboriderySet baseSet=cxt.getBean(EmboriderySet.class);
//		EmborideryTool tool=new EmborideryTool(baseSet);
//		System.out.println(tool.render(comp));
		CompoundElement.LayoutTypes layout=CompoundElement.LayoutTypes.ltStackedVertical;
		System.out.println(layout);
		
	}	
}
