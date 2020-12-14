package com.yondervision.mi.service;

import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.dto.CMi120;
import com.yondervision.mi.dto.CMi121;
import com.yondervision.mi.result.WebApi41104_queryResult;
import com.yondervision.mi.result.WebApi41108_queryResult;

/** 
* @ClassName: WebApi411Service 
* @Description: 图片动画
* @author Caozhongyan
* @date Dec 2, 2013 9:36:03 AM   
* 
*/ 
public interface WebApi411Service {
	
	public void webapi41101(CMi120 form) throws Exception;
	
	public void webapi41102(CMi120 form) throws Exception;
	
	public void webapi41103(CMi120 form) throws Exception;
	
	public WebApi41104_queryResult webapi41104(CMi120 form) throws Exception;
	
	public void webapi41105(CMi121 form) throws Exception;
	
	public void webapi41106(CMi121 form) throws Exception;
	
	public void webapi41107(CMi121 form) throws Exception;
	
	public WebApi41108_queryResult webapi41108(CMi121 form) throws Exception;
	
	public String webapi41109(String magecenterId, MultipartFile file) throws Exception;
}
