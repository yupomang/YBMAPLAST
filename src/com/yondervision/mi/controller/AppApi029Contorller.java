/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.ArrayList;
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
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi048;
import com.yondervision.mi.form.AppApi029Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi02902Result;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.service.WebApi040Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: AppApi029Contorller 
* @Description: 个人业务_消息通知渠道设置
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03   
* 
*/ 
@Controller
public class AppApi029Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	@Autowired
	private WebApi029Service webApi029ServiceImpl = null;
	@Autowired
	private WebApi040Service webApi040ServiceImpl = null;
	
	public WebApi029Service getWebApi029ServiceImpl() {
		return webApi029ServiceImpl;
	}

	public void setWebApi029ServiceImpl(WebApi029Service webApi029ServiceImpl) {
		this.webApi029ServiceImpl = webApi029ServiceImpl;
	}
	
	public MsgSendApi001Service getMsgSendApi001Service() {
		return msgSendApi001Service;
	}

	public WebApi040Service getWebApi040ServiceImpl() {
		return webApi040ServiceImpl;
	}

	public void setWebApi040ServiceImpl(WebApi040Service webApi040ServiceImpl) {
		this.webApi040ServiceImpl = webApi040ServiceImpl;
	}

	/**
	 * @deprecated 个人业务_消息通知渠道设置
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02901.{ext}")
	public String appapi02901(AppApi029Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("消息通知渠道设置");	
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
		String[] ids = form.getIds().split(",");
		String[] sendmessage = form.getSendmessage().split(",");
		for(int i=0;i<ids.length;i++){
			if(!CommonUtil.isEmpty(sendmessage[i])){
				Mi031 mi031 = new Mi031();
				mi031.setId(ids[i]);
				mi031.setSendmessage(sendmessage[i]);
				mi031.setDatemodified(CommonUtil.getSystemDate());
				webApi029ServiceImpl.webapi02922(mi031, request, response);
			}			
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}	
	
	/**
	 * @deprecated 个人业务_消息通知渠道查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02902.{ext}")
	public String appapi02902(AppApiCommonForm form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("消息通知渠道查询");	
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
		
		Mi031 mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
		if(CommonUtil.isEmpty(mi031)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "渠道用户信息不存在！");
			return "";
		}
		List<Mi031> list = webApi029ServiceImpl.webapi02921(mi031, request, response);
		if(CommonUtil.isEmpty(list)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "消息通知渠道不存在！");
			return "";
		}
		Mi029 mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
		if(CommonUtil.isEmpty(mi029)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "客户信息不存在！");
			return "";
		}
		
		List<AppApi02902Result> relist = new ArrayList();
		for(int i=0;i<list.size();i++){
			if("10".equals(list.get(i).getChannel())||"20".equals(list.get(i).getChannel())||"70".equals(list.get(i).getChannel())){
				Mi040 mi040 = webApi040ServiceImpl.webapi04008(list.get(i).getPid());
				AppApi02902Result appapi029 = new AppApi02902Result();
				appapi029.setId(list.get(i).getId());
				appapi029.setPidname(mi040.getAppname());
				appapi029.setSendmessage(CommonUtil.isEmpty(list.get(i).getSendmessage())?"0":list.get(i).getSendmessage());
				relist.add(appapi029);
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("username", mi029.getUsername());
		modelMap.put("certinum", mi029.getCertinum());
		modelMap.put("result", relist);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}	
}
	
