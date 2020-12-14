package com.yondervision.mi.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.form.WebApi05801Form;
import com.yondervision.mi.service.WebApi058Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.WkfAccessTokenUtil;

import net.sf.json.JSONObject;

@Controller
public class WebApi058Contorller {
	
	@Autowired
	private WebApi058Service webApi058ServiceImpl;
	
	@RequestMapping("/webapi05801.{ext}")
	public String webapi05801(WebApi05801Form form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "统计新媒体及呼叫中心满意度评价！05801";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"wkfurl").trim()+"/ybmapzh/overview";
		String accessToken = WkfAccessTokenUtil.WKF_GET_TOKEN(form.getCenterId());
		System.out.println("客户满意度查询，新媒体客服记录，accessToken : "+accessToken);
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", accessToken);
		hashMap.put("centerid", form.getCenterId());
		hashMap.put("startdate", form.getStartdate());//YYYY-MM-DD
		hashMap.put("enddate", form.getEnddate());
		
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		System.out.println("新媒体客服调用评价统计返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("1007".equals(remap.get("code"))){
				WkfAccessTokenUtil.deleteTokenWithCouchBase(form.getCenterId());
				rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
				System.out.println("新媒体客服调用评价统计返回信息："+rep);
				remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
			}
		}
//		rep = JsonUtil.getGson().toJson(remap);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", remap);
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}

	@RequestMapping("/webapi05802.{ext}")
	public String webapi05802(WebApi05801Form form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "统计新媒体及呼叫中心满意度评价明细信息05802！";
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"wkfurl").trim()+"/ybmapzh/details";
		String accessToken = WkfAccessTokenUtil.WKF_GET_TOKEN(form.getCenterId());
		System.out.println("客户满意度明细查询，新媒体客服记录，accessToken : "+accessToken);
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", accessToken);
		hashMap.put("centerid", form.getCenterId());
		hashMap.put("startdate", form.getStartdate());//YYYY-MM-DD
		hashMap.put("enddate", form.getEnddate());
		hashMap.put("selecttype", form.getSelecttype());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		System.out.println("新媒体客服调用评价统计返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		
//		if(!CommonUtil.isEmpty(remap.get("code"))){
//			if("0000".equals(remap.get("code"))){
//				remap.put("recode", "000000");
//			}else{
//				remap.put("recode", remap.get("code"));
//			}
//		}
//		rep = JsonUtil.getGson().toJson(remap);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", remap);
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	
	
	@RequestMapping("/webapi05803.{ext}")
	public String webapi05803(WebApi05801Form form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "用户体验评价统计-柜面业务服务评价";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		JSONObject obj = webApi058ServiceImpl.webapi05803(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", obj);
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	
	@RequestMapping("/webapi05804.{ext}")
	public String webapi05804(WebApi05801Form form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "柜面业务服务评价-不满意原因分类";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		JSONObject obj = webApi058ServiceImpl.webapi05804(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", obj);
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	@RequestMapping("/webapi05805.{ext}")
	public String webapi05805(WebApi05801Form form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "柜面业务服务评价-不满意原因分类-业务政策";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		JSONObject obj = webApi058ServiceImpl.webapi05805(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", obj);
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	@RequestMapping("/webapi05806.{ext}")
	public String webapi05806(WebApi05801Form form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "柜面业务服务评价-不满意原因分类-服务态度";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		JSONObject obj = webApi058ServiceImpl.webapi05806(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", obj);
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}

	@RequestMapping("/webapi05807.{ext}")
	public String webapi05807(WebApi05801Form form , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "柜面业务服务评价-不满意原因分类-服务态度-柜员信息";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		JSONObject obj = webApi058ServiceImpl.webapi05807(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", obj);
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	public WebApi058Service getWebApi058ServiceImpl() {
		return webApi058ServiceImpl;
	}

	public void setWebApi058ServiceImpl(WebApi058Service webApi058ServiceImpl) {
		this.webApi058ServiceImpl = webApi058ServiceImpl;
	}
}
