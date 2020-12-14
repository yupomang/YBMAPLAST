package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi037;
import com.yondervision.mi.dto.CMi039;
import com.yondervision.mi.dto.CMi047;
import com.yondervision.mi.dto.Mi039;
import com.yondervision.mi.result.WebApi03704_queryResult;
import com.yondervision.mi.result.WebApi03904_queryResult;
import com.yondervision.mi.result.WebApi04704_queryResult;
import com.yondervision.mi.service.WebApi047Service;
import com.yondervision.mi.util.CommonUtil;
/**
 * 消息通知通讯录、监控主题、监控消息通知通历史记录
 * @author lixu
 *
 */
@Controller
public class WebApi047Contorller {
	@Autowired
	private WebApi047Service webApi047ServiceImpl;
	
	
	@RequestMapping("/webapi04701.{ext}")
	public String webapi04701(CMi047 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息通知通讯录新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi047ServiceImpl.webapi04701(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page047/page04701";
	}

	@RequestMapping("/webapi04702.{ext}")
	public String webapi04702(CMi047 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息通知通讯录删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi047ServiceImpl.webapi04702(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page047/page04702";
	}
	
	@RequestMapping("/webapi04703.{ext}")
	public String webapi04703(CMi047 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息通知通讯录修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi047ServiceImpl.webapi04703(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page047/page04703";
	}
	
	
	
	@RequestMapping("/webapi04704.{ext}")
	public String webapi04704(CMi047 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息通知通讯录查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi04704_queryResult queryResult = webApi047ServiceImpl.webapi04704(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList047());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page047/page04704";
	}

	
	@RequestMapping("/webapi03901.{ext}")
	public String webapi03901(CMi039 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "监控主题新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi047ServiceImpl.webapi03901(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page039/page03901";
	}

	@RequestMapping("/webapi03902.{ext}")
	public String webapi03902(CMi039 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "监控主题制删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi047ServiceImpl.webapi03902(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page039/page03902";
	}
	
	@RequestMapping("/webapi03903.{ext}")
	public String webapi03903(CMi039 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "监控主题修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi047ServiceImpl.webapi03903(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page039/page03903";
	}
	
	
	
	@RequestMapping("/webapi03904.{ext}")
	public String webapi03904(CMi039 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "监控主题查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi03904_queryResult queryResult = webApi047ServiceImpl.webapi03904(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList039());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page039/page03904";
	}
	/**
	 * 获取所有的监控主题
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webapi03905.{ext}")
	public String webapi03905(ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "获取所有监控主题";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		List<Mi039> list = webApi047ServiceImpl.webapi03905();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page039/page03905";
	}
	
	@RequestMapping("/webapi03704.{ext}")
	public String webapi03704(CMi037 form ,  ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控-监控消息通知通讯记录查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi03704_queryResult queryResult = webApi047ServiceImpl.webapi03704(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList037());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page037/page03704";
	}

	public WebApi047Service getWebApi047ServiceImpl() {
		return webApi047ServiceImpl;
	}

	public void setWebApi047ServiceImpl(WebApi047Service webApi047ServiceImpl) {
		this.webApi047ServiceImpl = webApi047ServiceImpl;
	}
}
