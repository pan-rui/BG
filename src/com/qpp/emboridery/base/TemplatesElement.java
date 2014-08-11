package com.qpp.emboridery.base;

import java.util.ArrayList;
import java.util.List;

import com.qpp.ps.TemplateReqText;
import com.qpp.util.HttpClientTool;

public class TemplatesElement extends ReqUrlBase {
	private List<Personalizations> personalizations ; 
	private String templateName;
	public TemplatesElement(){
		super();
		this.setUrlPath("Templates/");
	}
	public List<Personalizations> getPersonalizations() {
		return personalizations;
	}
	public void setPersonalizations(List<Personalizations> personalizations) {
		this.personalizations = personalizations;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}	
	public TemplatesElement(TemplateReqText req){
		super(req);
		this.setTemplateName(req.TemplateName);
		this.setPersonalizations(req.Personalizations);
		this.setUrlPath("Templates/");
	}

	public String getRequestUrl(String urlPath){
		StringBuilder url=new StringBuilder();
		if (this.getPersonalizations()!= null && this.getPersonalizations().size()>0){
			url=HttpClientTool.addStringBuilderObjectArrayToUrl(url,"Personalizations",personalizations);
		}		
		url=getRequestUrlFromBuilder(url);
		StringBuilder urlAll=new StringBuilder();
		urlAll.append(urlPath);
		//urlAll.append(this.getUrlPath());
		urlAll.append(templateName);
		urlAll.append(url.toString());		
		return urlAll.toString();
	}	
	public static void main(String[] args){
		List<Personalizations> list=new ArrayList<Personalizations>();
		Personalizations person=new Personalizations();
		person.Name="Text 1";
		person.Text="Good";
		list.add(person);
		Personalizations person1=new Personalizations();
		person1.Name="Text 2";
		person1.Text="also Good";
		list.add(person1);
		TemplatesElement template=new TemplatesElement();
		template.setTemplateName("Flag.PXF");
		template.setPersonalizations(list);
		System.out.println(template.getRequestUrl("http://trial3.stitchport.com/stitchportwebapi/1/Render/"));
		
	}
}
