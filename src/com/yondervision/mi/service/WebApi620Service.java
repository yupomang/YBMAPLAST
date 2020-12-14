package com.yondervision.mi.service;

import net.sf.json.JSONObject;

import com.yondervision.mi.dto.CMi620;

/** 
* @ClassName: WebApi620Service 
* @Description: 预约业务类型
* @author sunxl
* @date Sep 29, 2013 2:55:31 PM
* 
*/ 
public interface WebApi620Service {
	/**
	 * 业务类型新增
	 */
	public void webapi62001(CMi620 form) throws Exception;
	
	/**
	 * 业务类型删除
	 */
	public int webapi62002(CMi620 form) throws Exception;
	
	/**
	 * 业务类型修改
	 */
	public int webapi62003(CMi620 form) throws Exception;
	
	/**
	 * 业务类型查询(分页)
	 * @param form
	 * @return
	 */
	public JSONObject webapi62004(CMi620 form,Integer page, Integer rows);
}
