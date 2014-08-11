package com.qpp.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.qpp.emboridery.base.EmboriderySet;
import com.qpp.emboridery.base.EmborideryTool;
import com.qpp.emboridery.base.TextElement;
import com.qpp.listener.SpringContextUtil;
import com.qpp.ps.EmborideryData;
import com.qpp.ps.JsonResponse;
import com.qpp.ps.ReqText;
import com.qpp.util.JsonTool;
//@WebServlet(urlPatterns = "/embroidery/generate_text_image",name ="emborideryService" )
public class EmborideryService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger=Logger.getLogger(this.getClass());

	public void init() throws ServletException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
    	//String data=(String)request.getParameter("data");
    	String data=readStream(request);
    	JsonResponse resp=new JsonResponse();
    	if (!StringUtils.isEmpty(data)){
    		logger.info("EmbroideryServlet message:"+data);
    		
    		ReqText req=new ReqText();
    		try{
    			String psData=JsonTool.getJsonPathNode(data,"data");//generate_text_image;
				if (StringUtils.isEmpty(psData))
					writeError(response,"No data is provided");
       			req=JsonTool.getJsonObject(psData,ReqText.class);//generate_text_image    			
    			if (req==null || req.Text==null){
    				writeError(response,"Data format is invalid");
    				return;
    			}
    			EmboriderySet baseSet=(EmboriderySet)SpringContextUtil.getBean("emboriderySet");
    			TextElement element=new TextElement(req);
    			EmborideryTool tool=new EmborideryTool(baseSet);
    			String imgUrl;
    			if (req.Format!=null && (req.Format.equalsIgnoreCase("PXF")||req.Format.equalsIgnoreCase("DST")||req.Format.equalsIgnoreCase("TCF")||req.Format.equalsIgnoreCase("PES")))//PXF,DST,TCF,PES
    				imgUrl=tool.generate(element);
    			else
    				imgUrl=tool.render(element);
    			boolean result=true;
//    			String rootPath=request.getRealPath("/WEB-INF/");//SpringContextUtil.getWebPath()    			
//    			InputStream inputStream=HttpClientTool.getStreamByGet(imgUrl);
//    			imgUrl=rootPath+baseSet.getImagePath();
//    			if (StringUtils.isEmpty(req.OrderNo))
//    				imgUrl+=FileUtil.getRandomFileName();
//    			else
//    				imgUrl+=req.OrderNo+".png";    			
//    			result=FileUtil.inputStreamToFile(inputStream,new File(imgUrl));
    			logger.debug(request.getRemoteAddr()+"-url:"+imgUrl);    			
    			if (result){
    				EmborideryData edata=new EmborideryData();
    				int stitches=0;
    				try{
    					stitches=Integer.parseInt(tool.getInfo(element));
    				}catch(Exception e){    				
    				}
    				edata.stitches=stitches;
    				edata.success=true;
    				edata.url=imgUrl;
    				edata.value=JsonTool.getJsonPathNode(data,"data");
    				resp.Success=true;
    				resp.data=edata;    				    				
    			}else{
        			resp.Success=false;
        			resp.ErrMsg="Create file error";
    			}
    			writeResponse(response,resp.toString());
    		}catch(Exception e){
    			e.printStackTrace();
    			logger.warn("EmborideryServlet get error:"+e.getMessage());
    			resp.Success=false;
    			resp.ErrMsg=e.getMessage();
    			writeResponse(response,resp.toString());
    		}
    	}else{
    		writeError(response,"No data is provided");
    	}
    }

    private void writeError(HttpServletResponse response,String message){
    	JsonResponse resp=new JsonResponse();
		resp.Success=false;
		resp.ErrMsg=message;
		writeResponse(response,resp.toString());    	
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
    	writeError(response,"Please use post method");
    }
    private void writeResponse(HttpServletResponse response,String ret){
    	try{
    		java.io.PrintWriter out = response.getWriter();
    		out.println(ret);
    		out.close();    			    	    		
    	}catch(Exception e){
    		
    	}
    }
	private String readStream(HttpServletRequest request){
	     String line ="";
		 try{
			 BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
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
