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
import com.yondervision.mi.form.AppApi90501Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: AppApi904Contorller 
* @Description: 知识库
* @author Caozhongyan
* @date 2016年9月23日 上午11:25:22   
* 
*/ 
@Controller
public class AppApi905Contorller {
	
	/**
	 * 知识库查询——查询知识结构信息
	 * 参数 request接受 strucid
	 * GET
	 * */
	@RequestMapping("/appapi90501.json")
	public String appapi90501(AppApi90501Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		StringBuffer url = new StringBuffer();
		url.append(PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"zskurl").trim()+"/FindStruc4Out.action");
		
		url.append("?strucid="+form.getStrucid());
		request.setAttribute("httpFlg", "1");
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		String rep = msm.sendGet(url.toString(), request.getCharacterEncoding());
		System.out.println("知识库查询——查询知识结构信息:"+rep);
		response.getOutputStream().write(rep.getBytes(encoding));
		response.getOutputStream().flush();
		response.getOutputStream().close();
		modelMap.clear();
		return "";
	}
	
	/**
	 * 知识库查询——查询知识结构信息
	 * 参数 request接受 strucid
	 * GET
	 * */
	@RequestMapping("/appapi90502.json")
	public String appapi90502(AppApi90501Form form , ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"zskurl").trim()+"/FindContentByTagid.action";
		
		
		HashMap hashMap = new HashMap();
		hashMap.put("strucid", form.getStrucid());
		hashMap.put("tagid", form.getTagid());
		
		request.setAttribute("httpFlg", "1");
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		String rep = msm.sendPost(url.toString(), hashMap, request.getCharacterEncoding());
		System.out.println("知识库查询——查询知识结构信息:"+rep);
		response.getOutputStream().write(rep.getBytes(encoding));
		response.getOutputStream().flush();
		response.getOutputStream().close();
		modelMap.clear();
		return "";
	}
	
}
