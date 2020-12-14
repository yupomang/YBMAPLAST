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
import com.yondervision.mi.form.AppApi00301Form;
import com.yondervision.mi.result.AppApi00201Result;
import com.yondervision.mi.result.AppApi00301Result;
import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JavaBeanUtil;
import com.yondervision.mi.util.security.AES;

/** 
* @ClassName: AppApi003Contorller 
* @Description: 结息对账Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi003Contorller {
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
	 * 结息对账查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00301.{ext}")
	public String appApi00301(AppApi00301Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("结息对账查询");	
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
//		msgSendApi001Service.send(request, response);
		
		
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
		return "/index";
	}	
}
