/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.form.AppApi020Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi00401Result;
import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JavaBeanUtil;
import com.yondervision.mi.util.security.AES;


/** 
* @ClassName: AppApi020Contorller 
* @Description: 单位基本信息查询
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03   
* 
*/ 
@Controller
public class AppApi020Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	
	
	
	/**
	 * @deprecated 单位基本信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02001.{ext}")
	public String appapi02001(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位基本信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		//住建委网站不校验签名
		if(!"30".equals(form.getChannel())){
			ApiUserContext.getInstance();
		}
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(rep.getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}	
	
	/**
	 * @deprecated 经办人信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02002.{ext}")
	public String appapi02002(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("经办人信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}	
	
	/**
	 * @deprecated 单位信息公共查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02003.{ext}")
	public String appapi02003(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位信息公共查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}	
}
	
