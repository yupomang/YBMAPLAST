package com.yondervision.mi.service;

import com.yondervision.mi.dto.CMi607;
import com.yondervision.mi.dto.Mi607;

import net.sf.json.JSONObject;

/**
 * @ClassName: WebApi607Service 
 * @Description:黑名单处理接口
 * @author Lihongjie
 * @date 2014-08-04 20:17
 * 
 */
public interface WebApi607Service {

	/*
	 * 修改有效标记
	 */
	public int webapi60701(Mi607 form) throws Exception ;
	
	/*
	 * 查询
	 */
	public JSONObject webapi60702(CMi607 form) throws Exception ;
	
	/**
	 * 查询指定客户是否为黑名单用户
	 * @param personalid
	 * @return
	 * @throws Exception
	 */
	public Mi607 webapi60703(String personalid) throws Exception ;
}
