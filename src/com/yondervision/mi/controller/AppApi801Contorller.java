package com.yondervision.mi.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.form.AppApi80102Form;
import com.yondervision.mi.form.AppApi80103Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.AppApi401Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.impl.AppApi110ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.security.AES;

@Controller
public class AppApi801Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	@Autowired
	private AppApi103Service appApi103ServiceImpl = null;
	public void setAppApi103ServiceImpl(AppApi103Service appApi103ServiceImpl) {
		this.appApi103ServiceImpl = appApi103ServiceImpl;
	}
	
	@Autowired
	private AppApi401Service appApi401ServiceImpl = null;
	public void setAppApi401ServiceImpl(AppApi401Service appApi401ServiceImpl) {
		this.appApi401ServiceImpl = appApi401ServiceImpl;
	}
	
	@Autowired
	private AppApi110ServiceImpl appApi110Service = null;
	public void setAppApi110Service(AppApi110ServiceImpl appApi110Service) {
		this.appApi110Service = appApi110Service;
	}
	
	/**
	 * 单位余额查询(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80101.{ext}")
	public String appApi80101(AppApi40102Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位余额查询(宁波旧业务系统专用)");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue(),"用户ID");
		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		//***************************************************
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1=(HttpServletRequest) request;
			HashMap m=new HashMap(request.getParameterMap());
			AES aes = new AES();
			String sfz = "";
			if(!CommonUtil.isEmpty(m.get("userId"))){
				String usid = (String)request.getParameter("userId");
				sfz = aes.decrypt(usid);
			}
			Mi103 mi103 = appApi103ServiceImpl.appApi10301Select(sfz);
			m.put("bodyCardNumber", mi103.getCertinum());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		//***************************************************		
		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 单位账户对账(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80102.{ext}")
	public String appapi80102(AppApi80102Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位账户对账(宁波旧业务系统专用)");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue(),"用户ID");
		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		//***************************************************
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1=(HttpServletRequest) request;
			HashMap m=new HashMap(request.getParameterMap());
			AES aes = new AES();
			String sfz = "";
			if(!CommonUtil.isEmpty(m.get("userId"))){
				String usid = (String)request.getParameter("userId");
				sfz = aes.decrypt(usid);
			}
			Mi103 mi103 = appApi103ServiceImpl.appApi10301Select(sfz);
			m.put("bodyCardNumber", mi103.getCertinum());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		//***************************************************		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 单位利息凭证(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80103.{ext}")
	public String appapi80103(AppApi80103Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位利息凭证(宁波旧业务系统专用)");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue(),"用户ID");
		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		//***************************************************
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1=(HttpServletRequest) request;
			HashMap m=new HashMap(request.getParameterMap());
			AES aes = new AES();
			String sfz = "";
			if(!CommonUtil.isEmpty(m.get("userId"))){
				String usid = (String)request.getParameter("userId");
				sfz = aes.decrypt(usid);
			}
			Mi103 mi103 = appApi103ServiceImpl.appApi10301Select(sfz);
			m.put("bodyCardNumber", mi103.getCertinum());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		//***************************************************		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 单位用户登陆验证(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80104.{ext}")
	public String appapi80104(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位用户登陆验证(宁波旧业务系统专用)");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		request.setAttribute("centerId", form.getCenterId());
		AES aes = new AES();
		form.setUserId(aes.decrypt(form.getUserId()));
		form.setFullName(aes.decrypt(form.getFullName()));
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		//form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		
		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
		List<Mi103> list = appApi401ServiceImpl.appapi40110(form);
		if(list.size()>0){
			m.remove("appid");					
			m.put("appid", list.get(0).getUserId());
		}else{
			m.remove("appid");					
			m.put("appid", "");
		}
		
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		
		String mssage = msgSendApi001Service.send(wrapRequest, response);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";
	}	
	@RequestMapping("/appapi80105.{ext}")
	public String appApi80105(AppApi40102Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位余额查询(宁波旧业务系统专用)");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ApiUserContext.getInstance();
//		if(CommonUtil.isEmpty(form.getUserId())){
//			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
//			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
//					.getValue(),"用户ID");
//		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		//***************************************************
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1=(HttpServletRequest) request;
			HashMap m=new HashMap(request.getParameterMap());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		//***************************************************		
		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 单位账户对账(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80106.{ext}")
	public String appapi80106(AppApi80102Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位账户对账(宁波旧业务系统专用)");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ApiUserContext.getInstance();
//		if(CommonUtil.isEmpty(form.getUserId())){
//			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
//			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
//					.getValue(),"用户ID");
//		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		//***************************************************
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1=(HttpServletRequest) request;
			HashMap m=new HashMap(request.getParameterMap());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		//***************************************************		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 单位利息凭证(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80107.{ext}")
	public String appapi80107(AppApi80103Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位利息凭证(宁波旧业务系统专用)");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ApiUserContext.getInstance();
//		if(CommonUtil.isEmpty(form.getUserId())){
//			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
//			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
//					.getValue(),"用户ID");
//		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		//***************************************************
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1=(HttpServletRequest) request;
			HashMap m=new HashMap(request.getParameterMap());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		//***************************************************		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 单位用户登陆验证(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80108.{ext}")
	public String appapi80108(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位用户登陆验证(宁波旧业务系统专用)");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
//		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		AES aes = new AES();
//		form.setUserId(aes.decrypt(form.getUserId()));
		form.setFullName(aes.decrypt(form.getFullName()));
//		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		//form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
//		List<Mi103> list = appApi401ServiceImpl.appapi40110(form);
//		if(list.size()>0){
//			m.remove("appid");					
//			m.put("appid", list.get(0).getUserId());
//		}else{
//			m.remove("appid");					
//			m.put("appid", "");
//		}
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		
		String mssage = msgSendApi001Service.send(wrapRequest, response);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";
	}	
	
}
