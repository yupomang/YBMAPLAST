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
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi20101Form;
import com.yondervision.mi.form.AppApi20102Form;
import com.yondervision.mi.form.AppApi20103Form;
import com.yondervision.mi.result.AppApi20101Result;
import com.yondervision.mi.result.AppApi20102Result;
import com.yondervision.mi.result.AppApi20103Result;
import com.yondervision.mi.service.AppApi201Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * 业务咨询APP接口控制类
 * @author LinXiaolong
 *
 */
@Controller
public class AppApi201Contorller {
	
	@Autowired
	private AppApi201Service appApi201Service;
	
	/**
	 * 业务咨询（子）项查询
	 * @param form centerId、bussinesstype
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi20101.json")
	public String appApi20101(AppApi20101Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "业务咨询（子）项查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			//业务处理
			List<AppApi20101Result> result = this.getAppApi201Service().appApi20101(form);
			
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			modelMap.put("result", result);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		
		return "";
	}
	
	/**
	 * 公共条件查询
	 * @param form consultitemid
	 * @param modelMap 
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi20102.json")
	public String appapi20102(AppApi20102Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "公共条件查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			//业务处理
			List<AppApi20102Result> result = this.getAppApi201Service().appApi20102(form);
			
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			modelMap.put("result", result);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		
		return "";
	}

	/**
	 * 向导步骤（内容）查询
	 * 
	 * @param form
	 *            consultitemid、titleid（业务咨询子项ID）、conditionid1（可空）、conditionid2（可空
	 *            ）、conditionid3（可空）、conditionid4（可空）、conditionid5（可空）、
	 *            conditionid6（可空）、conditionid7（可空）、conditionid8（可空）
	 * @param modelMap
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi20103.json")
	public String appapi20103(AppApi20103Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "向导步骤（内容）查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			//业务处理
			List<AppApi20103Result> result = this.getAppApi201Service().appApi20103(form);
			
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			modelMap.put("result", result);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		
		return "";
	}
	
	

	public AppApi201Service getAppApi201Service() {
		return appApi201Service;
	}

	public void setAppApi201Service(AppApi201Service appApi201Service) {
		this.appApi201Service = appApi201Service;
	}
	
	
	
	
}
