/**
 * 
 */
package com.yondervision.mi.controller;

import com.alibaba.fastjson.JSON;
import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.form.*;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.util.*;
import com.yondervision.mi.util.security.AES;
import com.yondervision.mi.util.security.Base64Decoder;
import com.yondervision.mi.util.security.RSAUtilShengTing;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
public class AppApi001Contorller {
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
	 * 个人公积金基本信息查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00101.{ext}")
	public String appApi00101(AppApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人公积金基本信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		//住建委网站不校验签名
		if(!"30".equals(form.getChannel())){
			ApiUserContext.getInstance();
		}
		ParamCheck pc = new ParamCheck();
		pc.check(form);


		request.setAttribute("centerId", form.getCenterId());
		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		String sfz = "";
		if(!CommonUtil.isEmpty(m.get("userId"))){
			String usid = (String)request.getParameter("userId");
			//针对住建委网站加密问题修改 xzw
			if (!"30".equals(form.getChannel())) {
				sfz = aes.decrypt(usid);
			}else{
				sfz =usid;
			}

			form.setUserId(sfz);
		}
		
		Mi031 mi031 = null;
		Mi029 mi029 = null;
		if("20".equals(form.getChannel())){
			AppApi50001Form form1 = new AppApi50001Form();
			form1.setCenterId(form.getCenterId());
			form1.setBodyCardNumber(form.getBodyCardNumber());
			mi029 = webApi029ServiceImpl.webapi02901(form1);
			if(CommonUtil.isEmpty(mi029)){
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "客户信息不存在！");
				return "";
			}
			m.put("bodyCardNumber", form.getBodyCardNumber());
		}else{
			mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
			if(CommonUtil.isEmpty(mi031)){
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "渠道用户信息不存在！");
				return "";
			}
			mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
			if(CommonUtil.isEmpty(mi029)){
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "客户信息不存在！");
				return "";
			}
			m.put("bodyCardNumber", mi029.getCertinum());
		}
		
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
	 * 查询个人公积金信息简版
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00102.{ext}")
	public String appApi00102(AppApi50001Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("查询个人公积金信息简版");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		
		request.setAttribute("centerId", form.getCenterId());
		
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 个人联名卡绑定
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00103.{ext}")
	public String appApi00103(AppApi50001Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人联名卡绑定");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		
		request.setAttribute("centerId", form.getCenterId());
		
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	
	/**
	 * @deprecated 个人账户查询（用于反显）
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00104.{ext}")
	public String appapi00104(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人账户查询（用于反显）");	
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
	 * 人口信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("/appapi00105.{ext}")
	public void appapi00105(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("人口信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		
		//连接浙江省公共数据平台获取数据
		String requestSecret =  WkfAccessTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
		log.info(requestSecret);
		
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		log.info("requestTime:" + requestTime);
		
		Map hm = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId(), "configPublicData");
		String sign = EncryptionByMD5.getMD5((String.valueOf(hm.get("appKey")).toLowerCase() + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
		String url = String.valueOf(hm.get("url")) + String.valueOf(hm.get("popInfoUrl")) + "?appKey=" 
				+ String.valueOf(hm.get("appKey")) + "&sign=" + sign 
				+ "&requestTime=" + requestTime + "&cardId=" + form.getBodyCardNumber();
		log.info("人口信息查询url="+url);
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String result = msm.sendGet(url, "UTF-8");
		JSONObject json = JSONObject.fromObject(result);
		if("00".equals(String.valueOf(json.get("code")))){
			json.put("recode", "000000");
		}else{
			json.put("recode",json.get("code"));
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));
		return ;
	}
	
	*//**
	 * 婚姻登记信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("/appapi00106.{ext}")
	public void appapi00106(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("婚姻登记信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		//连接浙江省公共数据平台获取数据
		String requestSecret =  WkfAccessTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
		log.info(requestSecret);
		
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		log.info("requestTime:" + requestTime);
		
		Map hm = WkfAccessTokenUtil.getAppSecretAndAppKey(form.getCenterId(), "configPublicData");
		String sign = EncryptionByMD5.getMD5((String.valueOf(hm.get("appKey")).toLowerCase() + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
		String url = String.valueOf(hm.get("url")) + String.valueOf(hm.get("marryInfo")) + "?appKey=" 
				+ String.valueOf(hm.get("appKey")) + "&sign=" + sign 
				+ "&requestTime=" + requestTime + "&cardId=" + form.getBodyCardNumber()
				+ "&sex=" + form.getSex() + "&name=" + form.getFullName() 
				+ "&birthday=" + form.getBirthday();
		log.info("婚姻登记信息查询url="+url);
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String result = msm.sendGet(url, "UTF-8");
		JSONObject json = JSONObject.fromObject(result);
		if("00".equals(String.valueOf(json.get("code")))){
			json.put("recode", "000000");
		}else{
			json.put("recode",json.get("code"));
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));
		return ;
	}*/
	@RequestMapping("/appapi00105.{ext}")
	public String appapi00105(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("人口信息查询");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	@RequestMapping("/appapi00106.{ext}")
	public String appapi00106(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("婚姻登记信息查询");	
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
			if(form.getChannel().trim().equals("92")){
				response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			}else{
				response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			}

			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+change(rep));
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
    
	@RequestMapping("/appapi00107.{ext}")
	public String appapi00107(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("获取房屋登记信息");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	/**
	 * 获取房屋登记信息
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	@RequestMapping("/appapi00107.{ext}")
	public void appapi00107(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("获取房屋登记信息");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		//连接房改局
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"fgj_url").trim();
		url = url + "?idNumber="+form.getBodyCardNumber()+"&divisionCode="+form.getDivisionCode()
				+"&certType="+form.getCertType()+"&certNumber="+form.getCertNumber();
		log.info("获取房屋登记信息url="+url);
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String result = msm.sendGet(url, "UTF-8");
		JSONObject json = JSONObject.fromObject(result);
		if("0".equals(String.valueOf(json.get("status")))){
			json.put("recode", "000000");
		}else{
			json.put("recode",json.get("code"));
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));
		return ;
	}*/
	
	@RequestMapping("/appapi00108.{ext}")
	public String appapi00108(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("户籍信息判断");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00111.{ext}")
	public String appapi00111(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("企业备案信息查询接口");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00112.{ext}")
	public String appapi00112(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("有无住房查询");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00113.{ext}")
	public String appapi00113(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("不动产房屋登记信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
//			request.setCharacterEncoding("GBK");
			request.setCharacterEncoding("UTF-8");
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00114.{ext}")
	public String appapi00114(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("退休信息查询");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00115.{ext}")
	public String appapi00115(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("人员当前参保信息查询");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00116.{ext}")
	public String appapi00116(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("户籍信息判断新");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	@RequestMapping("/appapi00117.{ext}")
	public String appapi00117(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("宁波市医疗保险参保人员信息查询");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
		else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00118.{ext}")
	public String appapi00118(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("房管局商品房合同查询");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00119.{ext}")
	public String appapi00119(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("省公安厅居民身份证新");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	/**
	 * @deprecated 个人贷款审批进度查询网厅
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00120.{ext}")
	public String appapi00120(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人贷款审批进度查询网厅");	
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
	 * @deprecated 个人贷款信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00121.{ext}")
	public String appapi00121(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人贷款信息查询");	
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
	 * @deprecated 还款明细查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00122.{ext}")
	public String appapi00122(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("还款明细查询");	
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
	 * @deprecated 还款计划查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00123.{ext}")
	public String appapi00123(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("还款计划查询");	
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
	 * @deprecated 个人贷款试算页面信息获取
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00124.{ext}")
	public String appapi00124(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人贷款试算页面信息获取");	
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
	 * @deprecated 商贷基本信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00125.{ext}")
	public String appapi00125(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("商贷基本信息查询");	
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
	 * @deprecated 商贷可提取金额计算
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00126.{ext}")
	public String appapi00126(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("商贷可提取金额计算");	
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
	 * @deprecated 银行卡校验
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00127.{ext}")
	public String appapi00127(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("银行卡校验");	
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
	 * @deprecated 查询个人公贷合同号
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00128.{ext}")
	public String appapi00128(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("查询个人公贷合同号");	
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
	 * @deprecated 查询个人公贷合同号（测试）
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
		@RequestMapping("/appapi00129.{ext}")
		public String appapi00129(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			Logger log = LoggerUtil.getLogger();
			form.setBusinName("查询个人公贷合同号（测试）");
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
	 * @deprecated 查询个人是否面签
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00130.{ext}")
	public String appapi00130(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("查询个人是否面签");	
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
	 * @deprecated 个人明细查询网厅
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00131.{ext}")
	public String appapi00131(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人明细查询网厅");	
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
	 * @deprecated 自然人、法人信用信息核查接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00132.{ext}")
	public String appapi00132(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("自然人、法人信用信息核查接口");	
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
	 * @deprecated 信用信息核查情况反馈接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00133.{ext}")
	public String appapi00133(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("信用信息核查情况反馈接口");	
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
	
	
	@RequestMapping("/appapi00134.{ext}")
	public String appapi00134(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("省国土资源厅不动产权证新");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00135.{ext}")
	public String appapi00135(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("低保救助信息");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	@RequestMapping("/appapi00136.{ext}")
	public String appapi00136(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("失业保险参保人员信息");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00137.{ext}")
	public String appapi00137(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("社会保险个人参保信息");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00138.{ext}")
	public String appapi00138(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("省公安厅居民身份证旧");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	@RequestMapping("/appapi00139.{ext}")
	public String appapi00139(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("宁波市存量房交易合同查询");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00140.{ext}")
	public String appapi00140(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("购房发票查询");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00141.{ext}")
	public String appapi00141(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("宁波市死因证明");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	/**
	 * @deprecated 个人明细查询网厅
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00142.{ext}")
	public String appapi00142(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人年度对账单查询");	
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
	
	@RequestMapping("/appapi00143.{ext}")
	public String appapi00143(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("契税完税信息接口");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	@RequestMapping("/appapi00144.{ext}")
	public String appapi00144(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("省建设厅商品房买卖合同接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			HashMap<String, String> params = new HashMap<String, String>();
	        String url ="http://118.178.119.38/gjjfw/sjgxService/doQuery.do";
	        params.put("jgh", form.getJgh());
	        params.put("jkbm", form.getJkbm());
	        
	        String para="{\"SRFZJHM\":\""+form.getCertinum()+"\","
	        		+ "\"SZCS\":\""+form.getSZCS()+"\","
	        		+ "\"HTBH\":\""+form.getHTBH()+"\"}";
	        
			log.info("para="+para);
			
	    	/** 指定公钥存放文件 */
	    	String PUBLIC_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "public_key_shengting").trim();

	    	/** 指定私钥存放文件 */
	    	String PRIVATE_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "private_key_shengting").trim();

	        params.put("para", RSAUtilShengTing.encrypt(para,PUBLIC_KEY));
//	        参数1： jgh（机构号），非加密；
//	        参数2： jkbm（接口编码），非加密
//			SRFZJHM	房屋买受人证件号码
//			SZCS	所在城市行政编码
//			HTBH	合同编号

	        SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
	        String rep = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
			
			log.info("省厅返回rep="+rep);
			rep =  RSAUtilShengTing.decrypt(rep,PRIVATE_KEY).replace("code", "recode");
			log.info("解密后rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			modelMap.clear();
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			HashMap<String, String> params = new HashMap<String, String>();
	        String url ="http://118.178.119.38/gjjfw/sjgxService/doQuery.do";
	        params.put("jgh", form.getJgh());
	        params.put("jkbm", form.getJkbm());
	        
	    	/** 指定公钥存放文件 */
	    	String PUBLIC_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "public_key_shengting").trim();

	    	/** 指定私钥存放文件 */
	    	String PRIVATE_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "private_key_shengting").trim();

	        String para="{\"SRFZJHM\":\""+form.getCertinum()+"\","
	        		+ "\"SZCS\":\""+form.getSZCS()+"\","
	        		+ "\"HTBH\":\""+form.getHTBH()+"\"}";
	        params.put("para", RSAUtilShengTing.encrypt(para,PUBLIC_KEY));
//	        参数1： jgh（机构号），非加密；
//	        参数2： jkbm（接口编码），非加密
//			SRFZJHM	房屋买受人证件号码
//			SZCS	所在城市行政编码
//			HTBH	合同编号

	        SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
	        String rep = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
	        
			log.info("省厅返回rep="+rep);
			rep =  RSAUtilShengTing.decrypt(rep,PRIVATE_KEY).replace("code", "recode");
			log.info("解密后rep="+rep);
			response.getOutputStream().write(rep.getBytes("UTF-8"));
			modelMap.clear();
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00145.{ext}")
	public String appapi00145(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("省建设厅二手房转让合同接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			HashMap<String, String> params = new HashMap<String, String>();
	        String url ="http://118.178.119.38/gjjfw/sjgxService/doQuery.do";
	        params.put("jgh", form.getJgh());
	        params.put("jkbm", form.getJkbm());
	        
	        String para="{\"SRFZJHM\":\""+form.getCertinum()+"\","
	        		+ "\"SZCS\":\""+form.getSZCS()+"\","
	        		+ "\"HTBH\":\""+form.getHTBH()+"\"}";
	    	/** 指定公钥存放文件 */
	    	String PUBLIC_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "public_key_shengting").trim();

	    	/** 指定私钥存放文件 */
	    	String PRIVATE_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "private_key_shengting").trim();

	        params.put("para", RSAUtilShengTing.encrypt(para,PUBLIC_KEY));
	        SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
	        String rep = simpleHttpMessageUtil.sendPost(url, params,"utf-8");

			log.info("省厅返回rep="+rep);
			rep =  RSAUtilShengTing.decrypt(rep,PRIVATE_KEY).replace("code", "recode");
			log.info("解密后rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			modelMap.clear();
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			HashMap<String, String> params = new HashMap<String, String>();
	        String url ="http://118.178.119.38/gjjfw/sjgxService/doQuery.do";
	        params.put("jgh", form.getJgh());
	        params.put("jkbm", form.getJkbm());
	        
	        String para="{\"SRFZJHM\":\""+form.getCertinum()+"\","
	        		+ "\"SZCS\":\""+form.getSZCS()+"\","
	        		+ "\"HTBH\":\""+form.getHTBH()+"\"}";

	    	/** 指定公钥存放文件 */
	    	String PUBLIC_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "public_key_shengting").trim();

	    	/** 指定私钥存放文件 */
	    	String PRIVATE_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "private_key_shengting").trim();

	        params.put("para", RSAUtilShengTing.encrypt(para,PUBLIC_KEY));
	        SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
	        String rep = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
	        
			log.info("省厅返回rep="+rep);
			rep =  RSAUtilShengTing.decrypt(rep,PRIVATE_KEY).replace("code", "recode");
			log.info("解密后rep="+rep);
			response.getOutputStream().write(rep.getBytes("UTF-8"));
			modelMap.clear();
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	
	@RequestMapping("/appapi00146.{ext}")
	public String appapi00146(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("地名核准");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00147.{ext}")
	public String appapi00147(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("部门申报系统获取统一办件编码接口");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00148.{ext}")
	public String appapi00148(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("统一编码标记废弃接口");	
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
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00149.{ext}")
	public String appapi00149(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("获取基本办件信息接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00150.{ext}")
	public String appapi00150(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("宁波市城镇职工个人参保证明基础信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00151.{ext}")
	public String appapi00151(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位社保缴纳基础信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00152.{ext}")
	public String appapi00152(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("劳动能力鉴定结论");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00153.{ext}")
	public String appapi00153(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("医保费用个人负担证明");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00154.{ext}")
	public String appapi00154(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("宁波市医疗保险参保人员信息（新）");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("53")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00155.{ext}")
	public String appapi00155(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("宁波市企业养老保险参保人员信息（新）");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00156.{ext}")
	public String appapi00156(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("宁波市失业人员信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	
	@RequestMapping("/appapi00157.{ext}")
	public String appapi00157(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("治安户籍迁出信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	@RequestMapping("/appapi00158.{ext}")
	public String appapi00158(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("省建设厅-省国土资源厅不动产权证");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			HashMap<String, String> params = new HashMap<String, String>();
	        String url ="http://118.178.119.38/gjjfw/sjgxService/doQuery.do";
	        params.put("jgh", form.getJgh());
	        params.put("jkbm", form.getJkbm());
	        
	        String para="{\"qlrid\":\""+form.getQlrid()+"\"}";
	    	/** 指定公钥存放文件 */
	    	String PUBLIC_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "public_key_shengting").trim();

	    	/** 指定私钥存放文件 */
	    	String PRIVATE_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "private_key_shengting").trim();

	        params.put("para", RSAUtilShengTing.encrypt(para,PUBLIC_KEY));
	        SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
	        String rep = simpleHttpMessageUtil.sendPost(url, params,"utf-8");

			log.info("省厅返回rep="+rep);
			rep =  RSAUtilShengTing.decrypt(rep,PRIVATE_KEY).replace("code", "recode");
			log.info("解密后rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			modelMap.clear();
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			HashMap<String, String> params = new HashMap<String, String>();
	        String url ="http://118.178.119.38/gjjfw/sjgxService/doQuery.do";
	        params.put("jgh", form.getJgh());
	        params.put("jkbm", form.getJkbm());
	        
	        String para="{\"qlrid\":\""+form.getQlrid()+"\"}";

	    	/** 指定公钥存放文件 */
	    	String PUBLIC_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "public_key_shengting").trim();

	    	/** 指定私钥存放文件 */
	    	String PRIVATE_KEY=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "private_key_shengting").trim();

	        params.put("para", RSAUtilShengTing.encrypt(para,PUBLIC_KEY));
	        SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
	        String rep = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
	        
			log.info("省厅返回rep="+rep);
			rep =  RSAUtilShengTing.decrypt(rep,PRIVATE_KEY).replace("code", "recode");
			log.info("解密后rep="+rep);
			response.getOutputStream().write(rep.getBytes("UTF-8"));
			modelMap.clear();
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	@RequestMapping("/appapi00159.{ext}")
	public String appapi00159(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("可信身份认证接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}

	
	
	@RequestMapping("/appapi00160.{ext}")
	public String appapi00160(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("住房公积金降低缴存比例和缓缴申请表");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00161.{ext}")
	public String appapi00161(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("省工商局营业执照");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00162.{ext}")
	public String appapi00162(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("法定代表人信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00163.{ext}")
	public String appapi00163(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("企业变更信息（工商）");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00164.{ext}")
	public String appapi00164(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("出入境证件");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00165.{ext}")
	public String appapi00165(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("火化信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00166.{ext}")
	public String appapi00166(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("公安户籍信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00167.{ext}")
	public String appapi00167(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("注销企业信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00168.{ext}")
	public String appapi00168(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("商品房预(销)售许可证");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	@RequestMapping("/appapi00169.{ext}")
	public String appapi00169(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("获取交易部门合同影像（不动产定制）");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(change(rep).getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	/**
	 * @deprecated 省国土资源厅不动产权证（新）
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
		@RequestMapping("/appapi00170.{ext}")
		public String appapi00170(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			Logger log = LoggerUtil.getLogger();
			form.setBusinName("省国土资源厅不动产权证（新）");
			log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			ApiUserContext.getInstance();
			request.setAttribute("centerId", form.getCenterId());
			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
				String rep=msgSendApi001Service.send(request, response);
				log.info("rep="+rep);
				log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
				AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
				response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
				log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
				log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
				return "/index";
			}else
			{
				String rep=msgSendApi001Service.send(request, response);
				response.getOutputStream().write(change(rep).getBytes("UTF-8"));
				log.info("rep="+rep);
				log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
				return "/index";
			}
		}

		/**
		 * @deprecated 贷款结清证明打印（补打）
		 * @param form
		 * @param modelMap
		 * @param request
		 * @param response
		 * @return 
		 * @throws Exception
		 */
			@RequestMapping("/appapi00171.{ext}")
			public String appapi00171(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
					HttpServletResponse response) throws Exception{
				Logger log = LoggerUtil.getLogger();
				form.setBusinName("贷款结清证明打印（补打）");
				log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
				log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
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
			 * @deprecated 个人免缴接口(网厅)
			 * @param form
			 * @param modelMap
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			@RequestMapping("/appapi00172.{ext}")
			public String appapi00172(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
									  HttpServletResponse response) throws Exception{
				Logger log = LoggerUtil.getLogger();
				form.setBusinName("个人免缴接口(网厅)");
				log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
				log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
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
			 * @deprecated 省工商局营业执照（新）
			 * @param form
			 * @param modelMap
			 * @param request
			 * @param response
			 * @return 
			 * @throws Exception
			 */
				@RequestMapping("/appapi00200.{ext}")
				public String appapi00200(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
						HttpServletResponse response) throws Exception{
					Logger log = LoggerUtil.getLogger();
					form.setBusinName("省工商局营业执照（新）");
					log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
					log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
					ApiUserContext.getInstance();
					request.setAttribute("centerId", form.getCenterId());
					if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
						String rep=msgSendApi001Service.send(request, response);
						log.info("rep="+rep);
						log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
						AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
						response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
			 * @deprecated 行政单位信息
			 * @param form
			 * @param modelMap
			 * @param request
			 * @param response
			 * @return 
			 * @throws Exception
			 */
				@RequestMapping("/appapi00204.{ext}")
				public String appapi00204(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
						HttpServletResponse response) throws Exception{
					Logger log = LoggerUtil.getLogger();
					form.setBusinName("行政单位信息");
					log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
					log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
					ApiUserContext.getInstance();
					request.setAttribute("centerId", form.getCenterId());
					if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
						String rep=msgSendApi001Service.send(request, response);
						log.info("rep="+rep);
						log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
						AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
						response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
				 * @deprecated 事业单位信息
				 * @param form
				 * @param modelMap
				 * @param request
				 * @param response
				 * @return 
				 * @throws Exception
				 */
					@RequestMapping("/appapi00203.{ext}")
					public String appapi00203(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
							HttpServletResponse response) throws Exception{
						Logger log = LoggerUtil.getLogger();
						form.setBusinName("事业单位信息");
						log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
						log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
						ApiUserContext.getInstance();
						request.setAttribute("centerId", form.getCenterId());
						if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
							String rep=msgSendApi001Service.send(request, response);
							log.info("rep="+rep);
							log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
							AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
							response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
					 * @deprecated 省公安厅居民户口簿（个人）
					 * @param form
					 * @param modelMap
					 * @param request
					 * @param response
					 * @return 
					 * @throws Exception
					 */
						@RequestMapping("/appapi00205.{ext}")
						public String appapi00205(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
								HttpServletResponse response) throws Exception{
							Logger log = LoggerUtil.getLogger();
							form.setBusinName("省公安厅居民户口簿（个人）");
							log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
							log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
							ApiUserContext.getInstance();
							request.setAttribute("centerId", form.getCenterId());
							if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
								String rep=msgSendApi001Service.send(request, response);
								log.info("rep="+rep);
								log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
								AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
					            response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
						 * @deprecated 民政部_婚姻登记信息核验(个人)
						 * @param form
						 * @param modelMap
						 * @param request
						 * @param response
						 * @return
						 * @throws Exception
						 */
						@RequestMapping("/appapi00206.{ext}")
						public String appapi00206(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
												  HttpServletResponse response) throws Exception{
							Logger log = LoggerUtil.getLogger();
							form.setBusinName("民政部_婚姻登记信息核验(个人)");
							log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
							log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
							ApiUserContext.getInstance();
							request.setAttribute("centerId", form.getCenterId());
							if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
								String rep=msgSendApi001Service.send(request, response);
								log.info("rep="+rep);
								log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
								AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
								response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
						 * @deprecated 民政部_婚姻登记信息核验(双方)
						 * @param form
						 * @param modelMap
						 * @param request
						 * @param response
						 * @return 
						 * @throws Exception
						 */
						@RequestMapping("/appapi00207.{ext}")
						public String appapi00207(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
						        HttpServletResponse response) throws Exception{
						    Logger log = LoggerUtil.getLogger();
						    form.setBusinName("民政部_婚姻登记信息核验(双方)");
						    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
						    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
						    ApiUserContext.getInstance();
						    request.setAttribute("centerId", form.getCenterId());
						    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
						        String rep=msgSendApi001Service.send(request, response);
						        log.info("rep="+rep);
						        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
						        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
						        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 信用中心核查奖惩数据返回接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00208.{ext}")
	public String appapi00208(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("信用中心核查奖惩数据返回接口");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 信用中心核查反馈接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00209.{ext}")
	public String appapi00209(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("信用中心核查反馈接口");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 信用中心信用核查状态
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00210.{ext}")
	public String appapi00210(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("信用中心信用核查状态");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 低保救助信息(新)
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00211.{ext}")
	public String appapi00211(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("低保救助信息(新)");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 省公安厅居民身份证（新）-市平台
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00212.{ext}")
	public String appapi00212(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("省公安厅居民身份证（新）-市平台");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 契税完税信息接口(省地税局)
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00213.{ext}")
	public String appapi00213(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("契税完税信息接口(省地税局)");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 省公安厅居民户口簿电子证照接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00214.{ext}")
	public String appapi00214(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("省公安厅居民户口簿电子证照接口");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * @deprecated 宁波市建设用地规划许可证
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00215.{ext}")
	public String appapi00215(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("宁波市乡村建设规划许可证");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
		String IP = getIpAddr(request);
		System.out.println("省厅IP==========="+IP);
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 公证书证照
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00216.{ext}")
	public String appapi00216(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("公证书证照");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 生成工单
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00217.{ext}")
	public String appapi00217(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("生成工单");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 家庭住房信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00218.{ext}")
	public String appapi00218(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("家庭住房信息查询");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 宁波市房管中心商品房合同查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00219.{ext}")
	public String appapi00219(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("宁波市房管中心商品房合同查询");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 宁波市房管中心二手房合同查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00220.{ext}")
	public String appapi00220(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("宁波市房管中心二手房合同查询");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 组织机构代码信息
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00221.{ext}")
	public String appapi00221(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("组织机构代码信息");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    log.info("centerId======"+form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("53")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated单笔短信发送接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00199.{ext}")
	public String appapi00199(AppApi50004Form form,  ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单笔短信发送接口");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		HashMap remap=null;

		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			  AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			  try{
				   remap=MessageSendUtil.sendSmsCheckAndMessage00057400(form);
		 		   if(!CommonUtil.isEmpty(remap.get("recode"))){
		 			   modelMap.clear();
		 			   modelMap.put("recode", remap.get("recode"));
		 			   modelMap.put("msg", "处理成功");
		 			   modelMap.put("miSeqno", remap.get("miSeqno"));
		 			   log.info("message send successlly");
		 			   String rep=JSON.toJSONString(remap);
		 			   log.info("rep="+rep);
		 			   log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
		 			   response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
		 			   modelMap.clear();
		 		   }else{
		 			   modelMap.clear();
		 			   modelMap.put("recode", "999999");
		 			   modelMap.put("msg", "发送失败");
		 			   modelMap.put("miSeqno", remap.get("miSeqno"));
		 			   String rep=JSON.toJSONString(remap);
		 			   log.info("rep="+rep);
		 			   log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
		 			   response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
		 			   modelMap.clear();
		 		   }
		 	   }catch(Exception e){
		 		   modelMap.clear();
		 		   modelMap.put("recode", "999998");
		 		   modelMap.put("remsg", "发送出现异常");
		 		   modelMap.put("miSeqno", "");
	 			   String rep=JSON.toJSONString(remap);
	 			   log.info("rep="+rep);
	 			   log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
	 			   response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
	 			   modelMap.clear();
		 		   return "";
		 	   }
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			  try{	   		
				   remap=MessageSendUtil.sendSmsCheckAndMessage00057400(form);
		 		   if(!CommonUtil.isEmpty(remap.get("recode"))){
		 			   modelMap.clear();
		 			   modelMap.put("recode", remap.get("recode"));
		 			   modelMap.put("msg", "处理成功");
		 			   modelMap.put("miSeqno", remap.get("miSeqno"));
		 			   log.info("message send successlly");
		 			   String rep=JSON.toJSONString(remap);
		 			   response.getOutputStream().write(rep.getBytes("UTF-8"));
		 			   log.info("rep="+rep);
		 		   }else{
		 			   modelMap.clear();
		 			   modelMap.put("recode", "999999");
		 			   modelMap.put("msg", "发送失败");
		 			   modelMap.put("miSeqno", remap.get("miSeqno"));
		 			   String rep=JSON.toJSONString(remap);
		 			   response.getOutputStream().write(rep.getBytes("UTF-8"));
		 			   log.info("rep="+rep);
		 		   }
		 	   }catch(Exception e){
		 		   modelMap.clear();
		 		   modelMap.put("recode", "999998");
		 		   modelMap.put("remsg", "发送出现异常");
		 		   modelMap.put("miSeqno", "");
	 			   String rep=JSON.toJSONString(remap);
	 			   response.getOutputStream().write(rep.getBytes("UTF-8"));
	 			   log.info("rep="+rep);
		 		   return "";
		 	   }
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}

	@RequestMapping("/appapi00227.{ext}")
	public String appapi00227(AppApi00225Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		form.setBusinName("异地贷款缴存使用证明打印");
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));

		long starTime = System.currentTimeMillis();
		HashMap params = new HashMap();
		String url = "http://172.16.0.208:7001/gjj-wsyyt/servlet/EmpPrintServlet";

		log.info("form.getAccnum()======" + form.getAccnum());
		log.info("form.getCertinum()======" + form.getCertinum());
		log.info("form.getCentername()======" + form.getCentername());
		log.info("form.getUserid()======" + form.getUserid());
		log.info("form.getBrccode()======" + form.getBrccode());
		log.info("form.getChannel()======" + form.getChannel());
		log.info("form.getProjectname()======" + form.getProjectname());
		log.info("form.getCenterId()======" + form.getCenterId());
		log.info("form.getBuzType()======" + form.getBuzType());

		params.put("accnum", form.getAccnum());
		params.put("certinum", form.getCertinum());
		params.put("centername", form.getCentername());
		params.put("userid", form.getUserid());
		params.put("brccode", form.getBrccode());
		params.put("channel", "ZWFWW");
		params.put("projectname", form.getProjectname());

		SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
		System.out.println(result);
		long endTime = System.currentTimeMillis();
		long Time = endTime - starTime;
		System.out.println("请求耗时" + Time + "毫秒");
		JSONObject json = JSONObject.fromObject(result.replace("\n", ""));
		if (json.get("recode").equals("999999")){
			json.put("recode","999999");
			String errMsg =  json.get("errMsg").toString();
			json.put("errMsg",new String(errMsg.getBytes("iso-8859-1"),"utf-8"));
			log.info("errMsg1===" + new String(errMsg.getBytes("iso-8859-1"),"utf-8"));
			log.info("json.toString()===" + json.toString());
			response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
			return json.toString();
		}else{
			String fileByte = json.get("fileByte").toString();
			System.out.println("fileByte================" + fileByte);
			String pdfBase64Str = fileByte;
			log.info("存放pdf路径");
			String saleContractPDFfilepath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "salePDFfilepath");
			log.info("saleContractPDFfilepath============" + saleContractPDFfilepath);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String saleContractNo = dateFormat.format(new Date());
			String filepath = saleContractPDFfilepath + form.getCertinum() + "-" + saleContractNo + ".pdf";
			String filename = form.getCertinum() + "-" + saleContractNo + ".pdf";
			log.info("filepath============" + filepath);
			log.info("filename============" + filename);
			//fileuoload3(filename);
			//String filepath = "D://2.pdf";
			BasetoPdffile(pdfBase64Str, filepath); //生成base64txt
			BasetoPdffile1(pdfBase64Str, filepath);//生成pdf
			System.out.println("生成文件结束");
			json.remove("fileByte");
			//测试
			//json.put("filepath", "http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/");
			//生产
			json.put("filepath", "http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/");
			json.put("filename", filename+".txt");
			json.remove("dataCount");
			json.remove("data");
			log.info("appApi00227 end.");
			log.info("form.getChannel()=" + form.getChannel());

			log.info("gbk");
			log.info("json.toString()==========" + json);
			response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
			return "/index";
		}

	}
	/*文件流解密为pdf格式*/
	public static void BasetoPdffile1(String pdfBase64Str,String filepath){
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try{
			byte[] bytes= Base64Decoder.decodeToBytes(pdfBase64Str);
			ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
			bis=new BufferedInputStream(byteArrayInputStream);
			File file=new File(filepath);
			File path=file.getParentFile();
			if(!path.exists()){
				path.mkdirs();
			}
			fos=new FileOutputStream(file);
			bos=new BufferedOutputStream(fos);

			byte[] buffer=new byte[1024];
			int length=bis.read(buffer);
			while(length!=-1){
				bos.write(buffer,0,length);
				length=bis.read(buffer);
			}
			bos.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				bis.close();
				bos.close();
				fos.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	/*文件流解密为pdf格式*/
	public static void BasetoPdffile(String pdfBase64Str,String filepath){
		BufferedInputStream bis = null;//缓冲输入流
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try{
			//BASE64Decoder encoder = new BASE64Encoder();
			//byte[] bytes = pdfBase64Str.getBytes();
			//String Base64Str = encoder.encode(bytes);
			//byte[] bytes= Base64Decoder.decodeToBytes(pdfBase64Str);//Base64解密字符串pdfBase64Str
			byte[] bytes1 = pdfBase64Str.getBytes();
			ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes1);//把字符串数组新建输入流
			bis=new BufferedInputStream(byteArrayInputStream);//变为缓存输入流
			File file=new File(filepath+".txt");//把filepath下文件存入新建file文件中
			File path=file.getParentFile();//获取文件所在目录
			if(!path.exists()){
				path.mkdirs();
			}
			fos=new FileOutputStream(file);//输出流
			bos=new BufferedOutputStream(fos);//缓存输出流

			byte[] buffer=new byte[1024];
			int length=bis.read(buffer);//每次读取1024个长度字节,length为读取的数据长度
			while(length!=-1){//如果碰到-1说明没有值了.
				bos.write(buffer,0,length);//写入到bos缓存输出流，从下标索引0开始，长度为length
				length=bis.read(buffer);
			}
			bos.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				bis.close();
				bos.close();
				fos.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * @deprecated 宁波公积金注销结果接收
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapiappapi00229.{ext}")
	public String appapiappapi00229(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("宁波公积金注销结果接收");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 个人开户
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00230.{ext}")
	public String appapi00230(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("个人开户");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 合同变更申请前查询申请
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00231.{ext}")
	public String appapi00231(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info("进入指定appapi00231接口");
		log.debug("进入指定appapi00231接口");
		log.info("获取指定231userId" + form.getUserId());
		form.setBusinName("合同变更申请前查询申请");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		ApiUserContext.getInstance();
		log.info("获取指定231userId" + form.getUserId());
		log.info("获取指定231channel" + form.getChannel());
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			log.debug("进入指定appapi00231接口if");
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			log.debug("进入指定appapi00231接口else");
			String rep=msgSendApi001Service.send(request, response);
			response.getOutputStream().write(rep.getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}
	
	
	/**
	 * @deprecated 贷款申请前查询申请
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00232.{ext}")
	public String appapi00232(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("贷款申请前查询申请");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * @deprecated 单位缓缴上传图片查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00234.{ext}")
	public String appapi00234(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("单位停缴缓缴上传文件查询");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        //response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	 * 公务员一件事办理接口
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/appapi00235.{ext}")
	public String appapi00235(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("公务员一件事办理接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		//获取请求json
		StringBuffer sb = new StringBuffer() ;
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String s  ;
		while((s=br.readLine())!=null){
			sb.append(s) ;
		}
		String str =sb.toString();
		System.out.println("InputStream json=====" + str);
		System.out.println("request.getParameter(\"json\")=====" + request.getParameter("json"));
		request.setAttribute("centerId", "00057400");
		String rep=msgSendApi001Service.send(request, response);
		response.getOutputStream().write(rep.getBytes("UTF-8"));
		log.info("yf返回json"+rep);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "/index";
	}
	
	/**
	 * @deprecated 宁波市高层次人才认定
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi00236.{ext}")
	public String appapi00236(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
	    Logger log = LoggerUtil.getLogger();
	    form.setBusinName("宁波市高层次人才认定");
	    log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
	    log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
	    ApiUserContext.getInstance();
	    request.setAttribute("centerId", form.getCenterId());
	    if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
	        String rep=msgSendApi001Service.send(request, response);
	        log.info("rep="+rep);
	        log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	        AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
	        response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
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
	
}
