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
import com.yondervision.mi.common.message.WeiXinMessageUtil;
import com.yondervision.mi.form.WeiXin00102Form;
import com.yondervision.mi.form.WeiXin00201Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

@Controller
public class WeiXin002Contorller {
	
	@RequestMapping("/weixinapi00201.{ext}")
	public void weixinapi00201(WeiXin00201Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("微信功能添加"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=update&type=function";
		String value = JsonUtil.getGson().toJson(form);
		log.debug("微信功能配置地址："+url);
		log.debug("微信功能配置参数："+value);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, value, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信功能配置添加"));		
	}
	
	@RequestMapping("/weixinapi00202.{ext}")
	public void weixinapi00202(String centerid ,ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("微信功能查询"));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=query&type=function";		
		log.debug("微信功能配置地址："+url);
//		String value = JsonUtil.getGson().toJson(centerid);
		log.debug("微信功能配置参数：");		
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, centerid, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信功能查询"));		
	}
	
	@RequestMapping("/weixinapi00203.{ext}")
	public void weixinapi00203(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("微信功能查询"));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=query&type=function";		
		log.debug("微信功能配置地址："+url);
		log.debug("微信功能配置参数：");		
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, "", modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信功能查询"));		
	}
}
