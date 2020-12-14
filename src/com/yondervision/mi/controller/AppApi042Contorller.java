/**
 * 
 */
package com.yondervision.mi.controller;

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
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi902;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.impl.AppApi902ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.ParamCheck;
import com.yondervision.mi.util.security.AES;


/** 
* @ClassName: AppApi042Contorller 
* @Description: 单位业务_缴存比例变更
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03
* 
*/ 
@Controller
public class AppApi042Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	@Autowired
	private AppApi902ServiceImpl appApi902Service = null;
	
	public AppApi902ServiceImpl getAppApi902Service() {
		return appApi902Service;
	}

	public void setAppApi902Service(AppApi902ServiceImpl appApi902Service) {
		this.appApi902Service = appApi902Service;
	}

	/**
	 * @deprecated 单位业务_缴存比例变更
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi04201.{ext}")
	public String appapi04201(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("缴存比例变更");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
//		ParamCheck pc = new ParamCheck();
//		pc.check(form);
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")){
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
	 * @deprecated 单位缴存证明打印
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi04202.{ext}")
	public String appapi04202(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位缴存证明打印");	
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
	 * @deprecated 个人缴存证明打印
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi04203.{ext}")
	public String appapi04203(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人缴存证明打印");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		
		if("50".equals(form.getChannel())){
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			form.setTel(aes.decrypt(form.getTel()));
			form.setCheckcode(aes.decrypt(form.getCheckcode()));
			
			List<Mi902>  list = appApi902Service.validCode(form.getTel(), form.getCheckcode() ,form.getChannel());
			if(CommonUtil.isEmpty(list)){
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
		
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		
		appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}	
}
	
