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
import com.yondervision.mi.form.AppApi60001Form;
import com.yondervision.mi.form.AppApi60002Form;
import com.yondervision.mi.result.AppApi60002Result;
import com.yondervision.mi.service.AppApi600Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @ClassName: AppApi600Contorller
 * @Description: 在线留言
 * @author gongqi
 * @date July 16, 2014 9:33:25 PM
 */
@Controller
public class AppApi600Contorller {
	@Autowired
	private AppApi600Service appApi600ServiceImpl = null;

	public void setAppApi600ServiceImpl(AppApi600Service appApi600ServiceImpl) {
		this.appApi600ServiceImpl = appApi600ServiceImpl;
	}

	/**
	 * 手机APP在线留言-发送
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi60001.{ext}")
	public String appapi60001(AppApi60001Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP留言发送");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"用户ID");
		}
		if(CommonUtil.isEmpty(form.getMessage())){
			log.error(ERROR.PARAMS_NULL.getLogText("message"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"留言内容");
		}
		appApi600ServiceImpl.appapi60001(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 手机APP在线留言-获取留言及回复
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi60002.{ext}")
	public String appapi60002(AppApi60002Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP留言-获取留言及回复");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"用户ID");
		}
		List<AppApi60002Result> resultList = appApi600ServiceImpl.appapi60002(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
}
