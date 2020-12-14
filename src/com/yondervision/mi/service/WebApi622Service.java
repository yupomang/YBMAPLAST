package com.yondervision.mi.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yondervision.mi.dto.CMi622;
import com.yondervision.mi.dto.Mi622;

/** 
* @ClassName: WebApi622Service 
* @Description:时段模版类型
* @author sunxl
* @date Sep 29, 2013 2:55:31 PM
* 
*/ 
public interface WebApi622Service {
	/**
	 * 时段模版新增
	 */
	public void webapi62201(CMi622 form) throws Exception;
	
	/**
	 * 时段模版删除
	 */
	public int webapi62202(CMi622 form) throws Exception;
	
	/**
	 * 时段模版修改
	 */
	public int webapi62203(CMi622 form) throws Exception;
	
	/**
	 * 时段模版查询(分页)
	 * @param form
	 * @return
	 */
	public JSONObject webapi62204(CMi622 form);
	/**
	 * 查询预约时段明细信息
	 * @param centerid
	 * @return
	 */
	public List<Mi622> getBussTemplaDetail(String template);
	public int webapiUpdateSort(JSONArray arr);
}
