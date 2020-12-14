package com.yondervision.mi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.yondervision.mi.dto.CMi103;

/** 
* @ClassName: WebApi103Service 
* @Description:目前其点处理接口
* @author Sunxl
* @date Sep 29, 2013 2:55:31 PM   
* 
*/ 
public interface WebApi103Service {
	/**
	 * 查询
	 */
	public JSONObject webapi10304(CMi103 form, Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception;

	List<HashMap> webapi10304New(CMi103 form,HttpServletRequest request, HttpServletResponse response) throws Exception;

	List<HashMap> webapi10304Sum(CMi103 form,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public JSONObject webapi10301(CMi103 form,Integer page,Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public JSONObject webapi10301All(CMi103 form,Integer page,Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception ;
	List<HashMap> webapi10305(CMi103 form,HttpServletRequest request, HttpServletResponse response) throws Exception;

	public Map<String,Object> webapi1030501(CMi103 form, Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
