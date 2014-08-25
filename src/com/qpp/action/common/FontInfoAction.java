package com.qpp.action.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qpp.action.BaseAction;
import com.qpp.model.BaseReturn;
import com.qpp.model.FontInfo;
import com.qpp.service.common.FontInfoService;

/**
 * system font
 * @author Kevin Liu 2014/8/21
 *
 */
@Controller
public class FontInfoAction  extends BaseAction {
	
	private Logger logger = Logger.getLogger(FontInfoAction.class);
	
	@Autowired
	private FontInfoService fontInfoService;
	
	@RequestMapping(value="/product/font")
	@ResponseBody
	public BaseReturn getFontInfos (HttpServletRequest request, HttpServletResponse response) {
		List<FontInfo> fontInfoList = fontInfoService.getFontInfoList();
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setData(fontInfoList);
		return baseReturn;
	}
}
