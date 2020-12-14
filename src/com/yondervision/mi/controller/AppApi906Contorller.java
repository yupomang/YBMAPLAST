package com.yondervision.mi.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.form.AppApi50001Form;
import com.yondervision.mi.form.AppApi90601Form;
import com.yondervision.mi.form.AppApi90602Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: AppApi904Contorller 
* @Description:	综合服务平台工单接口
* @author Caozhongyan
* @date 2016年9月24日 上午12:25:22   
* 
*/ 
@Controller
public class AppApi906Contorller {

	/**
	 * 工单系统——如何获得认证token
	 * 参数 request接受 key,secret
	 * POST
	 * */
	@RequestMapping("/appapi90601.json")
	public String appapi90601(AppApi90601Form form ,ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"gdurl").trim()+"/service/tokens";	
		HashMap hashMap = new HashMap();
		hashMap.put("key", form.getKey());
		hashMap.put("secret", form.getSecret());
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		response.getOutputStream().write(rep.getBytes(request.getCharacterEncoding()));
		return "";
	}
	
	/**
	 * 工单系统——用户获取待办任务
	 * 参数 request接受  token_，user_id
	 * POST
	 * */
	@RequestMapping("/appapi90602.json")
	public String appapi90602(AppApi90602Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"gdurl").trim()+"/activitis/wo/service/todos";
		
		HashMap hashMap = new HashMap();
		hashMap.put("token_", form.getToken_());
		hashMap.put("user_id", form.getUser_id());
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		response.getOutputStream().write(rep.getBytes(request.getCharacterEncoding()));
		return "";
	}
	
	/**
	 * 工单系统——用户签收待办任务
	 * 参数 request接受  token_，user_id，task_id
	 * POST
	 * */
	@RequestMapping("/appapi90603.json")
	public String appapi90603(AppApi90602Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"gdfurl").trim()+"/activitis/wo/service/claim";
		HashMap hashMap = new HashMap();
		hashMap.put("token_", form.getToken_());
		hashMap.put("user_id", form.getUser_id());
		hashMap.put("task_id", form.getTask_id());
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		response.getOutputStream().write(rep.getBytes(request.getCharacterEncoding()));
		return "";
	}
	

	/**
	 * 工单系统——用户取消签收待办任务
	 * 参数 request接受  accessToken,app_user_name
	 * POST
	 * */
	@RequestMapping("/appapi90604.json")
	public String appapi90604(AppApi90602Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"gdfurl").trim()+"/activitis/wo/service/unclaim";
		HashMap hashMap = new HashMap();
		hashMap.put("token_", form.getToken_());
		hashMap.put("user_id", form.getUser_id());
		hashMap.put("task_id", form.getTask_id());
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		response.getOutputStream().write(rep.getBytes(request.getCharacterEncoding()));
		return "";
	}
	
}
