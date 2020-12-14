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
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.form.AppApi058Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi05802Result;
import com.yondervision.mi.service.AppApi058Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.ParamCheck;
import com.yondervision.mi.util.security.AES;


@Controller
public class AppApi058Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	@Autowired
	private AppApi058Service appApi058ServiceImpl = null;
	

	public AppApi058Service getAppApi058ServiceImpl() {
		return appApi058ServiceImpl;
	}

	public void setAppApi058ServiceImpl(AppApi058Service appApi058ServiceImpl) {
		this.appApi058ServiceImpl = appApi058ServiceImpl;
	}
	@Autowired
	private WebApi029Service webApi029ServiceImpl = null;
	
	public WebApi029Service getWebApi029ServiceImpl() {
		return webApi029ServiceImpl;
	}

	public void setWebApi029ServiceImpl(WebApi029Service webApi029ServiceImpl) {
		this.webApi029ServiceImpl = webApi029ServiceImpl;
	}

	@RequestMapping("/appapi05801.{ext}")
	public void appApi05801(AppApi058Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("服务缓存时间查询");	
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
		
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		Mi031 mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
		if(CommonUtil.isEmpty(mi031)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "渠道用户信息不存在！");
			String repmsg = JsonUtil.getGson().toJson(modelMap);
			response.getOutputStream().write(repmsg.getBytes(encoding));
			return ;
		}
		Mi029 mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
		if(CommonUtil.isEmpty(mi029)){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "客户信息不存在！");
			String repmsg = JsonUtil.getGson().toJson(modelMap);
			response.getOutputStream().write(repmsg.getBytes(encoding));
			return ;
		}
		m.put("bodyCardNumber", mi029.getCertinum());
		
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		String rep = msgSendApi001Service.send(wrapRequest, response);
		System.out.println("form.getSelecttype()："+form.getSelecttype());
		AppApi05802Result rep058 = (AppApi05802Result)JsonUtil.getDisableHtmlEscaping().fromJson(rep, AppApi05802Result.class);
		
		
		
		AppApi05802Result result = new AppApi05802Result();
		if("000000".equals(rep058.getRecode())){
			result = appApi058ServiceImpl.appApi05801Select(form ,rep058,mi029);
			String repmsg = JsonUtil.getGson().toJson(result);
			//"0":"一般","1":"基本满意","2":"满意","3":"非常满意","-1":"不满意"    对应的 数值 仅供参考
			System.out.println("#####查询返回信息："+repmsg);
			response.getOutputStream().write(repmsg.getBytes(encoding));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return ;
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return ;
		}
	}
	
	@RequestMapping("/appapi05802.{ext}")
	public String appApi05802(AppApi058Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("服务缓存时间查询");	
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
		
//		if(CommonUtil.isEmpty(form.getDetail())||CommonUtil.isEmpty(form.getCounternum())
//				||CommonUtil.isEmpty(form.getCountername())){
//			modelMap.clear();
//			modelMap.put("recode", "999111");
//			modelMap.put("msg", "上传信息不正确！请检查柜面业务摘要、柜员编号、柜员姓名信息！");
//			return "";
//		}
		
		appApi058ServiceImpl.appApi05802Select(form , mi029);
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
}
