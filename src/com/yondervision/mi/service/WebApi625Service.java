package com.yondervision.mi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.yondervision.mi.dto.CMi625;
import com.yondervision.mi.dto.Mi625;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.result.WebApi62506_queryResult;

/** 
* @ClassName: WebApi623Service 
* @Description:时段模版类型
* @author sunxl
* @date Sep 29, 2013 2:55:31 PM
* 
*/ 
public interface WebApi625Service {
	

	/**
	 * 时段模版查询(分页)
	 * @param form
	 * @param response 
	 * @param request 
	 * @return
	 */
	public JSONObject webapi62504(CMi625 form,Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response);
	public int webapi62503(CMi625 form);
	/**
	 * 查询某一个月的预约汇总信息（页面日历控件显示）
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi627> webapi62505(CMi625 form) throws Exception ;
	
	/**
	 * 查询某一天的预约明细信息，可分页（页面列表显示）
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public WebApi62506_queryResult webapi63506(CMi625 form) throws Exception ;
}
