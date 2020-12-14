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
import com.yondervision.mi.form.WeiXin00101Form;
import com.yondervision.mi.form.WeiXin00102Form;
import com.yondervision.mi.form.WeiXin00103Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

@Controller
public class WeiXin001Contorller {
	
	@RequestMapping("/weixinapi00101.{ext}")
	public void weixinapi00101(WeiXin00101Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("微信中心配置添加"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=add&type=channel";
		String value = JsonUtil.getGson().toJson(form);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, value,modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信中心配置添加"));		
	}
	
	@RequestMapping("/weixinapi00102.{ext}")
	public void weixinapi00102(WeiXin00102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("微信中心配置查询"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=query&type=channel";
		String value = JsonUtil.getGson().toJson(form);
		if("".equals(form.getRegionId())){
			url = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=queryAll&type=channel";
		}
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, value,modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信中心配置查询"));		
	}
	
	@RequestMapping("/weixinapi00103.{ext}")
	public void weixinapi00103(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("微信中心配置查询全部"));		
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=queryAll&type=channel";		
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, "",modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信中心配置查询全部"));		
	}
	
	@RequestMapping("/weixinapi00104.{ext}")
	public void weixinapi00104(WeiXin00101Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("微信中心配置修改"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=update&type=channel";
		String value = JsonUtil.getGson().toJson(form);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, value,modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信中心配置修改"));		
	}
	
	@RequestMapping("/weixinapi00105.{ext}")
	public void weixinapi00105(WeiXin00103Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("微信用户信息查询"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/luser";
		if(!form.getCenterId().equals("")){
			String value = "{\"regionId\":\""+form.getCenterId()+"\",\"next_openid\":\""+form.getNextopenid()+"\",\"pageSize\":\""+form.getRows()+"\",\"pageNumber\":\""+form.getPage()+"\"}";
			WeiXinMessageUtil weixin = new WeiXinMessageUtil();
			weixin.post(url, value,modelMap, request, response);
		}		
		log.info(LOG.END_BUSIN.getLogText("微信用户信息查询"));		
	}
}
