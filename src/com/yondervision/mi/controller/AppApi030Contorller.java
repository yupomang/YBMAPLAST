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
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi00401Result;
import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JavaBeanUtil;
import com.yondervision.mi.util.security.AES;


/** 
* @ClassName: AppApi030Contorller 
* @Description: 个人业务_贷款提前还款
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03   
* 
*/ 
@Controller
public class AppApi030Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	@Autowired
	private AppApi103Service appApi103ServiceImpl = null;
	public AppApi103Service getAppApi103ServiceImpl() {
		return appApi103ServiceImpl;
	}
	public void setAppApi103ServiceImpl(AppApi103Service appApi103ServiceImpl) {
		this.appApi103ServiceImpl = appApi103ServiceImpl;
	}
	
	
	/**
	 * @deprecated 个人业务_贷款提前还款
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi03001.{ext}")
	public String appapi03001(AppApiCommonForm form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("结息对账单查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue());
		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1=(HttpServletRequest) request;
			HashMap m=new HashMap(request.getParameterMap());
			AES aes = new AES();
			String sfz = "";
			if(!CommonUtil.isEmpty(m.get("userId"))){
				String usid = (String)request.getParameter("userId");
				sfz = aes.decrypt(usid);
				form.setUserId(sfz);
			}
			Mi103 mi103 = appApi103ServiceImpl.appApi10301Select(sfz);
			m.put("bodyCardNumber", mi103.getCertinum());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}	
}
	
