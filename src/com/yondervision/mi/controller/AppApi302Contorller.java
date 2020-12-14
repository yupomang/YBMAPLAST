/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.controller
 * 文件名：     AppApi302Contorller.java
 * 创建日期：2013-10-23
 */
package com.yondervision.mi.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi124;
import com.yondervision.mi.form.AppApi30201Form;
import com.yondervision.mi.form.AppApi30202Form;
import com.yondervision.mi.form.AppApi30203Form;
import com.yondervision.mi.form.AppApi30205Form;
import com.yondervision.mi.form.AppApi30207Form;
import com.yondervision.mi.result.AppApi30201Result;
import com.yondervision.mi.result.AppApi30202Result;
import com.yondervision.mi.service.AppApi302Service;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * 推送短消息
 * @author LinXiaolong
 *
 */
@Controller
public class AppApi302Contorller {
	
	@Autowired
	private AppApi302Service appApi302Service;
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	
	/**
	 * @return the appApi302Service
	 */
	public AppApi302Service getAppApi302Service() {
		return appApi302Service;
	}


	/**
	 * @param appApi302Service the appApi302Service to set
	 */
	public void setAppApi302Service(AppApi302Service appApi302Service) {
		this.appApi302Service = appApi302Service;
	}

	/**
	 * 推送信息查询
	 * 
	 * @param form
	 *            userId、centerId、rowsum、messageid（可空）
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30201.json")
	public String appApi30201(AppApi30201Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "推送信息查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			//业务处理
			List<AppApi30201Result> result = this.getAppApi302Service().appApi30201(form);
			modelMap.clear();
			modelMap.put("result", result);
			
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
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
	 * 推送信息设置为已读
	 * 
	 * @param form
	 *            messageid
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30202.json")
	public String appApi30202(AppApi30202Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "推送信息设置为已读";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			//业务处理
			this.getAppApi302Service().appApi30202(form);
			
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
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
	 * 查询详细内容
	 * 
	 * @param form
	 *            userId、centerId、rowsum、messageid（可空）
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30203.json")
	public String appApi30203(AppApi30203Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "推送信息查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			//业务处理
			AppApi30201Result result = this.getAppApi302Service().appApi30203(form);
			
			modelMap.put("result", result);
			
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
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
	 * 主题信息查询
	 * 
	 * @param form
	 *            userid
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30204.json")
	public String appApi30204(AppApi30202Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "推送主题信息查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			ApiUserContext.getInstance();
			//业务处理
			List<Mi007> list1 = this.codeListApi001Service.getCodeList("00000000", "message_topic_type");
			List<AppApi30202Result> appApi30202Result = this.getAppApi302Service().appApi30204(form,list1);
			modelMap.clear();
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			modelMap.put("result", appApi30202Result);
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
	 * 主题信息设置
	 * 
	 * @param form
	 *            topictypeids豆号分隔
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30205.json")
	public String appApi30205(AppApi30205Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "主题信息设置";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			ApiUserContext.getInstance();
			//业务处理
			this.getAppApi302Service().appApi30205(form);
			modelMap.clear();
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
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
	 * 免打扰信息查询
	 * 
	 * @param form
	 *            topictypeids豆号分隔
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30206.json")
	public String appApi30206(AppApi30207Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "主题信息设置";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			ApiUserContext.getInstance();
			//业务处理
			Mi124 mi124 = this.getAppApi302Service().appApi30206(form);			
			modelMap.clear();
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			modelMap.put("nodisturb", mi124.getNodisturb());
			modelMap.put("starttime", mi124.getStarttime());
			modelMap.put("endtime", mi124.getEndtime());
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
	 * 免打扰时间设置
	 * 
	 * @param form
	 *            messageid
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30207.json")
	public String appApi30207(AppApi30207Form form,  ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		String businName = "推送信息设置为已读";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			ApiUserContext.getInstance();
			//业务处理
			this.getAppApi302Service().appApi30207(form);
			modelMap.clear();
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
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

	
	public CodeListApi001Service getCodeListApi001Service() {
		return codeListApi001Service;
	}


	public void setCodeListApi001Service(CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}
	
}
