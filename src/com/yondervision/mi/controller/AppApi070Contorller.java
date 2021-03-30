/**
 * 
 */
package com.yondervision.mi.controller;

import java.io.UnsupportedEncodingException;
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
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.form.AppApi07030Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.security.AES;


@Controller
public class AppApi070Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}

	
	/**
	 * @deprecated 已结顶幢号查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07010.{ext}")
	public String appapi07010(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("已结顶幢号查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 楼盘贷款信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07011.{ext}")
	public String appapi07011(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("楼盘贷款信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 柜台还款-个人账号查询公积金信息
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07012.{ext}")
	public String appapi07012(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("柜台还款-个人账号查询公积金信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 开发商注册
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07020.{ext}")
	public String appapi07020(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("开发商注册");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 开发商登录
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07021.{ext}")
	public String appapi07021(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("开发商登录");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 开发商密码修改与重置
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07022.{ext}")
	public String appapi07022(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("开发商密码修改与重置");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 楼盘导入
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07023.{ext}")
	public String appapi07023(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("楼盘导入");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 提前还款申请
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07024.{ext}")
	public String appapi07024(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提前还款申请");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 提前还款确认金额
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07025.{ext}")
	public String appapi07025(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提前还款确认金额");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 贷款还款记录查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07026.{ext}")
	public String appapi07026(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("贷款还款记录查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
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
	 * @deprecated 网厅楼盘状态推送
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi07030.{ext}")
	public String appapi07030(AppApi07030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("网厅楼盘状态推送");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			// 调用网厅接口			
		   SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		    String encoding = "UTF-8";
		    if (CommonUtil.isEmpty(request.getCharacterEncoding()))
		      encoding = "UTF-8";
		    else {
		      encoding = request.getCharacterEncoding();
		    }
			//String url = "http://172.16.0.186:7001/gjj-wsyyt/servlet/realEstateStateServlet";//生产环境
			String url = "http://172.16.0.207:7002/gjj-wsyyt/servlet/realEstateStateServlet";//生产环境
//			String url = "http://172.16.0.172:7001/gjj-wsyyt/servlet/realEstateStateServlet";//测试环境
	        HashMap hashMap = new HashMap();
	        hashMap.put("pubaccnum", form.getPubaccnum());
	        hashMap.put("lpstate",form.getLpstate());    
			String rep = msm.sendPost(url, hashMap, encoding);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{			
		   SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		    String encoding = "UTF-8";
//		    if (CommonUtil.isEmpty(request.getCharacterEncoding()))
//		      encoding = "UTF-8";
//		    else {
//		      encoding = request.getCharacterEncoding();
//		    }
			String url = "http://172.16.0.207:7002/gjj-wsyyt/servlet/realEstateStateServlet";//生产环境
//			String url = "http://172.16.0.172:7001/gjj-wsyyt/servlet/realEstateStateServlet";//测试环境
	        HashMap hashMap = new HashMap();
	        hashMap.put("pubaccnum", form.getPubaccnum());
	        hashMap.put("lpstate",form.getLpstate());
			String rep = msm.sendPost(url, hashMap, encoding);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info("change(rep)="+change(rep));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	private static String change(String input) {  
    	String output = null;
        try {  
            output = new String(input.getBytes("iso-8859-1"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output;  
    }  
}
	
