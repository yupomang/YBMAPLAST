package com.yondervision.mi.service;

import com.yondervision.mi.dto.CMi108;
import com.yondervision.mi.form.WebApi40401Form;
import com.yondervision.mi.result.WebApi40401_queryResult;

/** 
* @ClassName: WebApi404Service 
* @Description:意见反馈信息处理接口
* @author Caozhongyan
* @date Sep 29, 2013 2:55:31 PM   
* 
*/ 
public interface WebApi404Service {
	/**
	 * 意见反馈信息查询
	 */
	public WebApi40401_queryResult webapi40401(WebApi40401Form form) throws Exception;
	
	/**
	 * 确认人意见
	 */
	public int webapi40402(CMi108 form) throws Exception;
	
}
