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
import com.yondervision.mi.form.WeiXin00301Form;
import com.yondervision.mi.form.WeiXin00302Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

@Controller
public class WeiXin003Contorller {
	
	@RequestMapping("/weixinapi00301.{ext}")
	public void weixinapi00301(WeiXin00302Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		log.info(LOG.START_BUSIN.getLogText("微信菜单查询"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=queryMenu&type=policy";
		String value = JsonUtil.getGson().toJson(form);
		log.debug("微信菜单查询地址："+url);
		log.debug("微信菜单查询参数："+value);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, value, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信菜单查询"));		
	}
	
	@RequestMapping("/weixinapi00302.{ext}")
	public void weixinapi00302(WeiXin00301Form Form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("微信菜单添加"));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=update&type=policy";		
		log.debug("微信菜单添加地址："+url);
		log.debug("微信菜单添加参数："+Form.getValue());		
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, Form.getValue(), modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信菜单添加"));		
	}
	
	@RequestMapping("/weixinapi00303.{ext}")
	public void weixinapi00303(WeiXin00302Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("微信策略查询"));
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=queryPolicy&type=policy";		
		String value = JsonUtil.getGson().toJson(form);
		log.debug("微信策略查询地址："+url);
		log.debug("微信策略查询参数："+value);		
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, value, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("微信策略查询"));		
	}
	
	@RequestMapping("/weixinapi00304.{ext}")
	public void weixinapi00304(WeiXin00301Form Form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
