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
import com.yondervision.mi.util.security.AES;


/** 
* @ClassName: AppApi021Contorller 
* @Description: 单位明细账查询
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03   
* 
*/ 
@Controller
public class AppApi021Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}

	/**
	 * @deprecated 单位明细账查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02101.{ext}")
	public String appapi02101(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("结息对账查询");	
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
	 * 单位账户发生额明细批量查询下载
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi02102.{ext}")
	public String appapi02102(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位账户发生额明细批量查询下载");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
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
	 * @deprecated 单位托收金额信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02103.{ext}")
	public String appapi02103(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位托收金额信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
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
	 * @deprecated 单位发票打印
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02104.{ext}")
	public String appapi02104(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位发票打印");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
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
	 * @deprecated 跨机构调入信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02105.{ext}")
	public String appapi02105(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("跨机构调入信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
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
	 * @deprecated 单位-托收信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02106.{ext}")
	public String appapi02106(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位-托收信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
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
}
