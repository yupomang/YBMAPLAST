/**
 * 
 */
package com.yondervision.mi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi020Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: AppApi024Contorller 
* @Description: 缴存登记信息查询
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03   
* 
*/ 
@Controller
public class AppApi024Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	
	/**
	 * @deprecated 缴存登记信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02401.{ext}")
	public String appapi02401(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("缴存入账查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
	
	/**
	 * @deprecated 缴存入账信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02402.{ext}")
	public String appapi02402(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("缴存入账信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
}
	
