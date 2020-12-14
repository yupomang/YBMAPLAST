package com.yondervision.mi.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi057;
import com.yondervision.mi.result.WebApi05701_queryResult;
import com.yondervision.mi.service.WebApi057Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONObject;

@Controller
public class WebApi057Contorller {
	@Autowired
	private WebApi057Service webApi057ServiceImpl;
	
	public WebApi057Service getWebApi057ServiceImpl() {
		return webApi057ServiceImpl;
	}

	public void setWebApi057ServiceImpl(WebApi057Service webApi057ServiceImpl) {
		this.webApi057ServiceImpl = webApi057ServiceImpl;
	}

	//渠道接口
	@RequestMapping("/webapi05701.{ext}")
	public String webapi05701(CMi057 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "统一客户视图插件权限新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi057ServiceImpl.webapi05701(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page055/page05501";
	}

	@RequestMapping("/webapi05702.{ext}")
	public String webapi05702(CMi057 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "统一客户视图插件权限删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi057ServiceImpl.webapi05702(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page055/page05502";
	}
	
	@RequestMapping("/webapi05703.{ext}")
	public String webapi05703(String oldFuncid, CMi057 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "统一客户视图插件权限修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi057ServiceImpl.webapi05703(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page055/page05503";
	}
	@RequestMapping("/webapi05704.{ext}")
	public String webapi05704(CMi057 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "统一客户视图插件权限查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		JSONObject obj = webApi057ServiceImpl.webapi05704(form);
		modelMap.clear();
		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page055/page05504";
	}
	
}
