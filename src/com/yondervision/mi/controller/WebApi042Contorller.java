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
import com.yondervision.mi.dto.CMi042;
import com.yondervision.mi.result.WebApi04204_queryResult;
import com.yondervision.mi.service.WebApi042Service;
import com.yondervision.mi.util.CommonUtil;

@Controller
public class WebApi042Contorller {
	@Autowired
	private WebApi042Service webApi042ServiceImpl;
	
	
	@RequestMapping("/webapi04201.{ext}")
	public String webapi04201(CMi042 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi042ServiceImpl.webapi04201(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page042/page04201";
	}

	
	@RequestMapping("/webapi04202.{ext}")
	public String webapi04202(String centerid , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		
		
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page042/page04201";
	}
	
	@RequestMapping("/webapi04204.{ext}")
	public String webapi04204(CMi042 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi04204_queryResult queryResult = webApi042ServiceImpl.webapi04204(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList042());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page042/page04204";
	}

	public WebApi042Service getWebApi042ServiceImpl() {
		return webApi042ServiceImpl;
	}

	public void setWebApi042ServiceImpl(WebApi042Service webApi042ServiceImpl) {
		this.webApi042ServiceImpl = webApi042ServiceImpl;
	}
}
