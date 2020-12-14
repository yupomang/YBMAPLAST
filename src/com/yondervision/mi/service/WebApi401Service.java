package com.yondervision.mi.service;

import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.result.WebApi40101_queryResult;
import com.yondervision.mi.result.WebApi40102_queryResult;
import com.yondervision.mi.result.WebApi40103_queryResult;

/** 
* @ClassName: WebApi401Service 
* @Description:APP用户注册信息处理接口
* @author Caozhongyan
* @date Sep 29, 2013 2:55:31 PM   
* 
*/ 
public interface WebApi401Service {
	/**
	 * APP用户注册信息查询
	 */
	public WebApi40101_queryResult webapi40101(CMi103 form) throws Exception;
	
	/**
	 * APP用户设备信息查询
	 */
	public WebApi40102_queryResult webapi40102(CMi103 form) throws Exception;
	

	/**
	 * APP用户通讯信息查询
	 */
	public WebApi40103_queryResult webapi40103(CMi103 form) throws Exception;
}
