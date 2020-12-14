/**
 * 
 */
package com.yondervision.mi.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.dto.CMi201;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.result.WebApi20104_queryResult;
import com.yondervision.mi.service.WebApi101Service;
import com.yondervision.mi.service.WebApi103Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi103Contorller 
* @Description: 用户维护
* @author Sunxl
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi103Contorller {
	@Autowired
	private WebApi103Service webApi103ServiceImpl;

	public WebApi103Service getWebApi103ServiceImpl() {
		return webApi103ServiceImpl;
	}

	public void setWebApi103ServiceImpl(WebApi103Service webApi103ServiceImpl) {
		this.webApi103ServiceImpl = webApi103ServiceImpl;
	}
	/**
	 * 用户查询
	 * @param form 用户查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi10304.{ext}")
	public String webapi10304(CMi103 form , ModelMap modelMap, Integer page,Integer rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		
		JSONObject obj=webApi103ServiceImpl.webapi10304(form, page, rows,request,response);
		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page103/page10304";
	}
	@RequestMapping("/webapi10304Sum.{ext}")
	public String webapi10304Sum(CMi103 form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户累计信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		List<HashMap> list=webApi103ServiceImpl.webapi10304Sum(form,request,response);
		modelMap.put("data", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page103/page10304";
	}
	@RequestMapping("/webapi10304New.{ext}")
	public String webapi10304New(CMi103 form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户新增信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		List<HashMap> list=webApi103ServiceImpl.webapi10304New(form,request,response);
		modelMap.put("data", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page103/page10304";
	}
	@RequestMapping("/webapi10301All.{ext}")
	public String webapi10301All(CMi103 form , ModelMap modelMap, Integer page,Integer rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		
		JSONObject obj=webApi103ServiceImpl.webapi10301All(form, page, rows,request,response);
		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page103/page10304";
	}
	@RequestMapping("/webapi10301.{ext}")
	public String webapi10301(CMi103 form , ModelMap modelMap, Integer page,Integer rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		
		JSONObject obj=webApi103ServiceImpl.webapi10301(form, page, rows,request,response);
		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page103/page10304";
	}
	@RequestMapping("/webapi10305.{ext}")
	public String webapi10305(CMi103 form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户新增信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		List<HashMap> list=webApi103ServiceImpl.webapi10305(form,request,response);
		modelMap.put("data", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page103/page10305";
	}
	/**
	 * 用户查询
	 * @param form 用户查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi1030501.{ext}")
	public String webapi1030501(CMi103 form , ModelMap modelMap, Integer page,Integer rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		
		Map<String,Object> obj=webApi103ServiceImpl.webapi1030501(form, page, rows,request,response);
		for(String key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page103/page10305";
	}
}
