/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.HashMap;

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
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi048;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.form.AppApi00601Form;
import com.yondervision.mi.form.AppApi00603Form;
import com.yondervision.mi.form.AppApi00609Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.ParamCheck;
import com.yondervision.mi.util.security.AES;

/** 
* @ClassName: AppApi006Contorller 
* @Description: 贷款余额查询Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi006Contorller {
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
	 * 贷款余额查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00601.{ext}")
	public String appApi00601(AppApi00601Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款余额查询");	
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
		
		request.setAttribute("centerId", form.getCenterId());
		m.put("bodyCardNumber", mi029.getCertinum());
		m.put("certinumType", mi029.getCertinumtype());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}	
	
	/**
	 * 贷款个人信息查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00602.{ext}")
	public String appApi00602(AppApi00601Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款个人信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}	
	
	/**
	 * 贷款个人信息修改
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00603.{ext}")
	public String appApi00603(AppApi00603Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款个人信息修改");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
			
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
	
	/**
	 * 借款合同号查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00604.{ext}")
	public String appApi00604(AppApi00601Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("借款合同号查询");	
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
		
		request.setAttribute("centerId", form.getCenterId());
		m.put("bodyCardNumber", mi029.getCertinum());
		m.put("certinumType", mi029.getCertinumtype());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
	/**
	 * 贷款欠款情况查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00605.{ext}")
	public String appApi00605(AppApi00601Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款欠款情况查询");	
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
		
		request.setAttribute("centerId", form.getCenterId());
		m.put("bodyCardNumber", mi029.getCertinum());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
	/**
	 * 提前还款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00606.{ext}")
	public String appApi00606(AppApi00601Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提前还款试算");	
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
		
		request.setAttribute("centerId", form.getCenterId());
		m.put("bodyCardNumber", mi029.getCertinum());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);	
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
	
	/**
	 * 贷款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00607.{ext}")
	public String appApi00607(AppApi00601Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款试算");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
			
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

		Mi048 mi048 = webApi029ServiceImpl.webapi02919(mi029 ,"04" ,null ,null);
		
		m.put("loancontrnum", mi048.getAccountcode());//借款合同号
		request.setAttribute("centerId", form.getCenterId());
		m.put("bodyCardNumber", mi029.getCertinum());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);	
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
	
	/**
	 * 提前还款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00608.{ext}")
	public String appApi00608(AppApi00601Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提前还款试算");	
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
		
		Mi048 mi048 = webApi029ServiceImpl.webapi02919(mi029 ,"04" ,null ,null);
		System.out.println("##########################       loancontrnum : "+mi048.getAccountcode());
		request.setAttribute("centerId", form.getCenterId());
		m.put("bodyCardNumber", mi029.getCertinum());
		m.remove("loancontractno");
		m.put("loancontractno", mi048.getAccountcode());//借款合同号
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);	
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 提前部分还款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00609.{ext}")
	public String appApi00609(AppApi00609Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提前部分还款试算");	
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
		
//		Mi031 mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
//		if(CommonUtil.isEmpty(mi031)){
//			modelMap.clear();
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "渠道用户信息不存在！");
//			return "";
//		}
//		Mi029 mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
//		if(CommonUtil.isEmpty(mi029)){
//			modelMap.clear();
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "客户信息不存在！");
//			return "";
//		}
		
//		Mi048 mi048 = webApi029ServiceImpl.webapi02919(mi029 ,"04" ,null ,null);
//		System.out.println("##########################       loancontrnum : "+mi048.getAccountcode());
//		request.setAttribute("centerId", form.getCenterId());
//		m.put("bodyCardNumber", mi029.getCertinum());
//		m.remove("loancontractno");
//		m.put("loancontractno", mi048.getAccountcode());//借款合同号
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);	
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 提前还款试算取最低还款额
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00610.{ext}")
	public String appApi00610(AppApi00609Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提前还款试算取最低还款额");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();	
		ParamCheck pc = new ParamCheck();
		pc.check(form);
		
		request.setAttribute("centerId", form.getCenterId());
		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
//		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
//		String sfz = "";
//		if(!CommonUtil.isEmpty(m.get("userId"))){
//			String usid = (String)request.getParameter("userId");
//			sfz = aes.decrypt(usid);
//			form.setUserId(sfz);
//		}
		
//		Mi031 mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
//		if(CommonUtil.isEmpty(mi031)){
//			modelMap.clear();
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "渠道用户信息不存在！");
//			return "";
//		}
//		Mi029 mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
//		if(CommonUtil.isEmpty(mi029)){
//			modelMap.clear();
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "客户信息不存在！");
//			return "";
//		}
		
//		Mi048 mi048 = webApi029ServiceImpl.webapi02919(mi029 ,"04" ,null ,null);
//		System.out.println("##########################       loancontrnum : "+mi048.getAccountcode());
//		request.setAttribute("centerId", form.getCenterId());
//		m.put("bodyCardNumber", mi029.getCertinum());
//		m.remove("loancontractno");
//		m.put("loancontractno", mi048.getAccountcode());//借款合同号
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);	
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 贷款申请
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00611.{ext}")
	public String appApi00611(AppApi00601Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款申请");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();	
		
		
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
		
		request.setAttribute("centerId", form.getCenterId());
		m.put("bodyCardNumber", mi029.getCertinum());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);	
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
}
