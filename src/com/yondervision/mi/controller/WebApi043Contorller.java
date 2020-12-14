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
import com.yondervision.mi.dto.CMi036;
import com.yondervision.mi.dto.CMi043;
import com.yondervision.mi.dto.CMi044;
import com.yondervision.mi.result.WebApi03604_queryResult;
import com.yondervision.mi.result.WebApi04304_queryResult;
import com.yondervision.mi.result.WebApi04404_queryResult;
import com.yondervision.mi.service.WebApi043Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONObject;

@Controller
public class WebApi043Contorller {
	@Autowired
	private WebApi043Service webApi043ServiceImpl;
	
	
	@RequestMapping("/webapi04301.{ext}")
	public String webapi04301(CMi043 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi043ServiceImpl.webapi04301(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page043/page04301";
	}

	@RequestMapping("/webapi04302.{ext}")
	public String webapi04302(CMi043 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi043ServiceImpl.webapi04302(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page043/page04302";
	}
	
	@RequestMapping("/webapi04303.{ext}")
	public String webapi04303(CMi043 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi043ServiceImpl.webapi04303(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page043/page04303";
	}
	
	
	
	@RequestMapping("/webapi04304.{ext}")
	public String webapi04304(CMi043 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi04304_queryResult queryResult = webApi043ServiceImpl.webapi04304(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList043());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page043/page04304";
	}

	@RequestMapping("/webapi04404.{ext}")
	public String webapi04404(CMi044 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "单笔限额明细查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi04404_queryResult queryResult = webApi043ServiceImpl.webapi04404(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList044());

		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page043/page04304";
	}
	
	@RequestMapping("/webapi03604.{ext}")
	public String webapi03604(CMi036 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "单日限额明细查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi03604_queryResult queryResult = webApi043ServiceImpl.webapi03604(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList036());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page043/page04304";
	}

	public WebApi043Service getWebApi043ServiceImpl() {
		return webApi043ServiceImpl;
	}


	public void setWebApi043ServiceImpl(WebApi043Service webApi043ServiceImpl) {
		this.webApi043ServiceImpl = webApi043ServiceImpl;
	}
}
