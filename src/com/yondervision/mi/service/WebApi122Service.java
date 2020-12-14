package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.CMi122;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.result.WebApi12201_queryResult;

import net.sf.json.JSONArray;

/** 
* @ClassName: WebApi122Service 
* @Description: 推送主题类
* @author Caozhongyan
* @date Jan 12, 2015 9:59:57 AM   
* 
*/ 
public interface WebApi122Service {
	/**
	 * 推送主题类型填加
	 */
	public void webapi12201(CMi122 form) throws Exception;
	/**
	 * 推送主题类型删除
	 */
	public void webapi12202(CMi122 form) throws Exception;
	/**
	 * 推送主题类型修改
	 */
	public int webapi12203(CMi122 form) throws Exception;
	/**
	 * 推送主题类型查询
	 */
	public WebApi12201_queryResult webapi12204(CMi122 form) throws Exception;
	
	public void webapi12205(JSONArray arr) throws Exception;
	/**
	 * 根据中心获取定制的主题列表
	 * @param form
	 * @throws Exception
	 */
	public List<Mi122> webapi12206(CMi122 form) throws Exception;
}	
