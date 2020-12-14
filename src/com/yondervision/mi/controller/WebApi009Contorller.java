/**
 * 贷款试算-利率维护
 */
package com.yondervision.mi.controller;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi109;
import com.yondervision.mi.service.WebApi009Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi009Contorller 
* @Description: 利率维护
* @author gongqi
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi009Contorller {
	@Autowired
	private WebApi009Service webApi009Service;

	public void setWebApi009Service(WebApi009Service webApi009Service) {
		this.webApi009Service = webApi009Service;
	}

	/**
	 * 利率信息增加
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi00901.{ext}")
	public String webapi00901(CMi109 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "利率信息增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi009Service.webapi00901(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page009/page00904";
	}
	
	/**
	 * 利率信息删除
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi00902.{ext}")
	public String webapi00902(CMi109 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "利率信息删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi009Service.webapi00902(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page009/page00904";
	}
	
	/**
	 * 利率信息修改
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi00903.{ext}")
	public String webapi00903(CMi109 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "利率信息修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi009Service.webapi00903(form);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page009/page00904";
	}
	
	/**
	 * 利率信息查询（全部检索+模糊查询、分页）
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi00904.{ext}")
	public String webapi00904(CMi109 form , ModelMap modelMap, Integer page,Integer rows){
		Logger log = LoggerUtil.getLogger();

		String businName = "利率信息查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.info(LOG.SELF_LOG.getLogText("contrller page="+page+",rows="+rows));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
   		JSONObject obj= webApi009Service.webapi00904(form, page, rows);
   		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page009/page00904";
	}
	
}
