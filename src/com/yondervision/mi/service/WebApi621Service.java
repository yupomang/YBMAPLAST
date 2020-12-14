package com.yondervision.mi.service;

import net.sf.json.JSONObject;

import com.yondervision.mi.dto.CMi621;

/** 
* @ClassName: WebApi621Service 
* @Description:时段模版类型
* @author sunxl
* @date Sep 29, 2013 2:55:31 PM
* 
*/ 
public interface WebApi621Service {
	/**
	 * 时段模版新增
	 */
	public void webapi62101(CMi621 form) throws Exception;
	
	/**
	 * 时段模版删除
	 */
	public int webapi62102(CMi621 form) throws Exception;
	
	/**
	 * 时段模版修改
	 */
	public int webapi62103(CMi621 form) throws Exception;
	
	/**
	 * 时段模版查询(分页)
	 * @param form
	 * @return
	 */
	public JSONObject webapi62104(CMi621 form);
}
