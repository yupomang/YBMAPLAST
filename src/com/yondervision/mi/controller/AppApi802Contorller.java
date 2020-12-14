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
import com.yondervision.mi.dao.Mi110DAO;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi110Example;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.form.AppApi80202Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.AppApi401Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.impl.AppApi110ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.security.AES;

@Controller
public class AppApi802Contorller {
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
	 * 个人余额查询(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80201.{ext}")
	public String appApi80201(AppApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人余额查询(宁波旧业务系统专用)");	
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
	 * 个人账户对账(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80202.{ext}")
	public String appapi80202(AppApi80202Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人账户对账(宁波旧业务系统专用)");	
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
	 * 贷款情况审批(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80203.{ext}")
	public String appapi80203(AppApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款情况审批(宁波旧业务系统专用)");	
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
	 * 个人用户登陆验证(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80204.{ext}")
	public String appapi80204(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人用户登陆验证(宁波旧业务系统专用)");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		request.setAttribute("centerId", form.getCenterId());
		AES aes = new AES();
		form.setUserId(aes.decrypt(form.getUserId()));
		//form.setFullName(aes.decrypt(form.getFullName()));
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		
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
		if(mssage.indexOf("\"recode\":\"000000\"")>0){
			String msg = appApi110Service.appApi11004Insert(form, modelMap, request, response);
			if(msg.equals("true")){
				System.out.println(form.getUserId()+"绑定操作统一用户表正确。");
			}
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";	
	}
	
	@RequestMapping("/appapi80205.{ext}")
	public String appApi80205(AppApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人余额查询(宁波旧业务系统专用)");	
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
			String sfz = (String)request.getParameter("userId");
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
	 * 个人账户对账(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80206.{ext}")
	public String appapi80206(AppApi80202Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人账户对账(宁波旧业务系统专用)");	
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
			String sfz = (String)request.getParameter("userId");
//			if(!CommonUtil.isEmpty(m.get("userId"))){
//				String usid = (String)request.getParameter("userId");
//				sfz = aes.decrypt(usid);
//			}
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
	 * 贷款情况审批(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80207.{ext}")
	public String appapi80207(AppApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款情况审批(宁波旧业务系统专用)");	
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
			String sfz =(String)request.getParameter("userId");
//			if(!CommonUtil.isEmpty(m.get("userId"))){
//				String usid = (String)request.getParameter("userId");
//				sfz = aes.decrypt(usid);
//			}
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
	 * 个人用户登陆验证(宁波旧业务系统专用)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80208.{ext}")
	public String appapi80208(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人用户登陆验证(宁波旧业务系统专用)");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		request.setAttribute("centerId", form.getCenterId());
		AES aes = new AES();
//		if(form.getUserId()!=null&&!("").equals(form.getUserId())){
//			form.setUserId(aes.decrypt(form.getUserId()));
//		}
//		if(form.getSurplusAccount()!=null&&!("").equals(form.getSurplusAccount())){
//			form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
//		}
//		form.setUserId(aes.decrypt(form.getUserId()));
		//form.setFullName(aes.decrypt(form.getFullName()));
//		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		log.info(LOG.START_BUSIN.getLogText(form.getIdcardNumber()));
		
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
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(mssage, HashMap.class);
		if(mssage.indexOf("\"recode\":\"000000\"")>0){
			form.setSurplusAccount(hasMap.get("gjjzh").toString());
			String msg = appApi110Service.appApi11004Insert(form, modelMap, request, response);
			if(msg.equals("true")){
				System.out.println(form.getUserId()+"绑定操作统一用户表正确。");
			}
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";	
	}
}