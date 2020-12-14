/**
 * 区域信息维护
 */
package com.yondervision.mi.controller;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi202;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.service.WebApi202Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi202Contorller 
* @Description: 区域信息维护
* @author gongqi
* @date Oct 17, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi202Contorller {
	@Autowired
	private WebApi202Service webApi202Service;

	public void setWebApi202Service(WebApi202Service webApi202Service) {
		this.webApi202Service = webApi202Service;
	}

	/**
	 * 区域信息增加
	 * @param form 区域信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi20201.json")
	public String webapi20201(CMi202 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "区域信息增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi202Service.webapi20201(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));

		return "page202/page20201";
	}
	
	/**
	 * 区域信息删除
	 * @param form 区域信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi20202.json")
	public String webapi20202(CMi202 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "区域信息删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		webApi202Service.webapi20202(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page202/page20201";
	}
	
	/**
	 * 区域信息修改
	 * @param form 区域信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi20203.json")
	public String webapi20203(CMi202 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "区域信息修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		webApi202Service.webapi20203(form);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page202/page20201";
	}
	
	/**
	 * 区域信息查询
	 * @param form 区域信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi20204.json")
	public String webapi20204(CMi202 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "区域信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		List<Mi202> list = webApi202Service.webapi20204(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		modelMap.put("total", list.size());
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page202/page20201";
	}
	
	/**
	 * 区域信息顺序修改
	 * @param form 区域信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi20205.{ext}")
	public String webapi20205(String datalist,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "区域信息顺序修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));		
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi202Service.webapi20206(arr);
	    

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page202/page20201";
	} 
	
}
