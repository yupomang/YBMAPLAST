package com.yondervision.mi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.form.AppApi50001Form;
import com.yondervision.mi.form.AppApi90401Form;
import com.yondervision.mi.form.AppApi90402Form;
import com.yondervision.mi.form.AppApi90403Form;
import com.yondervision.mi.form.AppApi90404Form;
import com.yondervision.mi.form.AppApi90405Form;
import com.yondervision.mi.form.AppApi90406Form;
import com.yondervision.mi.form.AppApi90407Form;
import com.yondervision.mi.form.AppApi90408Form;
import com.yondervision.mi.form.AppApi90409Form;
import com.yondervision.mi.form.AppApi90410Form;
import com.yondervision.mi.form.AppApi90411Form;
import com.yondervision.mi.form.AppApi90412Form;
import com.yondervision.mi.form.AppApi90413Form;
import com.yondervision.mi.form.AppApi90414Form;
import com.yondervision.mi.form.AppApi90417Form;
import com.yondervision.mi.form.AppApi90419Form;
import com.yondervision.mi.form.AppApi90420Form;
import com.yondervision.mi.form.AppApi90421Form;
import com.yondervision.mi.form.AppApi90424Form;
import com.yondervision.mi.result.AppApi90415Result;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.service.WebApi302Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.WkfAccessTokenUtil;

/** 
* @deprecated 新媒体客服
* @ClassName: AppApi904Contorller 
* @Description: TODO
* @author Caozhongyan
* @date 2016年9月23日 上午11:25:22   
* 
*/ 
@Controller
public class AppApi904Contorller {
	
	@Autowired
	private WebApi029Service webApi029Service;
	@Autowired
	private WebApi302Service webApi302Service;
	
	public WebApi029Service getWebApi029Service() {
		return webApi029Service;
	}

	public void setWebApi029Service(WebApi029Service webApi029Service) {
		this.webApi029Service = webApi029Service;
	}

	public WebApi302Service getWebApi302Service() {
		return webApi302Service;
	}

	public void setWebApi302Service(WebApi302Service webApi302Service) {
		this.webApi302Service = webApi302Service;
	}

