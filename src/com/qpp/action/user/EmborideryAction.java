package com.qpp.action.user;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.qpp.action.BaseAction;
import com.qpp.emboridery.base.CompoundElement;
import com.qpp.emboridery.base.EmboriderySet;
import com.qpp.emboridery.base.EmborideryTool;
import com.qpp.emboridery.base.ReqUrlBase;
import com.qpp.emboridery.base.TemplatesElement;
import com.qpp.emboridery.base.TextElement;
import com.qpp.ps.ComplexReqText;
import com.qpp.ps.EmborideryData;
import com.qpp.ps.JsonResponse;
import com.qpp.ps.PsReqBase;
import com.qpp.ps.ReqText;
import com.qpp.ps.TemplateReqText;
import com.qpp.util.JsonTool;
@Controller
@RequestMapping("/embroideryAction")
public class EmborideryAction extends BaseAction {
	private Logger logger=Logger.getLogger(this.getClass());
	@Autowired
	private EmboriderySet baseSet;
    @RequestMapping(value="/{method}")
    @ResponseBody
    //public @ResponseBody String show(@PathVariable String method,HttpServletRequest request,HttpServletResponse response) {
    public  JsonResponse show(@PathVariable String method,HttpServletRequest request) {
    	//response.setContentType("text/html; charset=utf-8");
    	String data=readStream(request);
    	JsonResponse resp=new JsonResponse();
		EmborideryTool tool=new EmborideryTool(baseSet);
    	if (!StringUtils.isEmpty(data)){
    		logger.info(method+"--"+request.getRemoteAddr()+":"+data);
    		try{
    			String imgUrl="";
				PsReqBase req=null;
				ReqUrlBase element=null;
				String psData=JsonTool.getJsonPathNode(data,"data");//generate_text_image;
				if (StringUtils.isEmpty(psData))
					return writeError("No data is provided");
    			if ("generate_text_image".equalsIgnoreCase(method)){
        			req=JsonTool.getJsonObject(psData,ReqText.class);//generate_text_image
        			if (req==null){
        				return writeError("Data format is invalid");
        			}
        			element=new TextElement((ReqText)req);
    			}else if("generate_complex_text_image".equalsIgnoreCase(method)){
        			req=JsonTool.getJsonObject(psData,ComplexReqText.class);//generate_text_image
        			if (req==null){
        				return writeError("Data format is invalid");
        			}
        			element=new CompoundElement((ComplexReqText)req);
    			}else if("generate_template_text_image".equalsIgnoreCase(method)){
        			req=JsonTool.getJsonObject(psData,TemplateReqText.class);//generate_template_image
        			if (req==null){
        				return writeError("Data format is invalid");
        			}
        			element=new TemplatesElement((TemplateReqText)req);
    			}
    			if (req.Format!=null && (req.Format.equalsIgnoreCase("PXF")||req.Format.equalsIgnoreCase("DST")||req.Format.equalsIgnoreCase("TCF")||req.Format.equalsIgnoreCase("PES")))//PXF,DST,TCF,PES
    				imgUrl=tool.generate(element);
    			else
    				imgUrl=tool.render(element);
				int stitches=0;
				try{
					//stitches=Integer.parseInt(tool.getInfo(element));
				}catch(Exception e){    				
				}
    			logger.debug("url:"+imgUrl);    			
				EmborideryData edata=new EmborideryData();
				edata.stitches=stitches;
				edata.success=true;
				edata.url=imgUrl;
				edata.value=JsonTool.getJsonPathNode(data,"data");
				resp.Success=true;
				resp.data=edata;    				    				
    			return resp;
    		}catch(Exception e){
    			e.printStackTrace();
    			logger.warn("EmborideryServlet get error:"+e.getMessage());
    			resp.Success=false;
    			resp.ErrMsg=e.getMessage();
    			return resp;
    		}
    	}else{
    		return writeError("No data is provided");
    	}    	
    }
    @RequestMapping(value="/{method}",params = "actionMethod=delete")
    @ResponseBody
    //public @ResponseBody String show(@PathVariable String method,HttpServletRequest request,HttpServletResponse response) {
    public  JsonResponse showTest(@PathVariable String method,HttpServletRequest request) {
    	return writeError("You want to delete?");    	
    }	
  
    private JsonResponse writeError(String message){
    	JsonResponse resp=new JsonResponse();
		resp.Success=false;
		resp.ErrMsg=message;
		return resp;    	
    }
    private String readStream(HttpServletRequest request){
	     String line ="";
		 try{
			 BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		     StringBuilder sb = new StringBuilder();
		     while((line = br.readLine())!=null){
		          sb.append(line);
		      }
		     line=sb.toString();
		 }catch(Exception e){
		    	 line="";
		     }
		return line;
	 }        
    }   