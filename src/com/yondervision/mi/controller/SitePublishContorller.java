package com.yondervision.mi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.service.SitePublishService;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: SitePublishContorller 
* @Description: 站点发布
* @author gongqi  
* @date July 18, 2014 9:33:25 PM   
*/ 
@Controller
public class SitePublishContorller {
	@Autowired
	private SitePublishService sitePublishServiceImpl;
	public void setSitePublishServiceImpl(SitePublishService sitePublishServiceImpl) {
		this.sitePublishServiceImpl = sitePublishServiceImpl;
	}


	/**
	 * 首页静态页面生成
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/indexStatic.json")
	public String indexStatic(String centerId, ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "首页静态页面生成";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		if(CommonUtil.isEmpty(centerId)){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		sitePublishServiceImpl.indexStatic(centerId,request);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	/**
	 * 栏目静态页面生成
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/classficationStatic.json")
	public String classficationStatic(String centerId, ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "栏目静态页面生成";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		if(CommonUtil.isEmpty(centerId)){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		sitePublishServiceImpl.classficationStatic(centerId, request);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	/**
	 * 内容静态页面生成
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/contentStatic.json")
	public String contentStatic(String centerId, ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "内容静态页面生成";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		if(CommonUtil.isEmpty(centerId)){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		sitePublishServiceImpl.classficationStatic(centerId, request);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
}
