/**
 * 
 */
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
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi048;
import com.yondervision.mi.dto.Mi902;
import com.yondervision.mi.form.AppApi034Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.service.impl.AppApi902ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.ParamCheck;
import com.yondervision.mi.util.security.AES;


/** 
* @ClassName: AppApi034Contorller 
* @Description: 个人业务_退休提取
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03   
* 
*/ 
@Controller
public class AppApi034Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	@Autowired
	private WebApi029Service webApi029ServiceImpl = null;
	
	@Autowired
	private AppApi902ServiceImpl appApi902Service = null;
	
	public AppApi902ServiceImpl getAppApi902Service() {
		return appApi902Service;
	}

	public void setAppApi902Service(AppApi902ServiceImpl appApi902Service) {
		this.appApi902Service = appApi902Service;
	}

	public WebApi029Service getWebApi029ServiceImpl() {
		return webApi029ServiceImpl;
	}

	public void setWebApi029ServiceImpl(WebApi029Service webApi029ServiceImpl) {
		this.webApi029ServiceImpl = webApi029ServiceImpl;
	}
	
	
	/**
	 * @deprecated 退休提取
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi03401.{ext}")
	public String appapi03401(AppApi034Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("退休提取查询");	
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
		form.setCheckcode(aes.decrypt(form.getCheckcode()));
		form.setTel(aes.decrypt(form.getTel()));
		form.setCheckflag(aes.decrypt(form.getCheckflag()));
		if("1".equals(form.getCheckflag())){
			if(CommonUtil.isEmpty(form.getCheckcode())){
				log.error(ERROR.PARAMS_NULL.getLogText("Checkcode"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"验证码");
			}
			List<Mi902>  list = appApi902Service.validCode(form.getTel(), form.getCheckcode() ,form.getChannel());
			if(list.size()==0){
				modelMap.clear();
				modelMap.put("recode", "000001");
				modelMap.put("msg", "验证码错误，请重新填写");
				return "";
			}else{
				Mi902 mi902 = list.get(0);
				String failuretime = mi902.getFailuretime();
				if(new Long(failuretime)<System.currentTimeMillis()){
					modelMap.clear();
					modelMap.put("recode", "000001");
					modelMap.put("msg", "验证码失效，请重新获取验证码");
					return "";
				}
			}
		}
		
		
		
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
		
		if(!"2".equals(mi029.getUselevel().trim())){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "请先完成银行卡绑定！");
			return "";
		}
		Mi048 mi048 = webApi029ServiceImpl.webapi02919(mi029 , "01" ,request ,response);
		m.put("accnum", mi048.getAccountcode());
		m.put("bodyCardNumber", mi029.getCertinum());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);
		System.out.println("退休提取返回信息："+rep);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
			System.out.println("appapi03401资金类业务处理结果，是否记账：【"+((String)hasMap.get("acc_flag"))+"】");
			System.out.println("appapi03401资金类业务处理结果，记账金额：【"+((String)hasMap.get("acc_amt"))+"】");
			request.setAttribute("acc_flag", (String)hasMap.get("acc_flag"));
			request.setAttribute("acc_amt", (String)hasMap.get("acc_amt"));
		}
		
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}	
	
	/**
	 * @deprecated 物业费提取
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi03402.{ext}")
	public String appapi03402(AppApi034Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("物业费提取");
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
		form.setCheckcode(aes.decrypt(form.getCheckcode()));
		form.setTel(aes.decrypt(form.getTel()));
		form.setCheckflag(aes.decrypt(form.getCheckflag()));
		if("1".equals(form.getCheckflag())){
			if(CommonUtil.isEmpty(form.getCheckcode())){
				log.error(ERROR.PARAMS_NULL.getLogText("Checkcode"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"验证码");
			}
			List<Mi902>  list = appApi902Service.validCode(form.getTel(), form.getCheckcode() ,form.getChannel());
			if(list.size()==0){
				modelMap.clear();
				modelMap.put("recode", "000001");
				modelMap.put("msg", "验证码错误，请重新填写");
				return "";
			}else{
				Mi902 mi902 = list.get(0);
				String failuretime = mi902.getFailuretime();
				if(new Long(failuretime)<System.currentTimeMillis()){
					modelMap.clear();
					modelMap.put("recode", "000001");
					modelMap.put("msg", "验证码失效，请重新获取验证码");
					return "";
				}
			}
		}
		
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
		if(!"2".equals(mi029.getUselevel())){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "请先完成银行卡绑定！");
			return "";
		}
		Mi048 mi048 = webApi029ServiceImpl.webapi02919(mi029 , "01" ,request ,response);
		m.put("accnum", mi048.getAccountcode());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);
		System.out.println("物业费提取返回信息："+rep);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
			System.out.println("appapi03402资金类业务处理结果，是否记账：【"+((String)hasMap.get("acc_flag"))+"】");
			System.out.println("appapi03402资金类业务处理结果，记账金额：【"+((String)hasMap.get("acc_amt"))+"】");
			request.setAttribute("acc_flag", (String)hasMap.get("acc_flag"));
			request.setAttribute("acc_amt", (String)hasMap.get("acc_amt"));
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}	
	
	
	/**
	 * @deprecated 偿还商贷提取
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi03403.{ext}")
	public String appapi03403(AppApi034Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("物业费提取");
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
		form.setCheckcode(aes.decrypt(form.getCheckcode()));
		form.setTel(aes.decrypt(form.getTel()));
		form.setCheckflag(aes.decrypt(form.getCheckflag()));
		if("1".equals(form.getCheckflag())){
			if(CommonUtil.isEmpty(form.getCheckcode())){
				log.error(ERROR.PARAMS_NULL.getLogText("Checkcode"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"验证码");
			}
			List<Mi902>  list = appApi902Service.validCode(form.getTel(), form.getCheckcode() ,form.getChannel());
			if(list.size()==0){
				modelMap.clear();
				modelMap.put("recode", "000001");
				modelMap.put("msg", "验证码错误，请重新填写");
				return "";
			}else{
				Mi902 mi902 = list.get(0);
				String failuretime = mi902.getFailuretime();
				if(new Long(failuretime)<System.currentTimeMillis()){
					modelMap.clear();
					modelMap.put("recode", "000001");
					modelMap.put("msg", "验证码失效，请重新获取验证码");
					return "";
				}
			}
		}
		
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
		if(!"2".equals(mi029.getUselevel())){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "请先完成银行卡绑定！");
			return "";
		}
		Mi048 mi048 = webApi029ServiceImpl.webapi02919(mi029 , "01" ,request ,response);
		m.put("accnum", mi048.getAccountcode());
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);
		System.out.println("偿还商贷提取返回信息："+rep);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
			System.out.println("appapi03403资金类业务处理结果，是否记账：【"+((String)hasMap.get("acc_flag"))+"】");
			System.out.println("appapi03403资金类业务处理结果，记账金额：【"+((String)hasMap.get("acc_amt"))+"】");
			request.setAttribute("acc_flag", (String)hasMap.get("acc_flag"));
			request.setAttribute("acc_amt", (String)hasMap.get("acc_amt"));
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	
}
	
