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
import com.yondervision.mi.form.WebApi70101Form;
import com.yondervision.mi.form.WebApi70102Form;
import com.yondervision.mi.form.WebApi70103Form;
import com.yondervision.mi.form.WebApi70104Form;
import com.yondervision.mi.form.WebApi70105Form;
import com.yondervision.mi.result.WebApi70104_queryResult;
import com.yondervision.mi.service.WebApi701Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi701Contorller 
* @Description:报刊期次维护
* @author gongq
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi701Contorller {
	@Autowired
	private WebApi701Service webApi701ServiceImpl;
	public void setWebApi701ServiceImpl(WebApi701Service webApi701ServiceImpl) {
		this.webApi701ServiceImpl = webApi701ServiceImpl;
	}

	/**
	 * 报刊期次填加
	 * @param form 报刊期次填加信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70101.{ext}")
	public String webapi70101(WebApi70101Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("报刊期次信息填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi701ServiceImpl.webapi70101(form);			
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page701/page70101";
	}
	
	/**
	 * 报刊期次删除
	 * @param form 报刊期次删除信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70102.{ext}")
	public String webapi70102(WebApi70102Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("报刊期次信息删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi701ServiceImpl.webapi70102(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page701/page70101";
	}
	
	/**
	 * 报刊期次修改
	 * @param form 报刊期次修改参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70103.{ext}")
	public String webapi70103(WebApi70103Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("报刊期次修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		int result = webApi701ServiceImpl.webapi70103(form);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("报刊期次Mi702","期次ID："+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
					"期次名称："+form.getItemval());
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page701/page70101";
	}
	
	/**
	 * 报刊期次查询
	 * @param form 报刊期次查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70104.{ext}")
	public String webapi70104(WebApi70104Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("报刊期次信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		WebApi70104_queryResult queryResult = webApi701ServiceImpl.webapi70104(form);
		if(queryResult.getList702().isEmpty()||queryResult.getList702().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("报刊期次Mi702", CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"报刊期次信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList702());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page701/page70101";
	}
	
	/**
	 * 报刊期次发布
	 * @param form 报刊期次发布信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70105.{ext}")
	public String webapi70105(WebApi70105Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("报刊期次发布");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		int result = webApi701ServiceImpl.webapi70105(form);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("报刊期次Mi702","期次ID："+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), 
					"期次名称："+form.getItemval());
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page701/page70101";		
	}
}
