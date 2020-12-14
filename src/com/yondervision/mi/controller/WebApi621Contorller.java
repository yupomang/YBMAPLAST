/**
 * 在线预约-预约时段模版维护
 */
package com.yondervision.mi.controller;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi621;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi621Example;
import com.yondervision.mi.service.WebApi621Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi621Contorller 
* @Description: 预约时段模版
* @author sunxl
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi621Contorller {
	@Autowired
	private WebApi621Service webApi621Service;

	public void setWebApi621Service(WebApi621Service webApi621Service) {
		this.webApi621Service = webApi621Service;
	}

	/**
	 * 时段模板信息增加
	 * @param form 时段模板信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62101.{ext}")
	public String webapi62101(CMi621 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段模版增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi621Service.webapi62101(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62104";
	}
	
	/**
	 * 时段模板信息删除
	 * @param form 时段模板信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62102.{ext}")
	public String webapi62102(CMi621 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段模版删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi621Service.webapi62102(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62104";
	}
	
	/**
	 * 时段模板信息修改
	 * @param form 时段模板信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62103.{ext}")
	public String webapi62103(CMi621 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段模版修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi621Service.webapi62103(form);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62104";
	}
	
	/**
	 * 时段模板信息查询（全部检索+模糊查询、分页）
	 * @param form 时段模板信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62104.{ext}")
	public String webapi62104(CMi621 form , ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();

		String businName = "时段模版查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
//		log.info(LOG.SELF_LOG.getLogText("contrller page="+page+",rows="+rows));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
   		JSONObject obj= webApi621Service.webapi62104(form);
   		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62104";
	}
}
