package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi601;
import com.yondervision.mi.dto.Mi601;
import com.yondervision.mi.dto.Mi602;
import com.yondervision.mi.form.WebApi60001Form;
import com.yondervision.mi.form.WebApi60004Form;
import com.yondervision.mi.result.WebApi60001_queryResult;
import com.yondervision.mi.service.WebApi600Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi600Contorller 
* @Description: 在线留言信息查询
* @author gongqi  
* @date July 16, 2014 9:33:25 PM   
*/ 
@Controller
public class WebApi600Contorller {
	@Autowired
	private WebApi600Service webApi600ServiceImpl;
	public void setWebApi600ServiceImpl(WebApi600Service webApi600ServiceImpl) {
		this.webApi600ServiceImpl = webApi600ServiceImpl;
	}

	/**
	 * 在线留言信息查询-分页
	 * @param form 在线留言信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi60001.{ext}")
	public String webapi60001(WebApi60001Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("在线留言信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// 业务处理
		WebApi60001_queryResult queryResult = webApi600ServiceImpl.webapi60001(form);
		if(queryResult.getList601().isEmpty()||queryResult.getList601().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"在线留言信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("totalPage", queryResult.getTotalPage());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList601());		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page600/page60001";
	}
	
	/**
	 * 留言回复
	 * @param form 留言回复参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi60002.{ext}")
	public String webapi60002(CMi601 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("留言回复");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));	
		if(CommonUtil.isEmpty(form.getUserid())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue(),"用户ID");
		}
		// 业务处理
		int i = webApi600ServiceImpl.webapi60002(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page600/page60002";
	}
	
	/**
	 * 在线留言信息查询
	 * @param form 在线留言信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi60003.{ext}")
	public String webapi60003(WebApi60001Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("在线留言信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// 业务处理
		List<Mi601> mi601List = webApi600ServiceImpl.webapi60003(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("resultList", mi601List);		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page600/page60001";
	}
	
	/**
	 * 留言回复信息查询
	 * @param form 在线留言信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi60004.{ext}")
	public String webapi60004(WebApi60004Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("留言回复信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// 业务处理
		List<Mi602> mi602List = webApi600ServiceImpl.webapi60004(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("resultList", mi602List);		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page600/page60001";
	}
}
