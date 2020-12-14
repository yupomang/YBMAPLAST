/**
 * 在线预约-预约时段明细维护
 */
package com.yondervision.mi.controller;

import java.util.List;

import net.sf.json.JSONArray;
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
import com.yondervision.mi.dto.CMi622;
import com.yondervision.mi.dto.Mi622;
import com.yondervision.mi.service.WebApi622Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi622Contorller 
* @Description: 预约时段明细
* @author sunxl
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi622Contorller {
	@Autowired
	private WebApi622Service webApi622Service;

	public void setWebApi622Service(WebApi622Service webApi622Service) {
		this.webApi622Service = webApi622Service;
	}

	/**
	 * 利率信息增加
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62201.{ext}")
	public String webapi62201(CMi622 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi622Service.webapi62201(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62204";
	}
	
	/**
	 * 利率信息删除
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62202.{ext}")
	public String webapi62202(CMi622 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi622Service.webapi62202(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62204";
	}
	
	/**
	 * 利率信息修改
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi62203.{ext}")
	public String webapi62203(CMi622 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi622Service.webapi62203(form);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62204";
	}
	
	/**
	 * 利率信息查询（全部检索+模糊查询、分页）
	 * @param form 利率信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi62204.{ext}")
	public String webapi62204(CMi622 form , ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.info(LOG.SELF_LOG.getLogText("contrller"));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
   		JSONObject obj= webApi622Service.webapi62204(form);
   		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62204";
	}
	@RequestMapping("/page62206.{ext}")
	public String page62206(Mi622 form,ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String businName = "时段明细查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
	
		
		List<Mi622> obj= webApi622Service.getBussTemplaDetail(form.getAppotemplateid());
		JSONArray arr= new JSONArray();
   		for(int i=0;i<obj.size();i++){
   			JSONObject json = new JSONObject();
   			json.put("appotpldetailid", obj.get(i).getAppotpldetailid());
   			json.put("timeinterval",obj.get(i).getTimeinterval());
   			arr.add(json);
   		} 
   		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result",arr);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page303/page62206";
	}
	@RequestMapping("/webapi622SaveSort.json")
	public String webapi622SaveSort(String datalist,ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi622Service.webapiUpdateSort(arr);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page303/page62301";
	} 
}
