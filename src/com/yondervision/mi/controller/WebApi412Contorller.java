/**
 * 消息模板
 */
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
import com.yondervision.mi.dto.CMi411;
import com.yondervision.mi.dto.Mi412;
import com.yondervision.mi.result.WebApi41104Query_queryResult;
import com.yondervision.mi.service.WebApi412Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONArray;

@Controller
public class WebApi412Contorller {
	@Autowired
	private WebApi412Service webApi412ServiceImpl;
	
	
	@RequestMapping("/webapi411Add.{ext}")
	public void webapi411Add(CMi411 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息模板增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		String id = webApi412ServiceImpl.webapi411Add(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("templateid", id);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
	}

	@RequestMapping("/webapi411Remove.{ext}")
	public void webapi411Remove(CMi411 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息模板删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi412ServiceImpl.webapi411Remove(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
	}
	
	@RequestMapping("/webapi411Update.{ext}")
	public void webapi411Update(CMi411 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息模板修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi412ServiceImpl.webapi411Update(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
	}
	
	@RequestMapping("/webapi411UpdateSend.{ext}")
	public void webapi411UpdateSend(CMi411 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息模板修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi412ServiceImpl.webapi411UpdateSend(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
	}
	
	/**
	 * 消息模板查询分页
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webapi411Query.{ext}")
	public void webapi411Query(CMi411 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息模板查询分页";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		WebApi41104Query_queryResult queryResult = webApi412ServiceImpl.webapi411Query(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList411());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}

	@RequestMapping("/webapi411Detail.{ext}")
	public void webapi411Detail(CMi411 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "根据模板查询要素";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		List<Mi412> list = webApi412ServiceImpl.webapi411Detail(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}
	
	@RequestMapping("/webapi412003.{ext}")
	public void webapi412003(String datalist , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "消息模板要素修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(datalist));
		JSONArray arr= JSONArray.fromObject(datalist);
		webApi412ServiceImpl.webapi41203(arr);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		log.info(LOG.END_BUSIN.getLogText(businName));
	}
	
	
	public WebApi412Service getWebApi412ServiceImpl() {
		return webApi412ServiceImpl;
	}

	public void setWebApi412ServiceImpl(WebApi412Service webApi412ServiceImpl) {
		this.webApi412ServiceImpl = webApi412ServiceImpl;
	}
}
