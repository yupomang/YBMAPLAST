/**
 * 
 */
package com.yondervision.mi.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.result.WebApi40101_queryResult;
import com.yondervision.mi.result.WebApi40102_queryResult;
import com.yondervision.mi.result.WebApi40103_queryResult;
import com.yondervision.mi.service.WebApi401Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi401Contorller 
* @Description: APP用户注册信息查询
* @author Caozhongyan
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi401Contorller {
	@Autowired
	private WebApi401Service webApi401ServiceImpl;

	public WebApi401Service getWebApi401ServiceImpl() {
		return webApi401ServiceImpl;
	}

	public void setWebApi401ServiceImpl(WebApi401Service webApi401ServiceImpl) {
		this.webApi401ServiceImpl = webApi401ServiceImpl;
	}

	/**
	 * APP用户注册信息查询
	 * @param form APP用户注册信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40101.{ext}")
	public String webapi40101(CMi103 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("APP用户注册信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		WebApi40101_queryResult queryResult = webApi401ServiceImpl.webapi40101(form);
		if(queryResult.getList103().isEmpty()||queryResult.getList103().size()==0){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP用户注册信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList103());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page401/page40101";
	}
	
	/**
	 * APP用户设备信息查询
	 * @param form APP用户设备信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40102.{ext}")
	public String webapi40102(CMi103 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("APP用户设备信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// TODO 业务处理
		WebApi40102_queryResult queryResult = webApi401ServiceImpl.webapi40102(form);
		if(queryResult.getList105().isEmpty()||queryResult.getList105().size()==0){			
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP用户设备信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList105());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page401/page40102";
	}
	
	/**
	 * APP用户通讯信息查询
	 * @param form APP用户通讯信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40103.{ext}")
	public String webapi40103(CMi103 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("APP用户通讯信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// TODO 业务处理
		WebApi40103_queryResult queryResult = webApi401ServiceImpl.webapi40103(form);
		if(queryResult.getList104().isEmpty()||queryResult.getList104().size()==0){		
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP用户通讯信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList104());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page401/page40103";
	}
}
