package com.yondervision.mi.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yondervision.mi.dto.CMi623;
import com.yondervision.mi.dto.CMi624;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi623;

/** 
* @ClassName: WebApi623Service 
* @Description:时段模版类型
* @author sunxl
* @date Sep 29, 2013 2:55:31 PM
* 
*/ 
public interface WebApi623Service {
	/**
	 * 时段模版新增
	 */
	public void webapi62301(CMi623 form) throws Exception;
	
	/**
	 * 时段模版删除
	 */
	public int webapi62302(CMi623 form) throws Exception;
	
	/**
	 * 时段模版修改
	 */
	public int webapi62303(CMi623 form) throws Exception;
	
	/**
	 * 时段模版查询(分页)
	 * @param form
	 * @return
	 */
	public JSONObject webapi62304(CMi623 form,Integer page, Integer rows);
	/**
	 * 时段模版新增
	 */
	public void webapi62401(CMi624 form) throws Exception;
	
	/**
	 * 时段模版删除
	 */
	public int webapi62402(CMi624 form) throws Exception;
	
	/**
	 * 时段模版修改
	 */
	public int webapi62403(CMi624 form) throws Exception;
	
	/**
	 * 时段模版查询(分页)
	 * @param form
	 * @return
	 */
	public JSONObject webapi62404(CMi624 form,Integer page, Integer rows);

	public List<Mi201> getWebsiteByArea(String pid);
	public List<Mi202> getArea() ;
	public int webapiUpdateSort(JSONArray arr);
}
