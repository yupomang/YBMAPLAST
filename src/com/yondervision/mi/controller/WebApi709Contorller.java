/**
 * 
 */
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
import com.yondervision.mi.dto.Mi709;
import com.yondervision.mi.service.WebApi709Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONObject;

@Controller
public class WebApi709Contorller {
	@Autowired
	private WebApi709Service webApi709Service;
	
	
	@RequestMapping("/webapi70901.{ext}")
	public String webapi70901(Mi709 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目与服务增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi709Service.webapi70901(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}

	@RequestMapping("/webapi70902.{ext}")
	public String webapi70902(Mi709 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目与服务删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi709Service.webapi70902(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page709/page70901";
	}
	
	@RequestMapping("/webapi70903.{ext}")
	public String webapi70903(Mi709 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目与服务修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi709Service.webapi70903(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page709/page70903";
	}
	
	@RequestMapping("/webapi70904.{ext}")
	public String webapi70904(Mi709 form ,Integer page,Integer rows, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目与服务查询分页";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		JSONObject obj = webApi709Service.webapi70904(form,page,rows);
		modelMap.clear();
		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page709/page70904";
	}

	public WebApi709Service getWebApi709Service() {
		return webApi709Service;
	}

	public void setWebApi709Service(WebApi709Service webApi709Service) {
		this.webApi709Service = webApi709Service;
	}
}
