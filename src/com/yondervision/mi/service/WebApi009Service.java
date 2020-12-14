package com.yondervision.mi.service;

import net.sf.json.JSONObject;
import com.yondervision.mi.dto.CMi109;

/** 
* @ClassName: WebApi009Service 
* @Description: 利率维护处理接口
* @author gongq
* @date Sep 29, 2013 2:55:31 PM
* 
*/ 
public interface WebApi009Service {
	/**
	 * 利率新增
	 */
	public void webapi00901(CMi109 form) throws Exception;
	
	/**
	 * 利率删除
	 */
	public int webapi00902(CMi109 form) throws Exception;
	
	/**
	 * 利率修改
	 */
	public int webapi00903(CMi109 form) throws Exception;
	
	/**
	 * 利率查询
	 */
//	public List<Mi109> webapi00904(CMi109 form) throws Exception;
	
	/**
	 * 利率查询(分页)
	 * @param form
	 * @return
	 */
	public JSONObject webapi00904(CMi109 form,Integer page, Integer rows);
}
