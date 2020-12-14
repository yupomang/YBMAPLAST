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
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi00401Result;
import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JavaBeanUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.ParamCheck;
import com.yondervision.mi.util.security.AES;

/** 
* @ClassName: AppApi004Contorller 
* @Description: 提取金额查询Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi004Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	@Autowired
	private WebApi029Service webApi029ServiceImpl = null;
	
	public WebApi029Service getWebApi029ServiceImpl() {
		return webApi029ServiceImpl;
	}

	public void setWebApi029ServiceImpl(WebApi029Service webApi029ServiceImpl) {
		this.webApi029ServiceImpl = webApi029ServiceImpl;
	}
	
	/**
	 * 提取金额查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00401.{ext}")
	public String appApi00401(AppApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提取金额查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		ParamCheck pc = new ParamCheck();
		pc.check(form);
		
		request.setAttribute("centerId", form.getCenterId());
		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		String sfz = "";
		if(!CommonUtil.isEmpty(m.get("userId"))){
			String usid = (String)request.getParameter("userId");
			sfz = aes.decrypt(usid);
			form.setUserId(sfz);
		}
		
		Mi031 mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
		if(CommonUtil.isEmpty(mi031)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "渠道用户信息不存在！");
			return "";
		}
		Mi029 mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
		if(CommonUtil.isEmpty(mi029)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "客户信息不存在！");
			return "";
		}
		
		m.put("bodyCardNumber", mi029.getCertinum());
		m.put("certinumType", mi029.getCertinumtype());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			webApi029ServiceImpl.webapi02911(form.getCenterId() ,hasMap , mi029.getPersonalid() ,mi029);
			String encoding = "UTF-8";
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(rep.getBytes(encoding));
			return "";
		}else{
			modelMap.clear();
			modelMap.put("recode", (String)hasMap.get("recode"));
			modelMap.put("msg", (String)hasMap.get("msg"));
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 提取额度计算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00402.{ext}")
	public String appApi00402(AppApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提取额度计算");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		ParamCheck pc = new ParamCheck();
		pc.check(form);
		
		request.setAttribute("centerId", form.getCenterId());
		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		String sfz = "";
		if(!CommonUtil.isEmpty(m.get("userId"))){
			String usid = (String)request.getParameter("userId");
			sfz = aes.decrypt(usid);
			form.setUserId(sfz);
		}
		
		Mi031 mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
		if(CommonUtil.isEmpty(mi031)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "渠道用户信息不存在！");
			return "";
		}
		Mi029 mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
		if(CommonUtil.isEmpty(mi029)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "客户信息不存在！");
			return "";
		}
		
		m.put("bodyCardNumber", mi029.getCertinum());
		m.put("certinumType", mi029.getCertinumtype());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			webApi029ServiceImpl.webapi02911(form.getCenterId() ,hasMap , mi029.getPersonalid() ,mi029);
			String encoding = "UTF-8";
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(rep.getBytes(encoding));
			return "";
		}else{
			modelMap.clear();
			modelMap.put("recode", (String)hasMap.get("recode"));
			modelMap.put("msg", (String)hasMap.get("msg"));
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
}
