/**
 * 
 */
package com.yondervision.mi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.ApiCtrlMessageUtil;
import com.yondervision.mi.form.CtrlApi00101Form;
import com.yondervision.mi.form.CtrlApi00102Form;
import com.yondervision.mi.form.CtrlApi00201Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

@Controller
public class ApiCtrl002Contorller {
	
	@RequestMapping("/apiCtrl00201.{ext}")
	public void apiCtrl00201(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api platform select all"));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/platform?method=query_all";
//		String value = JsonUtil.getGson().toJson(form);
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.toApiServer(url, null,request, response);
		log.info(LOG.END_BUSIN.getLogText("api platform select all"));		
	}
	
	@RequestMapping("/apiCtrl00202.{ext}")
	public void apiCtrl00202(CtrlApi00201Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api platform add"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/platform?method=add";
		String value = JsonUtil.getGson().toJson(form);
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.post(url, value,modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api platform add"));		
	}
	
	@RequestMapping("/apiCtrl00203.{ext}")
	public void apiCtrl00203(CtrlApi00201Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api platform update"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/platform?method=update";
		String value = JsonUtil.getGson().toJson(form);
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.post(url, value,modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api platform update"));		
	}
	@RequestMapping("/apiCtrl00204.{ext}")
	public void apiCtrl00204(CtrlApi00201Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api platform para select"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/platform?method=get_addon_all";
		String value = JsonUtil.getGson().toJson(form);
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.post(url, value,modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api platform para select"));		
	}
	@RequestMapping("/apiCtrl00205.{ext}")
	public void apiCtrl00205(CtrlApi00102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api platform para add"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/platform?method=update";
//		String value = JsonUtil.getGson().toJson(form);
		String value = "{\"name\":\""+form.getCenterid()+"\",\"addon\":{\""+form.getName()+"\":\""+form.getValue()+"\"}}";
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.post(url, value,modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api platform para add"));		
	}
	@RequestMapping("/apiCtrl00206.{ext}")
	public void apiCtrl00206(CtrlApi00102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api platform para delete"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/platform?method=delete_addon";
//		String value = JsonUtil.getGson().toJson(form);
		String[] para = form.getName().split(",");
		int sum = 0;
		StringBuffer value = new StringBuffer("{\"name\":\""+form.getCenterid()+"\",\"addon\":{");
		for(int i=0;i<para.length;i++){
			if(i!=0){
				value.append(",\""+para[i]+"\":null");
			}else{
				value.append("\""+para[i]+"\":null");
			}
						
		}
		value.append("}}");
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		System.out.println("value: "+value.toString());
		String msg = api.post(url, value.toString(),modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api platform para delete"));		
	}
	
	
	
}
