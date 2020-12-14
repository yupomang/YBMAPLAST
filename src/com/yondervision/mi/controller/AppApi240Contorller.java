package com.yondervision.mi.controller;


import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** 
* @ClassName: AppApi240Contorller
* @Description: 发布在省数据平台的接口(长三角 一网通办 异地缴存证明)
* @author luolin
* @date 04 08, 2020 PM
*/ 
@Controller
public class AppApi240Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	/**
	 * 返回申请开具证明信息接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/appapi00240.{ext}")
	public String appapi00240(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("返回申请开具证明信息接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", "00057400");
		String rep=msgSendApi001Service.send(request, response);
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		return "/index";
	}
	
	/**
	 * 返回查询缴存证明信息接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/appapi00241.{ext}")
	public String appapi00241(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("返回查询缴存证明信息接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", "00057400");
		String rep=msgSendApi001Service.send(request, response);
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}

	/**
	 * 接收申请注销证明接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/appapi00242.{ext}")
	public String appapi00242(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("接收申请注销证明接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", "00057400");
		String rep=msgSendApi001Service.send(request, response);
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}

	/**
	 * 接收回执信息接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/appapi00243.{ext}")
	public String appapi00243(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("接收回执信息接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", "00057400");
		String rep=msgSendApi001Service.send(request, response);
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}


	/**
	 * 接收结清信息接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/appapi00244.{ext}")
	public String appapi00244(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("接收结清信息接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", "00057400");
		String rep=msgSendApi001Service.send(request, response);
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}

	/**
	 * 返回贷款台账查询接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/appapi00245.{ext}")
	public String appapi00245(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("返回贷款台账查询接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", "00057400");
		String rep=msgSendApi001Service.send(request, response);
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("rep="+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
}
