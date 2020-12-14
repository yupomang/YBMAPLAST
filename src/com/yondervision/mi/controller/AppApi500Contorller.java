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
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi030;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi110;
import com.yondervision.mi.dto.Mi607;
import com.yondervision.mi.dto.Mi902;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.form.AppApi50001Form;
import com.yondervision.mi.form.AppApi50002Form;
import com.yondervision.mi.form.AppApi50003Form;
import com.yondervision.mi.form.AppApi50004Form;
import com.yondervision.mi.service.AppApi401Service;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.service.WebApi030Service;
import com.yondervision.mi.service.WebApi040Service;
import com.yondervision.mi.service.WebApi302Service;
import com.yondervision.mi.service.WebApi607Service;
import com.yondervision.mi.service.impl.AppApi110ServiceImpl;
import com.yondervision.mi.service.impl.AppApi401ServiceImpl;
import com.yondervision.mi.service.impl.AppApi902ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.security.AES;

/**
 * @ClassName: AppApi500Contorller
 * @Description: 微信用户管理
 * @author Caozhongyan
 * @date Jan 20, 2014 2:00:29 PM
 *
 */
@Controller
public class AppApi500Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	@Autowired
	private AppApi110ServiceImpl appApi110Service = null;
	@Autowired
	private AppApi902ServiceImpl appApi902Service = null;
	@Autowired
	private WebApi029Service webApi029ServiceImpl = null;
	@Autowired
	private WebApi030Service webApi030ServiceImpl = null;
	@Autowired
	private WebApi040Service webApi040ServiceImpl = null;
	@Autowired
	private AppApi401ServiceImpl appApi401ServiceImpl = null;
	@Autowired
	private WebApi607Service wppApi607ServiceImpl = null;
	@Autowired
	private WebApi302Service webApi302ServiceImpl = null;
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;

	public WebApi302Service getWebApi302ServiceImpl() {
		return webApi302ServiceImpl;
	}
	public void setWebApi302ServiceImpl(WebApi302Service webApi302ServiceImpl) {
		this.webApi302ServiceImpl = webApi302ServiceImpl;
	}
	public AppApi110ServiceImpl getAppApi110Service() {
		return appApi110Service;
	}
	public void setAppApi110Service(AppApi110ServiceImpl appApi110Service) {
		this.appApi110Service = appApi110Service;
	}

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
	public WebApi030Service getWebApi030ServiceImpl() {
		return webApi030ServiceImpl;
	}
	public void setWebApi030ServiceImpl(WebApi030Service webApi030ServiceImpl) {
		this.webApi030ServiceImpl = webApi030ServiceImpl;
	}
	public WebApi040Service getWebApi040ServiceImpl() {
		return webApi040ServiceImpl;
	}
	public void setWebApi040ServiceImpl(WebApi040Service webApi040ServiceImpl) {
		this.webApi040ServiceImpl = webApi040ServiceImpl;
	}
	public MsgSendApi001Service getMsgSendApi001Service() {
		return msgSendApi001Service;
	}


	public void setAppApi401ServiceImpl(AppApi401ServiceImpl appApi401ServiceImpl) {
		this.appApi401ServiceImpl = appApi401ServiceImpl;
	}

	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}

	public AppApi401Service getAppApi401ServiceImpl() {
		return appApi401ServiceImpl;
	}



	public WebApi607Service getWppApi607ServiceImpl() {
		return wppApi607ServiceImpl;
	}
	public void setWppApi607ServiceImpl(WebApi607Service wppApi607ServiceImpl) {
		this.wppApi607ServiceImpl = wppApi607ServiceImpl;
	}
	/**
	 * 微信用户绑定
	 *
	 * @param form
	 *            请求参数(与APP注册FORMBEAN共用，USERID为微信账号)
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi50001.{ext}")
	public String appapi50001(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("用户绑定");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ApiUserContext.getInstance();


//		AES aes = new AES();
//		if(!CommonUtil.isEmpty(form.getFullName())){
//			form.setFullName(aes.decrypt(form.getFullName()));
//		}
//		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
//			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
//		}

//		OpenFireUtil ofu = new OpenFireUtil();
//		ofu.toOpenFireServer(form);
		request.setAttribute("centerId", form.getCenterId());
		AES aes = new AES();
		form.setUserId(aes.decrypt(form.getUserId()));
		form.setFullName(aes.decrypt(form.getFullName()));
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));

		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
		List<Mi103> list = appApi401ServiceImpl.appapi40110(form);
		if(list.size()>0){
			m.remove("appid");
			m.put("appid", list.get(0).getUserId());
		}else{
			m.remove("appid");
			m.put("appid", "");
		}

		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);

		String resStr = msgSendApi001Service.send(wrapRequest, response);

		JSONObject resJsonObj = JSONObject.fromObject(resStr);
		String recode = null, msg = null;
		if (resJsonObj.has("recode")){
			recode = resJsonObj.getString("recode");
			if (resJsonObj.has("msg")){
				msg = resJsonObj.getString("msg");
			}
			if("000000".equals(recode)){
				String msgtmp = appApi110Service.appApi11004Insert(form, modelMap, request, response);
				if(msgtmp.equals("true")){
					System.out.println(form.getUserId()+"绑定操作统一用户表正确。");

					String authFlg = "";
					String signedphone = "";
					form.setCardno(form.getCardno());
					List<Mi110> mi110List = appApi110Service.appApi11006Select(form);
					if(!CommonUtil.isEmpty(mi110List)){
						if(!CommonUtil.isEmpty(mi110List.get(0).getFreeuse3())
								&& Constants.IS_VALIDFLAG.equals(mi110List.get(0).getFreeuse3().trim())){
							authFlg = Constants.IS_VALIDFLAG;
							signedphone = mi110List.get(0).getFreeuse1();
						}else{
							authFlg = Constants.IS_NOT_VALIDFLAG;
							signedphone = "-";
						}
					}else{
						authFlg = Constants.IS_NOT_VALIDFLAG;
						signedphone = "-";
					}

					HashMap map = new HashMap();
					map.put("recode", Constants.WEB_SUCCESS_CODE);
					map.put("msg", Constants.WEB_SUCCESS_MSG);
					map.put("result", resJsonObj.getJSONObject("result"));
					map.put("authflg", authFlg);
					map.put("signedphone", signedphone);
					ObjectMapper mapper = new  ObjectMapper();
					JSONObject resJsonObjTmp = mapper.convertValue(map, JSONObject.class);
					String resMsg = resJsonObjTmp.toString();
					String encoding = null;
					if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
						encoding = "UTF-8";
					}else {
						encoding = request.getCharacterEncoding();
					}
					response.getOutputStream().write(resMsg.getBytes(encoding));
				}
			}else{
				HashMap map = new HashMap();
				map.put("recode", recode);
				map.put("msg", msg);
				ObjectMapper mapper = new  ObjectMapper();
				JSONObject resJsonObjTmp = mapper.convertValue(map, JSONObject.class);
				String resMsg = resJsonObjTmp.toString();
				String encoding = null;
				if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
					encoding = "UTF-8";
				}else {
					encoding = request.getCharacterEncoding();
				}
				response.getOutputStream().write(resMsg.getBytes(encoding));
				return "";
			}
		}

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 微信用户取消绑定
	 *
	 * @param form
	 *            请求参数(与APP注册FORMBEAN共用，USERID为微信账号)
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50002.{ext}")
	public String appapi50002(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("用户取消绑定");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();

		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		if(!CommonUtil.isEmpty(form.getUserId())){
			form.setUserId(aes.decrypt(form.getUserId()));
		}

		request.setAttribute("centerId", form.getCenterId());
		//HttpServletRequest request1=(HttpServletRequest) request;
		//HashMap m=new HashMap(request.getParameterMap());
		//String rep = "";
		Mi031 mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
		if(CommonUtil.isEmpty(mi031)){
			modelMap.clear();
			modelMap.put("recode", "999997");
			modelMap.put("msg", "用户信息不存在！");
			return "";
		}

		Mi029 mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
		if(CommonUtil.isEmpty(mi029)){
			modelMap.clear();
			modelMap.put("recode", "999998");
			modelMap.put("msg", "客户信息不存在！");
			return "";
		}
		//m.put("bodyCardNumber", mi029.getCertinum());
		//m.put("certinumType", mi029.getCertinumtype());
		//ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);

		//String message = msgSendApi001Service.send(wrapRequest, response);
		//System.out.println(form.getUserId()+"解绑BSP返回："+message);
		//if(message.indexOf("\"recode\":\"000000\"")>0){
		System.out.println(form.getUserId()+"解绑定操作统一用户表正确。");
		webApi029ServiceImpl.webapi02925(form, request, response);
		//}
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}



	/**
	 * 微信用户密码验证
	 *
	 * @param form
	 *            请求参数(与APP注册FORMBEAN共用，USERID为微信账号)
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50003.{ext}")
	public String appapi50003(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("用户绑定");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		request.setAttribute("centerId", form.getCenterId());
		AES aes = new AES();
		form.setUserId(aes.decrypt(form.getUserId()));
		form.setFullName(aes.decrypt(form.getFullName()));
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		// TODO gongq
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));

		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
		List<Mi103> list = appApi401ServiceImpl.appapi40110(form);
		if(list.size()>0){
			m.remove("appid");
			m.put("appid", list.get(0).getUserId());
		}else{
			m.remove("appid");
			m.put("appid", "");
		}

		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);

		String mssage = msgSendApi001Service.send(wrapRequest, response);
		if(mssage.indexOf("\"recode\":\"000000\"")>0){
			System.out.println(form.getUserId()+"绑定操作统一用户表正确。");
		}

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 综合服务平台——验下短信码获取
	 *
	 * @param form
	 *            请求参数(与APP注册FORMBEAN共用，USERID为微信账号)
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50004.{ext}")
	public void appapi50004(AppApi50004Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("短信验证码发送");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		HashMap smsmap = new HashMap();
		request.setAttribute("centerId", form.getCenterId());
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());

		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		String rep = "";
		int num = 0;
		if(!CommonUtil.isEmpty(form.getFullName())){//注册认证
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
			form.setFullName(aes.decrypt(form.getFullName()));

			request.setAttribute("centerId", form.getCenterId());
			rep = msgSendApi001Service.send(request, response);
			HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
			if("000000".equals((String)hasMap.get("recode"))){
				String vcode = appApi902Service.createSms(form.getCenterId(), form.getTel() ,form.getChannel());
				form.setSmstype("03");
				form.setVcode(vcode);
				smsmap = webApi302ServiceImpl.sendSmsCheckAndMessage(form, request, response);
			}else{
//				modelMap.clear();
//				modelMap.put("recode", (String)hasMap.get("999999"));
//				modelMap.put("msg", (String)hasMap.get("您输入的信息有误！请检查"));
//				rep = JsonUtil.getDisableHtmlEscaping().toJson(modelMap);
				response.getOutputStream().write(rep.getBytes(encoding));
				return;
			}
		}else{
			form.setUserId(aes.decrypt(form.getUserId()));
			Mi031 mi031 = webApi029ServiceImpl.webapi02907(form ,request ,response);
			if(CommonUtil.isEmpty(mi031)){
				modelMap.clear();
				modelMap.put("recode", "999997");
				modelMap.put("msg", "用户信息不存在！");
				rep = JsonUtil.getDisableHtmlEscaping().toJson(modelMap);
				response.getOutputStream().write(rep.getBytes(encoding));
				return;
			}

			Mi029 mi029 = webApi029ServiceImpl.webapi02908(mi031.getPersonalid());
			if(CommonUtil.isEmpty(mi029)){
				modelMap.clear();
				modelMap.put("recode", "999998");
				modelMap.put("msg", "客户信息不存在！");
				rep = JsonUtil.getDisableHtmlEscaping().toJson(modelMap);
				response.getOutputStream().write(rep.getBytes(encoding));
				return;
			}else{

				if(mi029.getTel().equals(form.getTel())){
					String vcode = appApi902Service.createSms(form.getCenterId(), form.getTel() ,form.getChannel());
					form.setSmstype("03");
					form.setVcode(vcode);
					smsmap = webApi302ServiceImpl.sendSmsCheckAndMessage(form, request, response);
				}else{
					modelMap.clear();
					modelMap.put("recode", "999996");
					modelMap.put("msg", "用户信息不存在！");
					rep = JsonUtil.getDisableHtmlEscaping().toJson(modelMap);
					response.getOutputStream().write(rep.getBytes(encoding));
					return;
				}

			}
		}

		modelMap.clear();
		if("000000".equals(smsmap.get("recode"))){
			modelMap.put("recode", "000000");
			modelMap.put("msg", "获取短信验证码成功！");
		}else{
			if(!CommonUtil.isEmpty(smsmap.get("msg"))){
				modelMap.put("recode", smsmap.get("recode"));
				modelMap.put("msg", smsmap.get("msg"));
			}else{
				modelMap.put("recode", "999999");
				modelMap.put("msg", "获取短信验证码失败！");
			}

		}
		rep = JsonUtil.getDisableHtmlEscaping().toJson(modelMap);
		response.getOutputStream().write(rep.getBytes(encoding));
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}

	/**
	 *综合服务平台——验证码验证
	 *
	 * @param form
	 *            请求参数(与APP注册FORMBEAN共用，USERID为微信账号)
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50005.{ext}")
	public String appapi50005(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("短信验证码验证");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		request.setAttribute("centerId", form.getCenterId());
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
//		form.setUserId(aes.decrypt(form.getUserId()));
//		form.setFullName(aes.decrypt(form.getFullName()));
//		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		// TODO gongq
//		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));

		modelMap.clear();
		List<Mi902>  list = appApi902Service.validCode(form.getTel(), form.getCheckcode() ,form.getChannel());
		if(list.size()==0){
			modelMap.put("recode", "000001");
			modelMap.put("msg", "验证码错误，请重新填写");
		}else{
			Mi902 mi902 = list.get(0);
			String failuretime = mi902.getFailuretime();
			if(new Long(failuretime)>System.currentTimeMillis()){
				appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
				modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
				modelMap.put("msg",Constants.WEB_SUCCESS_MSG);
			}else{
				modelMap.put("recode", "000001");
				modelMap.put("msg", "验证码失效，请重新获取验证码");
			}
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}


	/**
	 *
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50006.{ext}")
	public String appapi50006(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人身份认证——登录");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());

		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		if(!"0".equals(form.getCheckflag())){
			if(!CommonUtil.isEmpty(m.get("userId"))){
				//针对住建委网站加密问题修改 xzw
				if (!"30".equals(form.getChannel())) {
					form.setUserId(aes.decrypt(form.getUserId()));
				}
			}
			log.debug("个人登录userId : "+form.getUserId()+"type: "+form.getUsertype());
		}

		if("20".equals(form.getChannel())){
			ApiUserContext.getInstance();
		}

		String rep = "";
		HttpServletRequest request1=(HttpServletRequest) request;

		m.put("bodyCardNumber", form.getBodyCardNumber());
		m.put("accnum", form.getAccnum());
		m.put("tel", form.getTel());
		System.out.println("tel22222222222222"+form.getTel());
		m.put("checkcode", form.getCheckcode());
		m.put("checkflag", form.getCheckflag());


		if("1".equals(request.getParameter("AESFlag"))||"30".equals(form.getChannel())){
			m.put("certinumType", "01");
		}else{
			m.put("certinumType", aes.encrypt("01".getBytes()));
			form.setAccnum(aes.decrypt(form.getAccnum()));
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
			if ("40".equals(form.getChannel())) {
				form.setTel(aes.decrypt(form.getTel()));
				form.setCheckcode(aes.decrypt(form.getCheckcode()));
			}
		}
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);

		long starTime=System.currentTimeMillis();
		rep = msgSendApi001Service.send(wrapRequest, response);
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("YF耗时"+Time+"毫秒");

		System.out.println("YF cost "+Time+"milliseconds");

		System.out.println("个人登录验证，接收前置返回报文："+rep);
		String encoding = null;
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);

		form.setTel(String.valueOf(hasMap.get("phone")));
		System.out.println("tel111111111111111"+form.getTel());
		if("000000".equals((String)hasMap.get("recode"))){
			if(!"0".equals(form.getCheckflag())){
				if(!"20".equals(form.getChannel())){
					//住建委网站不解密
					if(!CommonUtil.isEmpty(form.getCertinumType())&& !"1".equals(request.getParameter("AESFlag"))&&!"30".equals(form.getChannel())){
						form.setCertinumType(aes.decrypt(form.getCertinumType()));
					}
					if(CommonUtil.isEmpty(form.getBodyCardNumber())){
						form.setBodyCardNumber(String.valueOf(hasMap.get("certinum")).trim());
					}
					//根据centerid,certinum查询表mi029有没有数据
					Mi029 mi029 = webApi029ServiceImpl.webapi02901(form);
					if(CommonUtil.isEmpty(mi029)){
						//插入mi029表
						webApi029ServiceImpl.webapi02927(hasMap, form ,request ,response);
						if(!"41".equals(form.getChannel())){
							hasMap.put("userid", form.getUserId());
						}
					}else{						try{
						//电话号码
						System.out.println("tel==========="+form.getTel());
						webApi029ServiceImpl.webapi02915(form ,request ,response);
						//更新渠道用户手机渠道信息
						webApi029ServiceImpl.webapi02924(mi029 ,request ,response);
						hasMap.put("userid", webApi029ServiceImpl.webapi02912(mi029 ,form ,request ,response));
					}catch(TransRuntimeErrorException e){
						modelMap.clear();
						modelMap.put("recode", e.getErrcode());
						modelMap.put("msg", e.getMessage());
						String result = JsonUtil.getDisableHtmlEscaping().toJson(modelMap);
						System.out.println("YBMAPZH综合服务50006返回信息："+result);
						if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
							response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
						}else{
							response.getOutputStream().write(rep.getBytes(encoding));
						}
						return "";
					}
					}
					System.out.println("YBMAPZH综合服务50006返回信息："+rep);

					if((!"41".equals(form.getChannel()))&&(!"50".equals(form.getChannel()))){
						hasMap.put("bodyCardNumber", form.getBodyCardNumber());
					}
					mi029 = webApi029ServiceImpl.webapi02901(form);
					if(CommonUtil.isEmpty(mi029)){
						if((!"41".equals(form.getChannel()))&&(!"50".equals(form.getChannel()))){
							hasMap.put("level", "1");
						}
					}else{
						if((!"41".equals(form.getChannel()))&&(!"50".equals(form.getChannel()))){
							hasMap.put("level", mi029.getUselevel());
						}
					}

					if((!"41".equals(form.getChannel()))&&(!"50".equals(form.getChannel()))){
						hasMap.put("cardno", "");
					}
					//民政局格式不能变
					if("41".equals(form.getChannel())){
						hasMap.remove("brcCode");
						hasMap.remove("phone");
						hasMap.remove("accname");
						hasMap.remove("unitaccnum");
						hasMap.remove("certinum");
						hasMap.remove("accnum");
						hasMap.remove("userid");
					}
					rep = JsonUtil.getGson().toJson(hasMap);
					System.out.println("登录前置返回报文："+rep);
					if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
						response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
					}else{
						response.getOutputStream().write(rep.getBytes(encoding));
					}
				}else{
					hasMap.put("bodyCardNumber", form.getBodyCardNumber());
					Mi029 mi029 = webApi029ServiceImpl.webapi02901(form);
					if(CommonUtil.isEmpty(mi029)){
						hasMap.put("level", "1");
					}else{
						hasMap.put("level", mi029.getUselevel());
					}
					hasMap.put("cardno", "");
					hasMap.remove("certinum");
					rep = JsonUtil.getGson().toJson(hasMap);
					System.out.println("登录前置返回报文："+rep);
					response.getOutputStream().write(rep.getBytes(encoding));
				}
			}else{
				if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
					response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
				}else{
					response.getOutputStream().write(rep.getBytes(encoding));
				}
				return "";
			}
		}else{
			System.out.println("登录前置返回报文异常recode："+(String)hasMap.get("recode"));
			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
				response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			}else{
				response.getOutputStream().write(rep.getBytes(encoding));
			}
			return "";
		}

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "";
	}

	/**
	 * 个人用户——重置密码
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50007.{ext}")
	public String appapi50007(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人用户——重置密码");
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getFullName())){
			log.error(ERROR.PARAMS_NULL.getLogText("FullName"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"用户名");
		}
		if(CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.error(ERROR.PARAMS_NULL.getLogText("BodyCardNumber"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"身份证号");
		}
		if(CommonUtil.isEmpty(form.getTel())){
			log.error(ERROR.PARAMS_NULL.getLogText("tel"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预留手机号");
		}
		//if("1".equals(form.getCheckflag())){
		if(CommonUtil.isEmpty(form.getCheckcode())){
			log.error(ERROR.PARAMS_NULL.getLogText("Checkcode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"验证码");
		}
		//}

		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());

		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		if(!"1".equals(request.getParameter("AESFlag"))){
			form.setFullName(aes.decrypt(form.getFullName()));
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
			form.setTel(aes.decrypt(form.getTel()));
			form.setCheckcode(aes.decrypt(form.getCheckcode()));
		}


		//if(!CommonUtil.isEmpty(form.getCheckflag())&&"1".equals(form.getCheckflag())){
		//form.setCheckcode(aes.decrypt(form.getCheckcode()));
			/*List<Mi902>  list = appApi902Service.validCode(form.getTel(), form.getCheckcode() ,form.getChannel());
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
			}*/
		//}
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
			String rep=msgSendApi001Service.send(request, response);
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
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
	 * APPTOKEN更新获取
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50008.{ext}")
	public String appapi50008(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("APPTOKEN更新获取");
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getAppid())){
			log.error(ERROR.PARAMS_NULL.getLogText("appid"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"应用标识");
		}
		if(CommonUtil.isEmpty(form.getAppkey())){
			log.error(ERROR.PARAMS_NULL.getLogText("appkey"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"应用KEY");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.PARAMS_NULL.getLogText("channel"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"渠道信息");
		}

		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());

		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());

		form.setAppid(aes.decrypt(form.getAppid()));
		form.setAppkey(aes.decrypt(form.getAppkey()));

		Mi040 mi040 = webApi040ServiceImpl.webapi04006(form);
		if(CommonUtil.isEmpty(mi040)){
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "未找到配置应用信息");
			return null;
		}

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("apptoken", mi040.getApptoken());
		modelMap.put("validtime", mi040.getValidtime());
		return null;
	}

	/**
	 * 个人用户——密码修改
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50009.{ext}")
	public String appapi50009(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人用户——修改密码");
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		/*if(CommonUtil.isEmpty(form.getTel())){
			log.error(ERROR.PARAMS_NULL.getLogText("tel"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预留手机号");
		}
		if(CommonUtil.isEmpty(form.getCheckcode())){
			log.error(ERROR.PARAMS_NULL.getLogText("Checkcode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"验证码");
		}*/
		if(CommonUtil.isEmpty(form.getPassword())){
			log.error(ERROR.PARAMS_NULL.getLogText("password"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"原密码");
		}
		if(CommonUtil.isEmpty(form.getNewpassword())){
			log.error(ERROR.PARAMS_NULL.getLogText("newpassword"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"新密码");
		}
		if(CommonUtil.isEmpty(form.getConfirmnewpassword())){
			log.error(ERROR.PARAMS_NULL.getLogText("confirmnewpassword"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"确认新密码");
		}
		if(CommonUtil.isEmpty(form.getAccnum()) && CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.error(ERROR.PARAMS_NULL.getLogText("bodycardnumber"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"身份证号或公积金账号");
		}
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
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
	 * 综合服务平台：个人身份认证——注册
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50010.{ext}")
	public String appapi50010(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人身份认证——注册");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());

		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		String sfz = "";
		if(!CommonUtil.isEmpty(m.get("userId"))){
			form.setUserId(aes.decrypt(form.getUserId()));
		}
		log.debug("个人注册userId : "+form.getUserId()+"    type : "+form.getUsertype());

		if("20".equals(form.getChannel())){
			ApiUserContext.getInstance();
		}

		String rep = "";
		HttpServletRequest request1=(HttpServletRequest) request;
		/**
		 *  01	身份证
		 02	军官证
		 03	护照
		 99	其他
		 04	外国人永久居住证
		 20	港澳通行证
		 */
		//form.setBodyCardNumber(form.getUserId());
		m.put("bodyCardNumber", form.getBodyCardNumber());
		m.put("accnum", form.getAccnum());
		m.put("certinumType", aes.encrypt("01".getBytes()));
		form.setAccnum(aes.decrypt(form.getAccnum()));
		form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
		rep = msgSendApi001Service.send(wrapRequest, response);

		System.out.println("个人注册，接收前置返回报文："+rep);
		String encoding = null;
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			if(!CommonUtil.isEmpty(form.getCertinumType())){
				form.setCertinumType(aes.decrypt(form.getCertinumType()));
			}
			if(CommonUtil.isEmpty(form.getBodyCardNumber())){
				form.setBodyCardNumber(String.valueOf(hasMap.get("certinum")).trim());
			}
			Mi029 mi029 = webApi029ServiceImpl.webapi02901(form);
			if(CommonUtil.isEmpty(mi029)){
				webApi029ServiceImpl.webapi02927(hasMap, form ,request ,response);
			}else{
				try{
					webApi029ServiceImpl.webapi02915(form ,request ,response);
					webApi029ServiceImpl.webapi02924(mi029 ,request ,response);
					webApi029ServiceImpl.webapi02912(mi029 ,form ,request ,response);
				}catch(TransRuntimeErrorException e){
					modelMap.clear();
					modelMap.put("recode", e.getErrcode());
					modelMap.put("msg", e.getMessage());
					String result = JsonUtil.getDisableHtmlEscaping().toJson(modelMap);
					System.out.println("YBMAPZH综合服务50010返回信息："+result);
					response.getOutputStream().write(result.getBytes(encoding));
					return "";
				}
			}
			System.out.println("YBMAPZH综合服务50006返回信息："+rep);
			//response.getOutputStream().write(rep.getBytes(encoding));

			hasMap.put("bodyCardNumber", form.getBodyCardNumber());
			mi029 = webApi029ServiceImpl.webapi02901(form);
			if(CommonUtil.isEmpty(mi029)){
				hasMap.put("level", "1");
			}else{
				hasMap.put("level", mi029.getUselevel());
			}
			//Mi031 mi031 = webApi029ServiceImpl.webapi02920(mi029, form, wrapRequest, response);
			//if(CommonUtil.isEmpty(mi031)){
			hasMap.put("userid", form.getBodyCardNumber());
			//}else{
			//hasMap.put("userid", mi031.getUserid());
			//}
			hasMap.put("cardno", "");
			rep = JsonUtil.getGson().toJson(hasMap);
			System.out.println("登录前置返回报文："+rep);
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			System.out.println("登录前置返回报文异常recode："+(String)hasMap.get("recode"));
			response.getOutputStream().write(rep.getBytes(encoding));
			return "";
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "";
	}

	/**
	 * 个人身份高级认证
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50011.{ext}")
	public String appapi50011(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人身份高级认证");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getFullName())){
			log.error(ERROR.PARAMS_NULL.getLogText("FullName"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"用户名");
		}
		if(CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.error(ERROR.PARAMS_NULL.getLogText("BodyCardNumber"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"身份证号");
		}
		if(CommonUtil.isEmpty(form.getTel())){
			log.error(ERROR.PARAMS_NULL.getLogText("tel"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预留手机号");
		}
		if(CommonUtil.isEmpty(form.getCheckcode())){
			log.error(ERROR.PARAMS_NULL.getLogText("Checkcode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"验证码");
		}
		if(CommonUtil.isEmpty(form.getBankcode())){
			log.error(ERROR.PARAMS_NULL.getLogText("Bankcode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"银行卡号");
		}
		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());
//
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		String rep = "";
		form.setFullName(aes.decrypt(form.getFullName()));
		form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		form.setTel(aes.decrypt(form.getTel()));
		form.setBankcode(aes.decrypt(form.getBankcode()));
		form.setCheckcode(aes.decrypt(form.getCheckcode()));

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


		Mi029 mi029 = webApi029ServiceImpl.webapi02901(form);
//		if("2".equals(mi029.getLevel())){
//			modelMap.clear();
//			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//			return "";
//		}
		if(!CommonUtil.isEmpty(mi029)){
			HttpServletRequest request1=(HttpServletRequest) request;
			m.put("bodyCardNumber", mi029.getCertinum());
			m.put("tel", mi029.getTel());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			rep = msgSendApi001Service.send(wrapRequest, response);
			HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
			if("000000".equals((String)hasMap.get("recode"))){
				appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
				log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
				if("2".equals(((String)hasMap.get("level")))){
					webApi029ServiceImpl.webapi02914(mi029, form, wrapRequest, response);
				}
			}else{
				modelMap.clear();
				modelMap.put("recode", (String)hasMap.get("recode"));
				modelMap.put("msg", (String)hasMap.get("msg"));
				return "";
			}
		}else{
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "客户信息错误");
			return "";
		}
		return "";
	}

	/**
	 * 单位注册
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50012.{ext}")
	public String appapi50012(AppApi50002Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位注册");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getOpercode())){
			log.error(ERROR.PARAMS_NULL.getLogText("opercode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位专办员号");
		}
		if(CommonUtil.isEmpty(form.getCertinum())){
			log.error(ERROR.PARAMS_NULL.getLogText("certinum"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位专办员证件号");
		}
		if(CommonUtil.isEmpty(form.getUnitlinkphone2())){
			log.error(ERROR.PARAMS_NULL.getLogText("unitlinkphone2"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预留手机号");
		}
		if(CommonUtil.isEmpty(form.getOpname())){
			log.error(ERROR.PARAMS_NULL.getLogText("opname"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位专办员姓名");
		}

		//验证短信码处理
		//
		List<Mi902>  list = appApi902Service.validCode(form.getUnitlinkphone2(), form.getCheckcode() ,form.getChannel());
		if(CommonUtil.isEmpty(list)){
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

		//验证通过后发往中心认证
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			form.setUnitaccnum((String)hasMap.get("unitaccnum"));
			form.setUnitcustid((String)hasMap.get("unitcustid"));
			form.setOpercode((String)hasMap.get("opercode"));
			form.setLicensenum((String)hasMap.get("licensenum"));
			form.setUnitaccname((String)hasMap.get("unitaccname"));
			appApi902Service.deleteSms(form.getUnitlinkphone2(), form.getCheckcode());
			Mi030 mi030 = webApi030ServiceImpl.webapi03001(form);
			if(!CommonUtil.isEmpty(mi030)){
				webApi030ServiceImpl.webapi03002(form);
			}
		}else{
			modelMap.clear();
			modelMap.put("recode", (String)hasMap.get("recode"));
			modelMap.put("msg", (String)hasMap.get("msg"));
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		return "";
	}

	/**
	 * 单位登录
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50013.{ext}")
	public String appapi50013(AppApi50002Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位登录");
		System.out.println("start appapi50013");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
			/*if(CommonUtil.isEmpty(form.getOpercode())){
				log.error(ERROR.PARAMS_NULL.getLogText("opercode"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位专办员号");
			}*/


		//验证通过后发往中心认证
		request.setAttribute("centerId", form.getCenterId());

		long starTime=System.currentTimeMillis();
		String rep = msgSendApi001Service.send(request, response);
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("YF耗时"+Time+"毫秒");

		System.out.println("YF cost "+Time+"milliseconds");

		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);

		String encoding = null;
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		log.debug("####1####");
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		if("000000".equals((String)hasMap.get("recode"))){
			log.debug("####2####");
			form.setUnitaccnum((String)hasMap.get("unitaccnum"));
			form.setUnitcustid((String)hasMap.get("unitaccnum"));
			//form.setUnitcustid((String)hasMap.get("unitcustid"));
			//form.setOpercode((String)hasMap.get("opercode"));
			//form.setLicensenum((String)hasMap.get("licensenum"));
			form.setUnitaccname((String)hasMap.get("unitaccname"));
			log.debug("####3####");
			//单位用户信息查询
			Mi030 mi030 = webApi030ServiceImpl.webapi03001(form);
			log.debug("####4####");
			//if(!CommonUtil.isEmpty(mi030)){
			//	webApi030ServiceImpl.webapi03002(form);
			//}
			//==========================================
			//form.setUnitaccnum((String)hasMap.get("unitaccnum"));//单位账号
			//form.setUnitaccname((String)hasMap.get("unitaccname"));//单位名称
			//form.setOpname((String)hasMap.get("opname"));//专办员姓名
			//form.setUnitaddr((String)hasMap.get("unitaddr"));//单位地址
			//form.setUnitcustid((String)hasMap.get("unitcustid"));//单位客户号
			//form.setUnitlinkphone2((String)hasMap.get("unitphone"));//单位电话
			//form.setExitdate((String)hasMap.get("exitdate"));//成立时间
			//AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			//form.setOpercode(aes.decrypt(form.getOpercode()));
			//Mi030 mi030 = webApi030ServiceImpl.webapi03001(form);
			if(CommonUtil.isEmpty(mi030)){
				log.debug("####5####");
				//单位用户信息添加
				mi030 = webApi030ServiceImpl.webapi03002(form);
				log.debug("####8####");
				//单位登录返回信息更新
				webApi030ServiceImpl.webapi03003(form ,request ,response);
			}else{
				log.debug("####6####");
				form.setUnitcustid(mi030.getUnitcustomerid());
				webApi030ServiceImpl.webapi03003(form ,request ,response);
			}
			log.debug("####7####");
			//返回信息
			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
				response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			}else{
				response.getOutputStream().write(rep.getBytes(encoding));
			}
		}else{
			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
				response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			}else{
				response.getOutputStream().write(rep.getBytes(encoding));
			}
		}
		return "";
	}

	/**
	 * 单位密码找回，密码重置
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50014.{ext}")
	public String appapi50014(AppApi50002Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位密码找回、密码重置");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getOpercode())){
			log.error(ERROR.PARAMS_NULL.getLogText("opercode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位专办员号");
		}
		if(CommonUtil.isEmpty(form.getCertinum())){
			log.error(ERROR.PARAMS_NULL.getLogText("certinum"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位专办员证件号");
		}
		if(CommonUtil.isEmpty(form.getUnitlinkphone2())){
			log.error(ERROR.PARAMS_NULL.getLogText("unitlinkphone2"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预留手机号");
		}
		if(CommonUtil.isEmpty(form.getOpname())){
			log.error(ERROR.PARAMS_NULL.getLogText("opname"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位专办员姓名");
		}
		if(CommonUtil.isEmpty(form.getCheckcode())){
			log.error(ERROR.PARAMS_NULL.getLogText("checkcode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"短信验证码");
		}

		//验证短信码处理
		//
		List<Mi902>  list = appApi902Service.validCode(form.getUnitlinkphone2(), form.getCheckcode() ,form.getChannel());
		if(CommonUtil.isEmpty(list)){
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

		//验证通过后发往中心认证
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);

		if("000000".equals((String)hasMap.get("recode"))){
			appApi902Service.deleteSms(form.getUnitlinkphone2(), form.getCheckcode());
		}

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		return "";
	}

	/**
	 * 单位密码修改
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50015.{ext}")
	public String appapi50015(AppApi50002Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位密码修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getPassword())){
			log.error(ERROR.PARAMS_NULL.getLogText("password"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"原密码");
		}
		if(CommonUtil.isEmpty(form.getNewpasswd())){
			log.error(ERROR.PARAMS_NULL.getLogText("newpasswd"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"新密码");
		}
		if(CommonUtil.isEmpty(form.getUnitaccnum())){
			log.error(ERROR.PARAMS_NULL.getLogText("unitaccnum"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位账号");
		}


		//验证短信码处理
		//
//		List<Mi902>  list = appApi902Service.validCode(form.getUnitlinkphone2(), form.getCheckcode() ,form.getChannel());
//		if(CommonUtil.isEmpty(list)){
//			modelMap.clear();
//			modelMap.put("recode", "000001");
//			modelMap.put("msg", "验证码错误，请重新填写");
//			return "";
//		}else{
//			Mi902 mi902 = list.get(0);
//			String failuretime = mi902.getFailuretime();
//			if(new Long(failuretime)<System.currentTimeMillis()){
//				modelMap.clear();
//				modelMap.put("recode", "000001");
//				modelMap.put("msg", "验证码失效，请重新获取验证码");
//				return "";
//			}
//		}

		//验证通过后发往中心认证
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
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
	 * 开发商用户注册
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50016.{ext}")
	public String appapi50016(AppApi50003Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("开发商用户注册");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ParamCheck pc = new ParamCheck();
//		pc.check(form);
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 开发商用户登录
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50017.{ext}")
	public String appapi50017(AppApi50003Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("开发商用户登录");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ParamCheck pc = new ParamCheck();
//		pc.check(form);
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 开发商用户密码找回、重置密码
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50018.{ext}")
	public String appapi50018(AppApi50003Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("开发商用户密码找回、重置密码");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ParamCheck pc = new ParamCheck();
//		pc.check(form);

		//验证短信码处理
		//
		List<Mi902>  list = appApi902Service.validCode(form.getTel(), form.getCheckcode() ,form.getChannel());
		if(CommonUtil.isEmpty(list)){
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

		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);

		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);

		if("000000".equals((String)hasMap.get("recode"))){
			appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
		}

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";

	}

	/**
	 * 开发商用户修改登录密码
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50019.{ext}")
	public String appapi50019(AppApi50003Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("开发商用户修改登录密码");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		ParamCheck pc = new ParamCheck();
//		pc.check(form);
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 客户VIP确认，敏感用户确认
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50020.{ext}")
	public String appapi50020(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("客户VIP敏感用户确认");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		Mi029 mi029 = webApi029ServiceImpl.webapi02910(form);
		if(!CommonUtil.isEmpty(mi029)){
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			Mi607 mi607 = wppApi607ServiceImpl.webapi60703(mi029.getPersonalid());
			if(!CommonUtil.isEmpty(mi607)){
				modelMap.put("black", "1");
			}else{
				modelMap.put("black", "0");
			}
			modelMap.put("vip", mi029.getVip());
			modelMap.put("sensitive", mi029.getSensitive());
		}else{
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "未查询到相应信息");
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}


	/**
	 * 暂未使用
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50021.{ext}")
	public String appapi50021(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("开发商用户修改登录密码");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		modelMap.clear();
		modelMap.put("recode", "999999");
		modelMap.put("msg", "未查询到相应信息");

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 客户预留手机号确认，同时更新
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50022.{ext}")
	public String appapi50022(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("客户预留手机号确认");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		ApiUserContext.getInstance();

		request.setAttribute("centerId", form.getCenterId());
		String msg = msgSendApi001Service.send(request, response);

		//{"recode":"000000","msg":"成功","isExist":"0","certinum":"134534534"}

		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(msg, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			if("1".equals((String)hasMap.get("isExist"))){
				form.setBodyCardNumber((String)hasMap.get("certinum"));
				webApi029ServiceImpl.webapi02915(form, request, response);
			}
		}else{
			modelMap.clear();
			modelMap.put("recode", (String)hasMap.get("recode"));
			modelMap.put("msg", (String)hasMap.get("msg"));
			return "";
		}

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 根据客户ID查询个人信息
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50023.{ext}")
	public String appapi50023(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("客户预留手机号确认");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getUserId())){
			modelMap.clear();
			modelMap.put("recode", "999998");
			modelMap.put("msg", "参数不正确，请检查参数");
			modelMap.put("result", "");
			return "";
		}
		if(CommonUtil.isEmpty(form.getAppid())){
			modelMap.clear();
			modelMap.put("recode", "999998");
			modelMap.put("msg", "参数不正确，请检查参数");
			modelMap.put("result", "");
			return "";
		}
		if(CommonUtil.isEmpty(form.getAppkey())){
			modelMap.clear();
			modelMap.put("recode", "999998");
			modelMap.put("msg", "参数不正确，请检查参数");
			modelMap.put("result", "");
			return "";
		}
		AES aes = new AES();

		form.setUserId(aes.decrypt(form.getUserId()));
		form.setAppid(aes.decrypt(form.getAppid()));
		form.setAppkey(aes.decrypt(form.getAppkey()));
		if(!"yondervisonGD".equals(form.getAppid())){
			modelMap.clear();
			modelMap.put("recode", "999998");
			modelMap.put("msg", "参数不正确，请检查参数");
			modelMap.put("result", "");
			return "";
		}
		if(!"EiOwXpJeCaQmYzHgVnRu".equals(form.getAppkey())){
			modelMap.clear();
			modelMap.put("recode", "999998");
			modelMap.put("msg", "参数不正确，请检查参数");
			modelMap.put("result", "");
			return "";
		}

		Mi029 mi029 = webApi029ServiceImpl.webapi02908(form.getUserId());

		if(CommonUtil.isEmpty(mi029)){
			modelMap.clear();
			modelMap.put("recode", "999997");
			modelMap.put("msg", "未查询到相关信息！");
			modelMap.put("result", "");
			return "";
		}else{
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", mi029);
		}




		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	/**
	 * 个人用户——密码修改
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50024.{ext}")
	public String appapi50024(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人用户——重置密码");
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getTel())){
			log.error(ERROR.PARAMS_NULL.getLogText("tel"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预留手机号");
		}
		if(CommonUtil.isEmpty(form.getCheckcode())){
			log.error(ERROR.PARAMS_NULL.getLogText("Checkcode"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"验证码");
		}
		if(CommonUtil.isEmpty(form.getPassword())){
			log.error(ERROR.PARAMS_NULL.getLogText("password"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"原密码");
		}
		if(CommonUtil.isEmpty(form.getNewpassword())){
			log.error(ERROR.PARAMS_NULL.getLogText("newpassword"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"新密码");
		}
		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());

		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());

		form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
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
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return null;
	}

	/**
	 * 公共信息验证
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50025.{ext}")
	public String appapi50025(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("公共信息验证");
//		if(CommonUtil.isEmpty(form.getCenterId())){
//			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
//		}
//		if(CommonUtil.isEmpty(form.getTel())){
//			log.error(ERROR.PARAMS_NULL.getLogText("tel"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预留手机号");
//		}
//		if(CommonUtil.isEmpty(form.getCheckcode())){
//			log.error(ERROR.PARAMS_NULL.getLogText("Checkcode"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"验证码");
//		}
//		if(CommonUtil.isEmpty(form.getPassword())){
//			log.error(ERROR.PARAMS_NULL.getLogText("password"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"原密码");
//		}
//		if(CommonUtil.isEmpty(form.getNewpassword())){
//			log.error(ERROR.PARAMS_NULL.getLogText("newpassword"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"新密码");
//		}
		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());

//		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());

//		form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
//		form.setTel(aes.decrypt(form.getTel()));
//		form.setCheckcode(aes.decrypt(form.getCheckcode()));


//		List<Mi902>  list = appApi902Service.validCode(form.getTel(), form.getCheckcode() ,form.getChannel());
//		if(CommonUtil.isEmpty(list)){
//			modelMap.put("recode", "000001");
//			modelMap.put("msg", "验证码错误，请重新填写");
//			return "";
//		}else{
//			Mi902 mi902 = list.get(0);
//			String failuretime = mi902.getFailuretime();
//			if(new Long(failuretime)<System.currentTimeMillis()){
//				modelMap.clear();
//				modelMap.put("recode", "000001");
//				modelMap.put("msg", "验证码失效，请重新获取验证码");
//				return "";
//			}
//		}
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return null;
	}

	/**
	 * 银行账号有效性验证
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50026.{ext}")
	public String appapi50026(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("银行账号有效性验证");
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return null;
	}

	/**
	 * 银行还款账号维护
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50027.{ext}")
	public String appapi50027(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("银行还款账号维护");
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		msgSendApi001Service.send(request, response);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return null;
	}

	/**
	 * 自定义用户名找回
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50028.{ext}")
	public String appapi50028(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("自定义用户名找回");

		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());


		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());

//		form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
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

		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);

		if("000000".equals((String)hasMap.get("recode"))){
			appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
		}

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return null;
	}

	/**
	 * 渠道实名认证核准
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50029.{ext}")
	public String appapi50029(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("渠道实名认证核准");

		request.setAttribute("centerId", form.getCenterId());
		HashMap m=new HashMap(request.getParameterMap());

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

		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		if("000000".equals((String)hasMap.get("recode"))){
			appApi902Service.deleteSms(form.getTel(), form.getCheckcode());
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return null;
	}


	/**
	 * 中心手机号码预留确认(昆明注册认证前发往BSP检查：手机号，姓名，证件号)
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50030.{ext}")
	public String appapi50030(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("中心手机号码预留确认");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		/**
		 *姓名:fullName
		 *证件号:bodyCardNumber
		 *手机号:tel
		 */
		request.setAttribute("centerId", form.getCenterId());
		String msg = msgSendApi001Service.send(request, response);
		System.out.println("中心手机号码预留确认YBMAPZH接收"+msg);

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return null;
	}

	/**
	 * 手机号码银行校验(昆明注册认证前发往BSP再到银行检查：手机号，姓名，证件号，银行编码)
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50031.{ext}")
	public String appapi50031(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机号码银行校验");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		/**
		 *姓名:fullName
		 *证件号:bodyCardNumber
		 *手机号:tel
		 *银行代码:bankcode
		 */
		request.setAttribute("centerId", form.getCenterId());
		String msg = msgSendApi001Service.send(request, response);
		System.out.println("手机号码银行校验YBMAPZH接收"+msg);

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return null;
	}


	/**
	 * 去核心获取手机验证码（综合服务不做处理，核心维护验证码并发送给用户）
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50032.{ext}")
	public String appapi50032(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("核心生成短信验证码");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		/**
		 *姓名:fullName
		 *证件号:bodyCardNumber
		 *手机号:tel
		 */
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
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
	 * @deprecated 多级字典及摘要接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50033.{ext}")
	public String appapi50033(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("多级字典及摘要接口");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
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
	 * @deprecated 个人面签
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50034.{ext}")
	public String appapi50034(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人面签");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
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
	 * @deprecated U盾信息修改
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50035.{ext}")
	public String appapi50035(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("U盾信息修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("52")){
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
	 * @deprecated 个人面签发送验证码
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50036.{ext}")
	public String appapi50036(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人面签发送验证码");
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
	 * @deprecated 单位销户
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi50037.{ext}")
	public String appapi50037(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("单位销户");
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
}