	/**
	 * 如何获得认证token
	 * 参数 request接受 appKey,appSecret,platform
	 * POST
	 * */
	@RequestMapping("/appapi90401.json")
	public void appapi90401(AppApi90401Form form ,ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("如何获得认证token");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		System.out.println("###90401...:"+gdmap.get("url").trim());
		String url = gdmap.get("url").trim() + "/accesstoken";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/accesstoken";	
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
				
		String data = JsonUtil.getGson().toJson(form);
		HashMap map = JsonUtil.getGson().fromJson(data, HashMap.class);
		String rep = msm.sendPost(url, map, request.getCharacterEncoding());
		log.info("【appapi90401.json】调用新媒体客服——【获得认证token】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}		
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 应用用户注册接口
	 * 参数 request接受  accessToken,app_user_name,app_user_nickname,app_user_passwd
	 * POST
	 * */
	@RequestMapping("/appapi90402.json")
	public void appapi90402(AppApi90402Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户注册接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/appuser/register/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/appuser/register/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
				form.setApp_user_nickname(mi029.getUsername());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("app_user_nickname", form.getApp_user_nickname());
		hashMap.put("app_user_passwd", form.getApp_user_passwd());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90402.json】调用新媒体客服——【应用用户注册接口】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}
	
	/**
	 * 应用用户修改注册信息
	 * 参数 request接受  accessToken,app_user_name,app_user_nickname,app_user_passwd
	 * POST
	 * */
	@RequestMapping("/appapi90403.json")
	public void appapi90403(AppApi90403Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户修改注册信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim()+"/appuser/update/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/appuser/update/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
				form.setApp_user_nickname(mi029.getUsername());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("app_user_nickname", form.getApp_user_nickname());
		hashMap.put("app_user_passwd", form.getApp_user_passwd());
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90403.json】调用新媒体客服——【应用用户修改注册信息接口】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	

	/**
	 * 应用用户注册信息查询
	 * 参数 request接受  accessToken,app_user_name
	 * POST
	 * */
	@RequestMapping("/appapi90404.json")
	public void appapi90404(AppApi90404Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户注册信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/appuser/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/appuser/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90404.json】调用新媒体客服——【应用用户注册信息查询】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 用户发送消息
	 * 参数 request接受  accessToken,app_id,app_user_name,app_user_nickname,app_plat_name,msg_data
	 * POST
	 * */
	@RequestMapping("/appapi90405.json")
	public void appapi90405(AppApi90405Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("用户发送消息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/custom/sendmsg/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/custom/sendmsg/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		HashMap hashMap = new HashMap();
		if(!CommonUtil.isEmpty(form.getUserId())){
			
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			System.out.println("appapi90405  个人所属机构号："+mi029.getFreeuse1());
			if(!CommonUtil.isEmpty(mi029)){
				
				if("00087121".equals(mi029.getFreeuse1())){
					form.setApp_id("2");
				}
				
				form.setApp_user_name(mi029.getPersonalid());
				form.setApp_user_nickname(mi029.getUsername());
				AppApi50001Form form50001 = new AppApi50001Form();
				form50001.setCenterId(form.getCenterId());
				form50001.setChannel(form.getChannel());
				Mi031 mi031 = webApi029Service.webapi02920(mi029 ,form50001 ,request ,response);
				if(!CommonUtil.isEmpty(mi031)){
					hashMap.put("plat_user_name", mi031.getUserid());
				}
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}else{
			hashMap.put("plat_user_name", form.getApp_user_name());
		}
		System.out.println("getApp_id  个人所属机构号："+form.getApp_id());
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_id", form.getApp_id()); 
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("app_user_nickname", form.getApp_user_nickname());
		hashMap.put("app_plat_name", form.getApp_plat_name());
		hashMap.put("msg_data", form.getMsg_data());
		hashMap.put("msg_cmd", form.getMsg_cmd());
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90405.json】调用新媒体客服——【用户发送消息】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 应用用户获得评分模版
	 * 参数 request接受  accessToken
	 * POST
	 * */
	@RequestMapping("/appapi90406.json")
	public void appapi90406(AppApi90406Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户获得评分模版");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/custom/scoreinfo/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/custom/scoreinfo/token";
		
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90406.json】调用新媒体客服——【应用用户获得评分模版】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 用户回复评价
	 * 参数 request接受  accessToken,app_user_name,chat_id,score
	 * POST
	 * */
	@RequestMapping("/appapi90407.json")
	public void appapi90407(AppApi90407Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("用户回复评价");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/custom/score/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/custom/score/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("chat_id", form.getChat_id());
		hashMap.put("score", form.getScore());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90407.json】调用新媒体客服——【用户回复评价】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 用户关闭会话
	 * 参数 request接受  accessToken,app_user_name,chat_id,score
	 * POST
	 * */
	@RequestMapping("/appapi90408.json")
	public void appapi90408(AppApi90408Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("用户关闭会话");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/custom/close/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/custom/close/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("chat_id", form.getChat_id());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90408.json】调用新媒体客服——【用户关闭会话】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 应用用户查询历史会话列表
	 * 参数 request接受  accessToken,app_user_name,score_state,currentPage,pageSize,begindate,enddate
	 * POST
	 * */
	@RequestMapping("/appapi90409.json")
	public void appapi90409(AppApi90409Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户查询历史会话列表");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/custom/chatlog/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/custom/chatlog/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("score_state", form.getScore_state());
		hashMap.put("currentPage", form.getCurrentPage());
		hashMap.put("pageSize", form.getPageSize());
		hashMap.put("begindate", form.getBegindate());
		hashMap.put("enddate", form.getEnddate());
		
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90409.json】调用新媒体客服——【用户查询历史会话列表】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 应用用户查询会话下消息信息
	 * 参数 request接受 accessToken,app_user_name,chat_id,currentPage,pageSize,begindate,enddate
	 * GET
	 * */
	@RequestMapping("/appapi90410.json")
	public void appapi90410(AppApi90410Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户查询会话下消息信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/msg/custom/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()+"/msg/custom/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("chat_id", form.getChat_id());
		hashMap.put("currentPage", form.getCurrentPage());
		hashMap.put("pageSize", form.getPageSize());
		hashMap.put("begindate", form.getBegindate());
		hashMap.put("enddate", form.getEnddate());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90410.json】调用新媒体客服——【用户查询会话下消息信息】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 应用用户创建留言
	 * 参数 request接受  accessToken,app_user_name,info_plat,information,user_name,user_contact
	 * POST
	 * */
	@RequestMapping("/appapi90411.json")
	public void appapi90411(AppApi90411Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户创建留言");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/info/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/info/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("info_plat", form.getInfo_plat());
		hashMap.put("information", form.getInformation());
		hashMap.put("user_name", form.getUser_name());
		hashMap.put("user_contact", form.getUser_contact());
		hashMap.put("info_desc", form.getInfo_desc());
		//2017-06-19新增 syw
		hashMap.put("show_info", form.getShow_info());
		//20107-08-17新增 "img_urls":["/downloadimg/info20170814160659","/downloadimg/info20170814160659"]
		hashMap.put("img_urls", JsonUtil.getGson().toJson(form.getImg_urls()));
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90411.json】调用新媒体客服——【用户创建留言】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 应用用户查询留言列表
	 * 参数 request接受  accessToken,app_user_name
	 * POST
	 * */
	@RequestMapping("/appapi90412.json")
	public void appapi90412(AppApi90412Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户查询留言列表");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/info/find/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/info/find/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("currentPage", form.getCurrentPage());
		hashMap.put("pageSize", form.getPageSize());
		hashMap.put("key", form.getKey());
		hashMap.put("isreturn", form.getIsreturn());
		hashMap.put("info_desc", form.getInfo_desc());
		//2017-06-19 新增 syw 
		hashMap.put("info_plat", form.getInfo_plat());
		hashMap.put("read", form.getRead());
		log.info("【appapi90412.json】调用新媒体客服——【用户查询留言列表】上传信息："+JsonUtil.getGson().toJson(hashMap));
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90412.json】调用新媒体客服——【用户查询留言列表】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 应用用户接收消息
	 * 参数 request接受  accessToken,app_user_name
	 * GET
	 * */
	@RequestMapping("/appapi90413.json")
	public void appapi90413(AppApi90413Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户接收消息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/custom/mqttToken/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()+"/custom/mqttToken/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90413.json】调用新媒体客服——【用户接收消息】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	
	/**
	 * 查询应用用户历史聊天消息记录
	 * 参数 request接受  accessToken,app_user_name,chat_id,currentPage,pageSize
	 * POST
	 * */
	@RequestMapping("/appapi90414.json")
	public void appapi90414(AppApi90414Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("查询应用用户历史聊天消息记录");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/msg/custom/latelymsg/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/msg/custom/latelymsg/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("chat_id", form.getChat_id());
		hashMap.put("currentPage", form.getCurrentPage());
		hashMap.put("pageSize", form.getPageSize());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90414.json】调用新媒体客服——【用户历史聊天消息记录】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 *  发送图片信息
	 * @param modelMap
	 * @param importFile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi90415.json")
	public void appapi90415(AppApi90414Form form, ModelMap modelMap, @RequestParam MultipartFile importFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("发送图片信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ApiUserContext.getInstance();
		
		String filepath=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"push_wkf_img").trim()+"/"+System.currentTimeMillis()+"_"+Thread.currentThread().getId()+".jpg";
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/gridfs/chat";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/gridfs/chat";
		FileOutputStream fs = new FileOutputStream(filepath);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		InputStream stream = importFile.getInputStream();
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close(); 
		String rep=WkfAccessTokenUtil.WKF_upload_ZH(form.getCenterId(), filepath ,form.getAccessToken());		
//		modelMap.put("wkfdata", obj );
		
//		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		HashMap hashMap = new HashMap();
//		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		AppApi90415Result app90415 = JsonUtil.getGson().fromJson(rep, AppApi90415Result.class);
		if(!CommonUtil.isEmpty(app90415.getCode())){
			if("0000".equals(app90415.getCode())){
				app90415.setRecode("000000");
			}else{
				app90415.setRecode( app90415.getRecode());
			}
		}
		rep = JsonUtil.getGson().toJson(app90415);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		response.getOutputStream().write(rep.getBytes(encoding));
		
		try{
			File file = new File(filepath);
			file.delete();
	
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
	
	/**
	 * 获取聊天图片
	 * 
	 * POST
	 * */
//	@RequestMapping("/appapi90416.json")
//	public String appapi90416( AppApi90416Form form, ModelMap modelMap, @RequestParam MultipartFile importFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
//		
//		
//		Logger log = LoggerUtil.getLogger();
//		form.setBusinName("查询应用用户历史聊天消息记录");	
//		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));
////		ApiUserContext.getInstance();
//		
//		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/gridfs/downloadimg"+form.getFileName();
//		
//		String rep = msm.sendGet(url, request.getCharacterEncoding());
//		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
//		if(!CommonUtil.isEmpty(remap.get("code"))){
//			if("0000".equals(remap.get("code"))){
//				remap.put("recode", "000000");
//			}else{
//				remap.put("recode", remap.get("code"));
//			}
//		}
//		rep = JsonUtil.getGson().toJson(remap);
//		response.getOutputStream().write(rep.getBytes(request.getCharacterEncoding()));
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		//============================================================
//		
//		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//				"wkfurl").trim()+"/gridfs/downloadimg/{fileName}";
//		
//		HashMap hashMap = new HashMap();		
//		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
//		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
//		if(!CommonUtil.isEmpty(remap.get("code"))){
//			if("0000".equals(remap.get("code"))){
//				remap.put("recode", "000000");
//			}else{
//				remap.put("recode", remap.get("code"));
//			}
//		}
//		rep = JsonUtil.getGson().toJson(remap);
//		response.getOutputStream().write(rep.getBytes(request.getCharacterEncoding()));
//		return "";
//	}
	
	/**
	 * 查询问卷列表
	 * 参数 request接受  accessToken,starttime,endtime,flag,tpl_type,tpl_name,currentPage,pageSize
	 * GET
	 * */
	@RequestMapping("/appapi90417.json")
	public void appapi90417(AppApi90417Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("查询问卷列表");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/question_temp/list/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()
//			+"/question_temp/list/token";
		
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("starttime", form.getStarttime());
		hashMap.put("endtime", form.getEndtime());
		hashMap.put("flag", form.getFlag());
		hashMap.put("tpl_type", form.getTpl_type());
		
		hashMap.put("tpl_name", form.getTpl_name());
		hashMap.put("currentPage", form.getCurrentPage());
		hashMap.put("pageSize", form.getPageSize());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90417.json】调用新媒体客服——【查询问卷列表】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 查看（预览）问卷明细接口
	 * 参数 request接受  accessToken,app_user_name
	 * GET
	 * */
	@RequestMapping("/appapi90418.json")
	public void appapi90418(AppApi90417Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("查看（预览）问卷明细接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/question_temp/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()
//					+"/question_temp/token";
		
		
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("tpl_id", form.getTpl_id());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90418.json】调用新媒体客服——【查看问卷明细】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				//remap.put("recode", "000000");
				rep = rep.substring(0, 1)+"\"recode\":\"000000\","+rep.substring(1);
			}else{
				//remap.put("recode", remap.get("code"));
				rep = rep.substring(0, 1)+"\"recode\":\"999999\","+rep.substring(1);
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		//rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 提交问卷答复
	 * 参数 request接受  accessToken,app_user_name
	 * GET
	 * */
	@RequestMapping("/appapi90419.json")
	public void appapi90419(AppApi90419Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提交问卷答复");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/question_temp/answer/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()
//			+"/question_temp/answer/token";
		
		
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("tpl_id", form.getTpl_id());
		
		hashMap.put("tpl_name", form.getTpl_name());
		hashMap.put("task_score", form.getTask_score());
		hashMap.put("answer_id", form.getAnswer_id());
		hashMap.put("answer_name", form.getAnswer_name());
		hashMap.put("answer_ip", form.getAnswer_ip());
		hashMap.put("array", form.getArray());
		
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90419.json】调用新媒体客服——【提交问卷答复】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 * 应用用户批量创建留言
	 * 参数 request接受  accessToken,app_user_name,info_plat,infos
	 * POST
	 * */
	@RequestMapping("/appapi90420.json")
	public void appapi90420(AppApi90420Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户批量创建留言");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		//ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/info/batch/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()
//			+"/info/batch/token";
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		//hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("info_plat", form.getInfo_plat());
		
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setChannel(form.getChannel());
		String infos = form.getInfos();
		JSONArray newja = new JSONArray();
		try
		{
			JSONArray ja = JSONArray.fromObject(infos);
			for(int i=0;i<ja.size();i++)
			{
				JSONObject json = JSONObject.fromObject(ja.get(i));
				JSONObject newjson = new JSONObject();
				newjson.put("information", json.getString("information"));
				newjson.put("user_name", json.getString("user_name"));
				newjson.put("user_contact", json.getString("user_contact"));
				form1.setUserId(json.getString("app_user_name"));
				if(!CommonUtil.isEmpty(form.getUserId())){
					Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
					if(!CommonUtil.isEmpty(mi029)){
						newjson.put("app_user_name",mi029.getPersonalid());
						//form.setApp_user_name(mi029.getPersonalid());
					}else{
//						throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
					}
				}
				newjson.put("info_desc", json.getString("info_desc"));
				newjson.put("show_info", json.getString("show_info"));
				newja.add(newjson);
			}
		}catch(Exception e)
		{
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"上传参数【infos】格式不正确！");
		}
		hashMap.put("infos", newja.toString());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90420.json】调用新媒体客服——【用户批量创建留言】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	
	/**
	 * 获取图文消息数据
	 * 参数 request接受  
	 * GET
	 * */
	@RequestMapping("/appapi90421.json")
	public void appapi90421(AppApi90421Form form , ModelMap modelMap, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("获取图文消息数据");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		HashMap map = new HashMap();
		try {
			map = this.webApi302Service.getTextImage(form);
			map.put("recode", Constants.WEB_SUCCESS_CODE);
			map.put("msg", Constants.WEB_SUCCESS_MSG);
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			map.put("recode", "999999");
			map.put("msg", tre.getMessage());
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			map.put("recode", "999999");
			map.put("msg", tre.getMessage());
		}finally{
			String encoding = "UTF-8";
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(JsonUtil.getGson().toJson(map).getBytes(encoding));
		}
		System.out.println(form.getBusinName()+"end");
	}
	
	/**
	 * 问卷的所有回复 选项投票情况统计
	 * 参数 request接受  accessToken,tpl_id
	 * GET
	 * */
	@RequestMapping("/appapi90422.json")
	public void appapi90422(AppApi90419Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("选项投票情况统计");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/statistics/answerDetails/token";
//		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()
//			+"/statistics/answerDetails/token";
 
		url = url + "?accessToken=" + form.getAccessToken() 
				+ "&tpl_id=" + form.getTpl_id();
		
		String rep = msm.sendGet(url, request.getCharacterEncoding());
		log.info("【appapi90422.json】调用新媒体客服——【留言已读】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	/**
	 *  发送图片信息——留言上传图片
	 * @param modelMap
	 * @param importFile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/appapi90423.json")
//	public void appapi90423(AppApi90414Form form, ModelMap modelMap, @RequestParam MultipartFile[] importFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//		log.info("【appapi90423.json】调用新媒体客服——【留言上传图片】");
//		form.setBusinName("发送图片信息——留言上传图片");	
//		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));
//		int imgsend = 0;
//		String urlKey = "/gridfs/chat";
//		String[] filepath=new String[importFile.length];
//		for(int i=0;i<importFile.length;i++){
//			filepath[i] = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//					"push_wkf_img").trim()+"/"+System.currentTimeMillis()+"_"+Thread.currentThread().getId()+".jpg";
//			log.info("【appapi90423.json】调用新媒体客服——【留言上传图片】filepath"+i+" : "+filepath[i]);
//			FileOutputStream fs = new FileOutputStream(filepath[i]);
//			byte[] buffer = new byte[1024 * 1024];
//			int bytesum = 0;
//			int byteread = 0;
//			InputStream stream = importFile[i].getInputStream();
//			while ((byteread = stream.read(buffer)) != -1) {
//				bytesum += byteread;
//				fs.write(buffer, 0, byteread);
//				fs.flush();
//			}
//			fs.close();
//			stream.close(); 
//		}
//		
//		String rep=WkfAccessTokenUtil.WKF_upload_Imgs(urlKey ,form.getCenterId(), filepath ,form.getAccessToken());		
//		HashMap hashMap = new HashMap();
//		AppApi90415Result app90415 = JsonUtil.getGson().fromJson(rep, AppApi90415Result.class);
//		if(!CommonUtil.isEmpty(app90415.getCode())){
//			if("0000".equals(app90415.getCode())){
//				app90415.setRecode("000000");
//			}else{
//				app90415.setRecode( app90415.getRecode());
//			}
//		}
//		rep = JsonUtil.getGson().toJson(app90415);
//		String encoding = "UTF-8";
//		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
//			encoding = "UTF-8";
//		}else {
//			encoding = request.getCharacterEncoding();
//		}
//		response.getOutputStream().write(rep.getBytes(encoding));
//		
//		try{
//			for(int j=0;j<filepath.length;j++){
//				File file = new File(filepath[j]);
//				file.delete();
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//				
//	} 
	
	
	/**
	 * @deprecated 留言已读 POST
	 * @date 2017-08-17
	 * @param form
	 * @param modelMap
	 * @param request
	 * 		accessToken
	 * 		app_user_name
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/appapi90424.json")
	public void appapi90424(AppApi90424Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("应用用户查询留言列表");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId() , "config");
		String url = gdmap.get("url").trim() + "/info/read/token";
		AppApi50001Form form1 = new AppApi50001Form();
		form1.setCenterId(form.getCenterId());
		form1.setUserId(form.getUserId());
		form1.setChannel(form.getChannel());
		
		if(!CommonUtil.isEmpty(form.getUserId())){
			Mi029 mi029 = webApi029Service.webapi02918(form1 ,modelMap ,request ,response);
			if(!CommonUtil.isEmpty(mi029)){
				form.setApp_user_name(mi029.getPersonalid());
			}else{
//				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户不存在"); 
			}
		}
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", form.getAccessToken());
		hashMap.put("app_user_name", form.getApp_user_name());
		hashMap.put("info_id", form.getInfo_id());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		log.info("【appapi90423.json】调用新媒体客服——【留言已读】返回信息："+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		response.getOutputStream().write(rep.getBytes(encoding));
	}
	
	public static void main(String[] args) {
//		HashMap hashMap = new HashMap();
//		hashMap.put("accessToken", "accessToken");
//		hashMap.put("info_plat", "info_plat");
//		AppApi50001Form form1 = new AppApi50001Form();
//		String infos = "[{\"information\":\"留言内容\"," +
//				"\"user_name\":\"留言者姓名\"," +
//				"\"user_contact\":\"留言者联系方式\"," +
//				"\"app_user_name\":\"user01\"," +
//				"\"info_desc\":{\"type_key\":\"gj\",\"type_name\":\"归集\",\"title\":\"留言标题信息\"}," +
//				"\"show_info\":[{\"key\":\"标题\",\"value\":\"info_desc.title\",\"order\":\"1\"},{\"key\":\"留言渠道\",\"value\":\"plat_desc\",\"order\":\"2\"}]}," +
//				"{\"information\":\"留言内容\"," +
//				"\"user_name\":\"留言者姓名\"," +
//				"\"user_contact\":\"留言者联系方式\"," +
//				"\"app_user_name\":\"user01\"," +
//				"\"info_desc\":{\"type_key\":\"gj\",\"type_name\":\"归集\",\"title\":\"留言标题信息\"}," +
//				"\"show_info\":[{\"key\":\"标题\",\"value\":\"info_desc.title\",\"order\":\"1\"},{\"key\":\"留言渠道\",\"value\":\"plat_desc\",\"order\":\"2\"}]}]";
//		JSONArray newja = new JSONArray();
//		try
//		{
//			System.out.println(infos);
//			JSONArray ja = JSONArray.fromObject(infos);
//			for(int i=0;i<ja.size();i++)
//			{
//				System.out.println(ja.get(i));
//				JSONObject json = JSONObject.fromObject(ja.get(i));
//				JSONObject newjson = new JSONObject();
//				newjson.put("information", json.getString("information"));
//				newjson.put("user_name", json.getString("user_name"));
//				newjson.put("user_contact", json.getString("user_contact"));
//				form1.setUserId(json.getString("app_user_name"));
//				newjson.put("app_user_name","app_user_name_"+i);
//				newjson.put("info_desc", json.getString("info_desc"));
//				newjson.put("show_info", json.getString("show_info"));
//				newja.add(newjson);
//			}
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		hashMap.put("infos", newja.toString());
//		JSONArray ja = JSONArray.fromObject(hashMap.get("infos"));
//		Iterator iter = hashMap.entrySet().iterator();
//		while (iter.hasNext()) {
//			Map.Entry entry = (Map.Entry) iter.next();
//			Object key = entry.getKey();
//			Object val = entry.getValue();
//			System.out.println(key+":"+val);
//		}
		Map<String, String> gdmap = WkfAccessTokenUtil.getAppSecretAndAppKey("00087100" , "config");
		System.out.println("###90401...:"+gdmap.get("url").trim());
	}
	
	
}
