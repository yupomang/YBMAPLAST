package com.yondervision.mi.service;

import net.sf.json.JSONArray;

import com.yondervision.mi.dto.CMi123;
import com.yondervision.mi.result.WebApi12301_queryResult;

/** 
* @ClassName: WebApi123Service 
* @Description: 免打扰时间
* @author Caozhongyan
* @date Jan 12, 2015 9:59:57 AM   
* 
*/ 
public interface WebApi123Service {
	/**
	 * 推送免打扰填加
	 */
	public void webapi12301(CMi123 form) throws Exception;
	/**
	 * 推送免打扰删除
	 */
	public void webapi12302(CMi123 form) throws Exception;
	/**
	 * 推送免打扰修改
	 */
	public int webapi12303(CMi123 form) throws Exception;
	/**
	 * 推送免打扰查询
	 */
	public WebApi12301_queryResult webapi12304(CMi123 form) throws Exception;
	
	public void webapi12305(JSONArray arr) throws Exception;
}	
