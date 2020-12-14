/**
 * 
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
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi040;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.result.WebApi04004_queryResult;
import com.yondervision.mi.service.WebApi040Service;
import com.yondervision.mi.util.CommonUtil;

@Controller
public class WebApi040Contorller {
	@Autowired
	private WebApi040Service webApi040ServiceImpl;
	
	
	@RequestMapping("/webapi04001.{ext}")
	public String webapi04001(CMi040 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "应用增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi040ServiceImpl.webapi04001(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page040/page04001";
	}

	@RequestMapping("/webapi04002.{ext}")
	public String webapi04002(CMi040 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "应用删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi040ServiceImpl.webapi04002(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page040/page04001";
	}
	
	@RequestMapping("/webapi04003.{ext}")
	public String webapi04003(CMi040 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "应用修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi040ServiceImpl.webapi04003(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page040/page04003";
	}
	
	/**
	 * 应用查询分页
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webapi04004.{ext}")
	public String webapi04004(CMi040 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "应用查询分页";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		WebApi04004_queryResult queryResult = webApi040ServiceImpl.webapi04004(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList040());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page040/page04004";
	}
	
	@RequestMapping("/webapi04005.{ext}")
	public String webapi04005(CMi040 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "认证设置（新增和修改）";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi040ServiceImpl.webapi04005(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page040/page04005";
	}
	
	@RequestMapping("/webapi04006.{ext}")
	public String webapi04006(CMi040 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "认证应用KEY";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		String value = EncryptionByMD5.getMD5((form.getPid()+System.currentTimeMillis()).getBytes());
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("appkey", value);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	@RequestMapping("/webapi04007.{ext}")
	public String webapi04007(CMi040 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "获取应用列表";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		List<Mi040> list = webApi040ServiceImpl.webapi04007(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("applist", list);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	
	public WebApi040Service getWebApi040ServiceImpl() {
		return webApi040ServiceImpl;
	}
	public void setWebApi040ServiceImpl(WebApi040Service webApi040ServiceImpl) {
		this.webApi040ServiceImpl = webApi040ServiceImpl;
	}
}
