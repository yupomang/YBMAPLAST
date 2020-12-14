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
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

@Controller
public class ApiCtrl001Contorller {
	
	@RequestMapping("/apiCtrl00101.{ext}")
	public void apiCtrl00101(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api group select all"));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/group?method=query_all";
//		String value = JsonUtil.getGson().toJson(form);
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.toApiServer(url, null,request, response);
		log.info(LOG.END_BUSIN.getLogText("api group select all"));		
	}
	
	@RequestMapping("/apiCtrl00102.{ext}")
	public void apiCtrl00102(CtrlApi00101Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api group add"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/group?method=add";
		String value = JsonUtil.getGson().toJson(form);
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.post(url, value,modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api group add"));		
	}
	
	@RequestMapping("/apiCtrl00103.{ext}")
	public void apiCtrl00103(CtrlApi00101Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api group update"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/group?method=update";
		String value = JsonUtil.getGson().toJson(form);
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.post(url, value,modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api group update"));		
	}
	@RequestMapping("/apiCtrl00104.{ext}")
	public void apiCtrl00104(CtrlApi00101Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api group para select"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/group?method=get_addon_all";
		String value = JsonUtil.getGson().toJson(form);
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.post(url, value,modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api group para select"));		
	}
	@RequestMapping("/apiCtrl00105.{ext}")
	public void apiCtrl00105(CtrlApi00102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api group para add"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/group?method=update_addon";
//		String value = JsonUtil.getGson().toJson(form);
		String value = "{\"name\":\""+form.getCenterid()+"\",\"addon\":{\""+form.getName()+"\":\""+form.getValue()+"\"}}";
		ApiCtrlMessageUtil api = new ApiCtrlMessageUtil();
		api.post(url, value,modelMap,request, response);
		log.info(LOG.END_BUSIN.getLogText("api group para add"));		
	}
	@RequestMapping("/apiCtrl00106.{ext}")
	public void apiCtrl00106(CtrlApi00102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("api group para delete"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "apiCtrlUrl").trim()+"/group?method=delete_addon";
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
		log.info(LOG.END_BUSIN.getLogText("api group para delete"));		
	}
	
	
	
}
