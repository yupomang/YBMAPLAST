/**
 * 
 */
package com.yondervision.mi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi110;
import com.yondervision.mi.dto.Mi119;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.form.AppApi40108Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi40103Result;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.AppApi401Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.impl.AppApi110ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.WkfAccessTokenUtil;
import com.yondervision.mi.util.security.AES;

/**
 * @ClassName: AppApi401Contorller
 * @Description: 用户管理
 * @author Caozhongyan
 * @date Oct 12, 2013 3:29:54 PM
 * 
 */
@Controller
public class AppApi401Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	@Autowired
	private AppApi110ServiceImpl appApi110Service = null;
	public AppApi110ServiceImpl getAppApi110Service() {
		return appApi110Service;
	}

	public void setAppApi110Service(AppApi110ServiceImpl appApi110Service) {
		this.appApi110Service = appApi110Service;
	}

	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	@Autowired
	private AppApi401Service appApi401ServiceImpl = null;

	public AppApi401Service getAppApi401ServiceImpl() {
		return appApi401ServiceImpl;
	}

	public void setAppApi401ServiceImpl(AppApi401Service appApi401ServiceImpl) {
		this.appApi401ServiceImpl = appApi401ServiceImpl;
	}
	@Autowired
	private AppApi103Service appApi103ServiceImpl = null;
	public void setAppApi103ServiceImpl(AppApi103Service appApi103ServiceImpl) {
		this.appApi103ServiceImpl = appApi103ServiceImpl;
	}

	/**
	 * 手机APP取验证码
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40101.{ext}")
	public void appapi40101(AppApiCommonForm form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP验证码获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		appApi401ServiceImpl.appapi40101(form,response);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}

	/**
	 * 用户注册
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi40102.{ext}")
	public String appapi40102(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP用户注册");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}		
		List<Mi119> list = appApi401ServiceImpl.appapi40108(form);
		if(list.isEmpty()||list.size()==0){
			log.error(ERROR.NO_DATA.getLogText("deviceToken"));
			throw new TransRuntimeErrorException(APP_ALERT.APP_ZC.getValue(),"匿名用户验证信息错误");
		}
		Mi119 mi119 = list.get(0);
		if(form.getCheckid().toLowerCase().equals(mi119.getCheckid())){
			String checkDate = mi119.getCheckdate();
			SimpleDateFormat formatter = new SimpleDateFormat(
					Constants.DATE_TIME_FORMAT);
			Date date = new Date();
			if (checkDate.compareTo(formatter.format(date)) < 0) {
				log.error(ERROR.SYS.getLogText("验证码超时"));
				throw new TransRuntimeErrorException(APP_ALERT.APP_ZC.getValue(),"验证码超时");				
			}				
		}else{
			log.error(ERROR.SYS.getLogText("验证码验证失败"));
			throw new TransRuntimeErrorException(APP_ALERT.APP_ZC.getValue(),"验证码验证失败");			
		}
		appApi401ServiceImpl.appapi40111(form);
		request.setAttribute("centerId",form.getCenterId());
		if(CommonUtil.isEmpty(form.getIscheck())||"1001".equals(form.getIscheck())){
			String req = msgSendApi001Service.send(request, response);
			HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(req, HashMap.class);
			System.out.println(req);
			if("000000".equals((String)hasMap.get("recode"))){
				if(form.getCenterId().equals("00057400")){
					form.setSurplusAccount(hasMap.get("gjjzh").toString());
				}
				appApi401ServiceImpl.appapi40102(form);
			}else{
//				modelMap.clear();
//				modelMap.put("recode", (String)hasMap.get("recode"));
//				modelMap.put("msg", (String)hasMap.get("msg"));
				HashMap map = new HashMap();
				map.put("recode", (String)hasMap.get("recode"));
				map.put("msg", (String)hasMap.get("msg"));
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
//				throw new TransRuntimeErrorException(APP_ALERT.APP_ZC.getValue(),(String)hasMap.get("msg"));
			}
		}else if("1002".equals(form.getIscheck())){
			appApi401ServiceImpl.appapi40102(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			return "";
		}else{
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "上传参数缺失请检查");
			return "";
		}
		
		
//		OpenFireUtil ofu = new OpenFireUtil();
//		ofu.toOpenFireServer(form);		
//		modelMap.clear();
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		HashMap map = new HashMap();
		map.put("recode", Constants.WEB_SUCCESS_CODE);
		map.put("msg", Constants.WEB_SUCCESS_MSG);
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
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";
	}

	/**
	 * 手机APP用户登录
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40103.{ext}")
	public String appapi40103(AppApi40102Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP用户登录");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}
//		if (CommonUtil.isEmpty(form.getDevtoken())) {
//			log.error(ERROR.NO_DATA.getLogText("devtoken"));
//			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"设备token未上传");			
//		}
		if ( CommonUtil.isEmpty(form.getPassword())) {
			log.error(ERROR.NO_DATA.getLogText("password"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"密码不正确");			
		}
		
		if(!CommonUtil.isEmpty(form.getCheckid())){
			List<Mi119> list = appApi401ServiceImpl.appapi40108(form);
			if(list.isEmpty()||list.size()==0){
				log.error(ERROR.NO_DATA.getLogText("deviceToken"));
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"匿名用户验证信息错误");
			}
			Mi119 mi119 = list.get(0);
			if(form.getCheckid().toLowerCase().equals(mi119.getCheckid())){
				String checkDate = mi119.getCheckdate();
				SimpleDateFormat formatter = new SimpleDateFormat(
						Constants.DATE_TIME_FORMAT);
				Date date = new Date();
				if (checkDate.compareTo(formatter.format(date)) < 0) {
					log.error(ERROR.SYS.getLogText("验证码超时"));
					throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"验证码超时");				
				}				
			}else{
				log.error(ERROR.SYS.getLogText("验证码验证失败"));
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"验证码验证失败");			
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(
				Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		List<Mi103> list = appApi401ServiceImpl.appapi40107(form);
		if (list.isEmpty()||list.size()==0) {
			log.error(ERROR.NO_DATA.getLogText("userId,centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_NO_USER.getValue(),"用户不存在");			
		} else {
			String date1 = list.get(0).getWaitdate();
			if (list.get(0).getWaitnum() > 5) {
				if (date1.compareTo(formatter.format(date)) > 0) {
					java.util.Date dateNo1 = formatter.parse(date1);
					java.util.Date dateNo2 = formatter.parse(formatter
							.format(date));
					long l = dateNo1.getTime() - dateNo2.getTime();
					long day = l / (24 * 60 * 60 * 1000);
					long hour = (l / (60 * 60 * 1000));
					long min = ((l / (60 * 1000)));
					long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60
							* 60 - min * 60);
					System.out.println("" + day + "天" + hour + "小时" + min
							+ "分" + s + "秒");
					modelMap.clear();
//					modelMap.put("recode", WEB_ALERT.LOGIN_NO_USER);
//					modelMap.put("msg", "请稍后登录:" + min + "分" + s + "秒");
					String msg = "";
					if(day>0){
						msg = "错误提交次数大于6,请" + day + "天" + hour +"小时" + min + "分" + s + "秒后重试";
					}else if(hour>0){
						msg = "错误提交次数大于6,请" + hour + "小时" + min + "分" + s + "秒后重试";
					}else if(min>0){
						msg = "错误提交次数大于6,请" + min + "分" + s + "秒后重试";
					}else{
						msg = "错误提交次数大于6,请" + s + "秒后重试";
					}
					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), msg);					
				}
			}
		}
		boolean is40103 = appApi401ServiceImpl.appapi40103(form);
		if (!is40103) {
			appApi401ServiceImpl.appapi40106(form);
			log.error(ERROR.SYS.getLogText("用户登录失败"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_NO_USER.getValue(),"账户或密码错误");			
		}
		//**************************************++++++++++++++++++
		String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());
		String bindkey = "";
		if("00".equals(bindkeytype)){
			bindkey = list.get(0).getAccnum();
		}else if("01".equals(bindkeytype)){			
			bindkey = list.get(0).getCertinum();
		}else if("02".equals(bindkeytype)){
			bindkey = list.get(0).getCardno();
		}else if("03".equals(bindkeytype)){
			
		}else if("04".equals(bindkeytype)){
			
		}
		//**************************************++++++++++++++++++
//		ApiUserContext.saveToSession();
		ApiUserContext.saveToSession(null,aes.encrypt(bindkey.getBytes("UTF-8")));
		ApiUserContext.getInstance();
		Mi103 mi103 = list.get(0);
		AppApi40103Result resultList = new AppApi40103Result();
		resultList.setAccname(aes.encrypt(mi103.getAccname().getBytes("UTF-8")));
		resultList.setAccnum(aes.encrypt(mi103.getAccnum().getBytes("UTF-8")));
		resultList.setCardno(aes.encrypt(((CommonUtil.isEmpty(mi103.getCardno())?"":mi103.getCardno()).getBytes("UTF-8"))));
		resultList.setCertinum(aes.encrypt((CommonUtil.isEmpty(mi103.getCertinum())?"":mi103.getCertinum()).getBytes("UTF-8")));
		resultList.setEmail(mi103.getEmail());
		resultList.setPhone(mi103.getPhone());
		resultList.setHavePwdFlag(mi103.getFreeuse1()==null?"0":mi103.getFreeuse1());
		if(!CommonUtil.isEmpty(mi103.getFreeuse2())){
			resultList.setImgurl(mi103.getFreeuse2());
		}else{
			resultList.setImgurl("");
		}
		
		form.setSurplusAccount(mi103.getAccnum());
		form.setIdcardNumber(mi103.getCertinum());
		form.setCardno(mi103.getCardno());
		List<Mi110> mi110List = appApi110Service.appApi11006Select(form);
		if(!CommonUtil.isEmpty(mi110List)){
			if(!CommonUtil.isEmpty(mi110List.get(0).getAuthflag())
					&& Constants.IS_VALIDFLAG.equals(mi110List.get(0).getAuthflag().trim())){
				resultList.setAuthflg(Constants.IS_VALIDFLAG);
				resultList.setSignedphone(mi110List.get(0).getSignedphone());
			}else{
				resultList.setAuthflg(Constants.IS_NOT_VALIDFLAG);
				resultList.setSignedphone("-");
			}
		}else{
			resultList.setAuthflg(Constants.IS_NOT_VALIDFLAG);
			resultList.setSignedphone("-");
		}
		
		form.setIdcardNumber(mi103.getCertinum());
		try{
			if(!"1002".equals(form.getIscheck())){
				appApi110Service.appApi11001Select(form);//互踢处理
			}			
		}catch(TransRuntimeErrorException e){
			throw e;
		}
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		modelMap.put("wkfdata",WkfAccessTokenUtil.WKF_Login(form.getUserId()));
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}

	/**
	 * 手机APP密码找回
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi40104.{ext}")
	public String appapi40104(AppApi40102Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP密码找回");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}
		if (CommonUtil.isEmpty(form.getFullName())) {
			log.error(ERROR.NO_DATA.getLogText("fullName"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"姓名为空");			
		}
		if (CommonUtil.isEmpty(form.getPassword())) {
			log.error(ERROR.NO_DATA.getLogText("password"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"密码为空");			
		}
		if (CommonUtil.isEmpty(form.getIdcardNumber())) {
			log.error(ERROR.NO_DATA.getLogText("idcardNumber"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"证件号码为空");			
		}
		if (CommonUtil.isEmpty(form.getUserId())) {
			log.error(ERROR.NO_DATA.getLogText("userId"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"用户ID为空");			
		}
		
		HashMap map = new HashMap();
		
		if(!CommonUtil.isEmpty(form.getIscheck())&&"1001".equals(form.getIscheck())){
			request.setAttribute("centerId", form.getCenterId());
			String req = msgSendApi001Service.send(request, response);
			HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(req, HashMap.class);
			System.out.println(req);
			if("000000".equals((String)hasMap.get("recode"))){
				if (appApi401ServiceImpl.appapi40104(form) == 0) {
					log.error(ERROR.SYS.getLogText("更新失败"));
					throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"密码找回失败，请正确填写用户信息");			
				}
				map.put("recode", Constants.WEB_SUCCESS_CODE);
				map.put("msg", Constants.WEB_SUCCESS_MSG);
			}else{
				map.put("recode", (String)hasMap.get("recode"));
				map.put("msg", (String)hasMap.get("msg"));
			}
		}else{
			if (appApi401ServiceImpl.appapi40104(form) == 0) {
				log.error(ERROR.SYS.getLogText("更新失败"));
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"密码找回失败，请正确填写用户信息");			
			}
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
		}

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
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";
	}

	/**
	 * 手机APP信息修改
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40105.{ext}")
	public String appapi40105(AppApi40102Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP信息修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}
		ApiUserContext.getInstance();
		if (CommonUtil.isEmpty(form.getPassword())) {
//			log.error(ERROR.NO_DATA.getLogText("password"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"原密码为空");			
		}				
		
		if(!CommonUtil.isEmpty(form.getCheckid())){
			List<Mi119> list = appApi401ServiceImpl.appapi40108(form);
			if(list.isEmpty()||list.size()==0){
				log.error(ERROR.NO_DATA.getLogText("deviceToken"));
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"匿名用户验证信息错误");
			}
			Mi119 mi119 = list.get(0);
			if(form.getCheckid().toLowerCase().equals(mi119.getCheckid())){
				String checkDate = mi119.getCheckdate();
				SimpleDateFormat formatter = new SimpleDateFormat(
						Constants.DATE_TIME_FORMAT);
				Date date = new Date();
				if (checkDate.compareTo(formatter.format(date)) < 0) {
					log.error(ERROR.SYS.getLogText("验证码超时"));
					throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"验证码超时");				
				}				
			}else{
				log.error(ERROR.SYS.getLogText("验证码验证失败"));
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"验证码验证失败");			
			}
		}
		
		
		SimpleDateFormat formatter = new SimpleDateFormat(
				Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		List<Mi103> list = appApi401ServiceImpl.appapi40107(form);
		if (list.isEmpty()||list.size()==0) {
			log.error(ERROR.SYS.getLogText("用户不存在或用户不可用"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_NO_USER.getValue(),"用户不存在或用户不可用");			
		} else {
			String date1 = list.get(0).getWaitdate();
			if (list.get(0).getWaitnum() > 5) {
				if (date1.compareTo(formatter.format(date)) > 0) {
					java.util.Date dateNo1 = formatter.parse(date1);
					java.util.Date dateNo2 = formatter.parse(formatter
							.format(date));
					long l = dateNo1.getTime() - dateNo2.getTime();
					long day = l / (24 * 60 * 60 * 1000);
					long hour = (l / (60 * 60 * 1000));
					long min = ((l / (60 * 1000)));
					long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60
							* 60 - min * 60);
					System.out.println("" + day + "天" + hour + "小时" + min
							+ "分" + s + "秒");
					log.error(ERROR.SYS.getLogText("请稍后登录:" + min + "分" + s + "秒"));
					
					String msg = "";
					if(day>0){
						msg = "错误提交次数大于6,请" + day + "天" + hour +"小时" + min + "分" + s + "秒后重试";
					}else if(hour>0){
						msg = "错误提交次数大于6,请" + hour + "小时" + min + "分" + s + "秒后重试";
					}else if(min>0){
						msg = "错误提交次数大于6,请" + min + "分" + s + "秒后重试";
					}else{
						msg = "错误提交次数大于6,请" + s + "秒后重试";
					}
					
					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), msg);					
				}
			}
		}			
		int i = appApi401ServiceImpl.appapi40105(form);
		if(i==0){
			appApi401ServiceImpl.appapi40106(form);
			log.error(ERROR.SYS.getLogText("用户信息修改失败"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"原密码错误，重新输入");			
		}
		form.setFullName("");
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 手机APP设备注销
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40106.{ext}")
	public String appapi40106(AppApi40102Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP设备注销");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}
		if (CommonUtil.isEmpty(form.getUserId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"用户ID信息为空");			
		}
		if (CommonUtil.isEmpty(form.getDeviceToken())) {
			log.error(ERROR.PARAMS_NULL.getLogText("deviceToken"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"设备ID信息为空");			
		}
		if (CommonUtil.isEmpty(form.getDeviceType())) {
			log.error(ERROR.PARAMS_NULL.getLogText("DeviceType"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"设备类型信息为空");			
		}
//		20141224修改，取消注销时设备有效位修改、以及绑定表有校位修改 开始
//		appApi401ServiceImpl.appapi40109(form);
//		try{
//			appApi110Service.appApi11005Select(form);
//		}catch(Exception e){
//			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"用户注销处理统一用户信息异常");	
//		}
//		20141224修改，取消注销时设备有效位修改、以及绑定表有校位修改 结束
		ApiUserContext.removeInstance();
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";
	}
	
	/**
	 * 登录用户头像上传
	 * @param modelMap
	 * @param importFile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40107.json")
	public String appapi40107( ModelMap modelMap, @RequestParam MultipartFile importFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
		//String filepath="/edoc/app/info_upload_img/"+System.currentTimeMillis()+"_"+Thread.currentThread().getId()+".jpg";
		String centerid = request.getParameter("centerId");
		String userid = request.getParameter("userId");
		String filePath = CommonUtil.getFileFullPath("loginuser_photo_upload_img", centerid, true) + File.separator;
		System.out.println("appapi40107.json----filePath===="+filePath);
		System.out.println("appapi40107.json----userid===="+userid);
		
		String dirName = "photo";
		String saveUrl = "";
		// 创建文件夹
		filePath += dirName + File.separator;
		saveUrl += dirName + File.separator;
		File saveDirFile = new File(filePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());*/
		AES aes = new AES();
		userid = aes.decrypt(userid);
		filePath += userid + File.separator;
		saveUrl += userid + File.separator;
		System.out.println("appapi40107.json----filePath111111===="+filePath);
		System.out.println("appapi40107.json----saveUrl111111===="+saveUrl);
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}else{
			String[] filelist = dirFile.list();
			for(int i = 0; i < filelist.length; i++) {
				try{
					new File(filePath+filelist[i]).delete();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		String newFileName = System.currentTimeMillis()+"_"+Thread.currentThread().getId()+".jpg";
		filePath = filePath + newFileName;
		System.out.println("appapi40107.json----filePath2===="+filePath);
		FileOutputStream fs = new FileOutputStream(filePath);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		InputStream stream = importFile.getInputStream();
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close(); 
		
		// http://www.12329app.com/YBMAP/downloadimg.file?filePathParam=info_manage_upload_img&fileName=00031900/image/20150203/20150203144552_784.jpg&isFullUrl=true
		String imgUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
				"loginuser_photo_upload_img", centerid+File.separator+ saveUrl + newFileName, true);
		System.out.println("appapi40107.json----imgUrl===="+imgUrl);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("imgurl", imgUrl);
		return "";
	} 

	/**
	 * 手机APP登录用户头像更换
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40108.{ext}")
	public String appapi40108(AppApi40108Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP信息修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		//ApiUserContext.getInstance();				
		System.out.println("appapi40108.json----userid===="+form.getUserId());
		List<Mi103> list = appApi401ServiceImpl.appapi40112(form.getUserId(), form.getCenterId());
		if (list.isEmpty()||list.size()==0) {
			log.error(ERROR.SYS.getLogText("用户不存在或用户不可用"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_NO_USER.getValue(),"用户不存在或用户不可用");			
		}
		appApi401ServiceImpl.appapi40113(form);

		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	
	/**
	 * 遵义登录处理接口
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40109.{ext}")
	public String appapi40109(AppApi40102Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP用户登录");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(!"00085200".equals(form.getCenterId())){
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"验证码验证失败");
		}
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}
//		if (CommonUtil.isEmpty(form.getDevtoken())) {
//			log.error(ERROR.NO_DATA.getLogText("devtoken"));
//			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"设备token未上传");			
//		}
		if ( CommonUtil.isEmpty(form.getPassword())) {
			log.error(ERROR.NO_DATA.getLogText("password"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"密码不正确");			
		}
		
		if(!CommonUtil.isEmpty(form.getCheckid())){
			List<Mi119> list = appApi401ServiceImpl.appapi40108(form);
			if(list.isEmpty()||list.size()==0){
				log.error(ERROR.NO_DATA.getLogText("deviceToken"));
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"匿名用户验证信息错误");
			}
			Mi119 mi119 = list.get(0);
			if(form.getCheckid().toLowerCase().equals(mi119.getCheckid())){
				String checkDate = mi119.getCheckdate();
				SimpleDateFormat formatter = new SimpleDateFormat(
						Constants.DATE_TIME_FORMAT);
				Date date = new Date();
				if (checkDate.compareTo(formatter.format(date)) < 0) {
					log.error(ERROR.SYS.getLogText("验证码超时"));
					throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"验证码超时");				
				}				
			}else{
				log.error(ERROR.SYS.getLogText("验证码验证失败"));
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"验证码验证失败");			
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(
				Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		
		if("00085200".equals(form.getCenterId())){
			form.setBodyCardNumber(form.getUserId());
			form.setIdcardNumber(form.getUserId());
//			form.setUserId("ZY"+form.getUserId());
		}
		List<Mi103> list = appApi401ServiceImpl.appapi40114(form);			
		
		
		String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());
		String bindkey = "";
		
		if(!list.isEmpty()||list.size()!=0){
			if(!form.getCenterId().equals(list.get(0).getCenterid())){
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_NO_USER.getValue(),"用户名异常");
			}
		}
		
		if(!list.isEmpty()||list.size()!=0){
			String date1 = list.get(0).getWaitdate();		
			if (list.get(0).getWaitnum() > 5) {
				if (date1.compareTo(formatter.format(date)) > 0) {
					java.util.Date dateNo1 = formatter.parse(date1);
					java.util.Date dateNo2 = formatter.parse(formatter
							.format(date));
					long l = dateNo1.getTime() - dateNo2.getTime();
					long day = l / (24 * 60 * 60 * 1000);
					long hour = (l / (60 * 60 * 1000));
					long min = ((l / (60 * 1000)));
					long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60
							* 60 - min * 60);
					System.out.println("" + day + "天" + hour + "小时" + min
							+ "分" + s + "秒");
					modelMap.clear();
//					modelMap.put("recode", WEB_ALERT.LOGIN_NO_USER);
//					modelMap.put("msg", "请稍后登录:" + min + "分" + s + "秒");
					String msg = "";
					if(day>0){
						msg = "错误提交次数大于6,请" + day + "天" + hour +"小时" + min + "分" + s + "秒后重试";
					}else if(hour>0){
						msg = "错误提交次数大于6,请" + hour + "小时" + min + "分" + s + "秒后重试";
					}else if(min>0){
						msg = "错误提交次数大于6,请" + min + "分" + s + "秒后重试";
					}else{
						msg = "错误提交次数大于6,请" + s + "秒后重试";
					}
					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), msg);					
				}
			}
			
			if("00".equals(bindkeytype)){
				bindkey = form.getSurplusAccount();
			}else if("01".equals(bindkeytype)){			
				bindkey = form.getBodyCardNumber();
			}else if("02".equals(bindkeytype)){
				bindkey = form.getCardnoNumber();
			}else if("03".equals(bindkeytype)){
				
			}else if("04".equals(bindkeytype)){
				
			}
		}
		request.setAttribute("centerId",form.getCenterId());
		String req = msgSendApi001Service.send(request, response);

		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(req, HashMap.class);
		System.out.println("YBMAPZH 接收YFMAP返回信息: "+req);
		if("000000".equals((String)hasMap.get("recode"))){
			if (list.isEmpty()||list.size()==0){//103,110表无用户新加
				form.setSurplusAccount(aes.decrypt((String)hasMap.get("accnum")));
				form.setFullName(aes.decrypt((String)hasMap.get("accname")));
				form.setMobileNumber((String)hasMap.get("phone"));
				appApi401ServiceImpl.appapi40102(form);
			}else{
				boolean is40103 = appApi401ServiceImpl.appapi40103(form);
//				if (!is40103) {
//					appApi401ServiceImpl.appapi40106(form);
//					log.error(ERROR.SYS.getLogText("用户登录失败"));
//					throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_NO_USER.getValue(),"账户或密码错误");
//				}
			}
			
//			ApiUserContext.saveToSession();
			ApiUserContext.saveToSession(null,aes.encrypt(bindkey.getBytes("UTF-8")));
			ApiUserContext.getInstance();
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(req.getBytes(encoding));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
			return "";
		}else{
//			HashMap map = new HashMap();
//			map.put("recode", (String)hasMap.get("recode"));
//			map.put("msg", (String)hasMap.get("msg"));
//			ObjectMapper mapper = new  ObjectMapper();
//			JSONObject resJsonObjTmp = mapper.convertValue(map, JSONObject.class);
//			String resMsg = resJsonObjTmp.toString();
			if(!"991111".equals((String)hasMap.get((String)hasMap.get("recode")))){//遵义修改密码不记录次数
				appApi401ServiceImpl.appapi40106(form);
			}
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(req.getBytes(encoding));
			return "";
		}
	}
	
	/**
	 * 申请手机短信码
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi40112.{ext}")
	public String appapi40112(AppApi40102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机短信码获取查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());		
		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1 = (HttpServletRequest) request;
			HashMap m = new HashMap(request.getParameterMap());
			//m.put("selectValue", new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8"));
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 手机短信码验证
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi40113.{ext}")
	public String appapi40113(AppApi40102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机短信码获取查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());		
		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1 = (HttpServletRequest) request;
			HashMap m = new HashMap(request.getParameterMap());
			//m.put("selectValue", new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8"));
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 修改核心密码
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi40114.{ext}")
	public String appapi40114(AppApi40102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机短信码获取查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());		
		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1 = (HttpServletRequest) request;
			HashMap m = new HashMap(request.getParameterMap());
			//m.put("selectValue", new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8"));
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 实名资格校验
	 * （校验用户是否满足做实名身份认证，满足会直接发送短信验证码）(保山)
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi40115.{ext}")
	public String appapi40115(AppApi40102Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("实名资格校验");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());		
		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1 = (HttpServletRequest) request;
			HashMap m = new HashMap(request.getParameterMap());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * （实名）身份认证
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi40116.{ext}")
	public String appapi40116(AppApi40102Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("（实名）身份认证");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
		ApiUserContext.getInstance();		
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		String useridtmp = form.getUserId();
		System.out.println("useridtmp=="+useridtmp);
		
		String accnum = "";
		String certinum = "";
		String cardno = "";
		AES aes = new AES();
		if(!"20".equals(form.getChannel())){
			HashMap m=new HashMap(request.getParameterMap());
			String sfz = "";
			if(!CommonUtil.isEmpty(m.get("userId"))){
				String usid = (String)request.getParameter("userId");
				sfz = aes.decrypt(usid);
				form.setUserId(sfz);
			}
			Mi103 mi103 = appApi103ServiceImpl.appApi10301Select(sfz);
			accnum = mi103.getAccnum();
			certinum = mi103.getCertinum();
			cardno = mi103.getCardno();
		}else{
			if(!CommonUtil.isEmpty(form.getSurplusAccount())){
				accnum = aes.decrypt(form.getSurplusAccount());
			}
			if(!CommonUtil.isEmpty(form.getIdcardNumber())){
				certinum = aes.decrypt(form.getIdcardNumber());
			}
			if(!CommonUtil.isEmpty(form.getCardno())){
				cardno = aes.decrypt(form.getCardno());
			}
			HashMap m=new HashMap(request.getParameterMap());
			String sfz = "";
			if(!CommonUtil.isEmpty(m.get("userId"))){
				String usid = (String)request.getParameter("userId");
				sfz = aes.decrypt(usid);
				form.setUserId(sfz);
			}
		}
		form.setSurplusAccount(accnum);
		form.setIdcardNumber(certinum);
		form.setCardno(cardno);
		form.setBankaccnum(aes.decrypt(form.getBankaccnum()));
		List<Mi110>  mi110List = appApi110Service.appApi11006Select(form);
		
		String resStr = "";
		if(CommonUtil.isEmpty(mi110List)){
			log.error(ERROR.BINDFLAG_ERROR.getLogText(""));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"该账户未绑定，请绑定后再操作！");
		}else{
			if(!"20".equals(request.getParameter("channel"))){
				HttpServletRequest request1 = (HttpServletRequest) request;
				HashMap m = new HashMap(request.getParameterMap());
				
				m.put("idcardNumber", aes.encrypt(((CommonUtil.isEmpty(certinum)?"":certinum).getBytes("UTF-8"))));
				m.put("cardnoNumber", aes.encrypt(((CommonUtil.isEmpty(cardno)?"":cardno).getBytes("UTF-8"))));
				
				ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
				resStr = msgSendApi001Service.send(wrapRequest, response);
				
			}else{
				resStr = msgSendApi001Service.send(request, response);
			}
			HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(resStr, HashMap.class);
			System.out.println("（实名）身份认证返回结果resStr=="+resStr);
			if("000000".equals((String)hasMap.get("recode"))){
				for(int i = 0; i < mi110List.size(); i++){
					appApi110Service.appApi11007Update(mi110List.get(i).getUnifyuserid(), form);
				}
			}/*else{
				HashMap map = new HashMap();
				map.put("recode", (String)hasMap.get("recode"));
				map.put("msg", (String)hasMap.get("msg"));
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
			}*/
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	/**
	 * 宁波密码
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi40117.{ext}")
	public String appapi40117(AppApi40102Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP密码找回");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}
//		if (CommonUtil.isEmpty(form.getFullName())) {
//			log.error(ERROR.NO_DATA.getLogText("fullName"));
//			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"姓名为空");			
//		}
		if (CommonUtil.isEmpty(form.getPassword())) {
			log.error(ERROR.NO_DATA.getLogText("password"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"密码为空");			
		}
		if (CommonUtil.isEmpty(form.getIdcardNumber())) {
			log.error(ERROR.NO_DATA.getLogText("idcardNumber"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"证件号码为空");			
		}
		if (CommonUtil.isEmpty(form.getUserId())) {
			log.error(ERROR.NO_DATA.getLogText("userId"));
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"用户ID为空");			
		}
		
		HashMap map = new HashMap();
		
		if(!CommonUtil.isEmpty(form.getIscheck())&&"1001".equals(form.getIscheck())){
			request.setAttribute("centerId", form.getCenterId());
			String req = msgSendApi001Service.send(request, response);
			HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(req, HashMap.class);
			System.out.println(req);
			if("000000".equals((String)hasMap.get("recode"))){
				if (appApi401ServiceImpl.appapi40117(form) == 0) {
					log.error(ERROR.SYS.getLogText("更新失败"));
					throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"密码找回失败，请正确填写用户信息");			
				}
				map.put("recode", Constants.WEB_SUCCESS_CODE);
				map.put("msg", Constants.WEB_SUCCESS_MSG);
			}else{
				map.put("recode", (String)hasMap.get("recode"));
				map.put("msg", (String)hasMap.get("msg"));
			}
		}else{
			if (appApi401ServiceImpl.appapi40104(form) == 0) {
				log.error(ERROR.SYS.getLogText("更新失败"));
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"密码找回失败，请正确填写用户信息");			
			}
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
		}

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
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));	
		return "";
	}
}