package com.qpp.service.common.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



import com.qpp.dao.FontInfoDao;
import com.qpp.model.FontInfo;
import com.qpp.service.common.FontInfoService;

@Service
public class FontInfoServiceImpl implements FontInfoService {

	@Resource
	private FontInfoDao fontInfoDao;
	
	@Cacheable(value="commonData")
	public List<FontInfo> getFontInfoList() {
		return fontInfoDao.getsByQuery("from FontInfo");
	}

}
