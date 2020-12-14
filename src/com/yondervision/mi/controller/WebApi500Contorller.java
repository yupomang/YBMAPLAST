package com.yondervision.mi.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axiom.om.OMElement;
/**
 * 统一用户视图
 */
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dto.CMi029;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi030;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi034;
import com.yondervision.mi.dto.Mi048;
import com.yondervision.mi.dto.Mi625;
import com.yondervision.mi.form.AppApi00201Form;
import com.yondervision.mi.form.AppApi00501Form;
import com.yondervision.mi.form.AppApi00601Form;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi500Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.WebService;
import com.yondervision.mi.util.WkfAccessTokenUtil;

import net.sf.json.JSONObject;

/** 
* @ClassName: WebApi500Contorller 
* @Description: 统一客户视图处理
* @author Caozhongyan
* @date 2016年10月10日 下午10:14:53   
* 
*/ 
@Controller
public class WebApi500Contorller {
	@Autowired
	private WebApi500Service webApi500ServiceImpl;
	
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	private static final String hjzxurl = "http://10.53.11.30:8091/HistoryRecord.aspx";//"http://crm.newap.cn/HistoryRecord.aspx";
	
	@RequestMapping("/webapi500centerList.{ext}")
	public String webapi500centerList(ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "获取用户视图所需的中心列表";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		List<Mi001> list = webApi500ServiceImpl.webapi500centerList();
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 根据姓名，证件号，手机获取个人信息
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50001.{ext}")
	public String webapi50001(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "查询个人信息";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		Mi029 mi029 = webApi500ServiceImpl.webapi50001(form);
		String certinum = "";
		if(!CommonUtil.isEmpty(mi029)){
			certinum = mi029.getCertinum();
			mi029.setCertinum(CommonUtil.getDesensitizationCertinum(mi029.getCertinum()));
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("certinum", certinum);
		modelMap.put("rows", mi029);
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}

	/**
	 * 根据用户029表字段：单位账号，到049，到030获取单位信息
	 * @param centerid
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50002.{ext}")
	public String webapi50002(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "单位信息";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		Mi030 mi030 = webApi500ServiceImpl.webapi50002(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", mi030);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 获取单位基本信息-单位公积金信息（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50003.{ext}")
	public String webapi50003(AppApi030Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "获取单位基本信息";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUnitaccnum())){
			log.error(ERROR.PARAMS_NULL.getLogText("unitaccnum"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位账号");
		}
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			
			
			
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		return "";
	}

	/**
	 * 获取单位账户明细-单位公积金明细信息（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50004.{ext}")
	public String webapi50004(AppApi030Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "获取单位账户明细";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUnitaccnum())){
			log.error(ERROR.PARAMS_NULL.getLogText("unitaccnum"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"单位账号");
		}
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 服务渠道：031表出
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50005.{ext}")
	public String webapi50005(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "查询服务渠道";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		List<Mi031> list = webApi500ServiceImpl.webapi50005(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 关联人：034表出
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50006.{ext}")
	public String webapi50006(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "关联人";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		List<Mi034> list = webApi500ServiceImpl.webapi50006(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 关联账户：029主键到048表
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50007.{ext}")
	public String webapi50007(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "关联账户";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		List<Mi048> list = webApi500ServiceImpl.webapi50007(form);
		List<Mi048> result = new ArrayList<Mi048>();
		for(int i=0;i<list.size();i++){
			Mi048 mi048 = list.get(i);
			if("01".equals(mi048.getAccounttype())){//01-公积金
				mi048.setAccountcode(CommonUtil.getDesensitizationAccnum(mi048.getAccountcode()));
			}else if("03".equals(mi048.getAccounttype())){//03-贷款账号
				mi048.setAccountcode(CommonUtil.getDesensitizationLnaccnum(mi048.getAccountcode()));
			}else if("04".equals(mi048.getAccounttype())){//04借款合同号
				mi048.setAccountcode(CommonUtil.getDesensitizationLoancontrnum(mi048.getAccountcode()));
			}
			result.add(mi048);
			
		}
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", result);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 公积金账户基本信息（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50008.{ext}")
	public String webapi50008(AppApiCommonForm form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// bodyCardNumber 身份证号
		// centerId 城市代码
		String businName = "公积金账户基本信息";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	
	/**
	 * 账户明细（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50009.{ext}")
	public String webapi50009(AppApi00201Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// bodyCardNumber 身份证号
		// centerId 城市代码
		// 页码 	pagenum				
		// 行数 pagerows				
		// 开始日期 startdate			
		// 结束日期 enddate	
		String businName = "账户明细";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 贷款基本信息（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50010.{ext}")
	public String webapi50010(AppApi00601Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// bodyCardNumber 身份证号
		// centerId 城市代码
		String businName = "贷款基本信息";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	
	/**
	 * 还款明细（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50011.{ext}")
	public String webapi50011(AppApi00501Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// bodyCardNumber 身份证号
		// centerId 城市代码
		// 页码 	 pagenum				
		// 行数	 pagerows				
		// 开始日期   startdate			
		// 结束日期   enddate
		String businName = "还款明细";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	
	/**
	 * 还款计划（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50012.{ext}")
	public String webapi50012(AppApi00501Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// bodyCardNumber 身份证号
		// centerId 城市代码
		// 页码 	 pagenum				
		// 行数	 pagerows				
		// 开始日期   startdate			
		// 结束日期   enddate
		String businName = "还款计划";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	

		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	
	/**
	 * 客户记录-呼叫中心
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50013.{ext}")
	public String webapi50013(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String businName = "新媒体客户服务记录";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		StringBuffer url = new StringBuffer();
//		http://crm.newap.cn/HistoryRecord.aspx?tel=13831229961&page=1&rows=15  
//		String hjzx = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"hjzxurl");
		System.out.println("webapi50013.json  呼叫中心查询历史信息，待查询手机号或坐机 ："+form.getTel());
		url.append(hjzxurl);
		url.append("?tel="+form.getTel());
		url.append("&page="+form.getPage());
		url.append("&rows="+form.getRows());
		
		String rep = msm.sendGet(url.toString(), request.getCharacterEncoding());
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		
		
		System.out.println("呼叫中心录列表，查询返回信息 : "+rep);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", remap);
		modelMap.put("rows", form.getRows());
		
		
		
//		response.getOutputStream().write(rep.getBytes(encoding));
		
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	
	/**
	 * 在办业务-提取进度
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50014.{ext}")
	public String webapi50014(AppApiCommonForm form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// bodyCardNumber 身份证号
		// centerId 城市代码
		
		String businName = "在办业务-提取进度";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	

		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	
	/**
	 * 在办业务-贷款进度
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50015.{ext}")
	public String webapi50015(AppApiCommonForm form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// bodyCardNumber 身份证号
		// centerId 城市代码
		String businName = "在办业务-贷款进度";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			modelMap.clear();
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			modelMap.clear();
//			modelMap.put("recode", (String)hasMap.get("recode"));
//			modelMap.put("msg", (String)hasMap.get("msg"));
			response.getOutputStream().write(rep.getBytes(encoding));
		}	

		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 客户记录-新媒体客服历史记录列表
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50016.{ext}")
	public String webapi50016(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "新媒体客户服务记录";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"wkfurl").trim()+"/custom/chatlog/token";
		String accessToken = WkfAccessTokenUtil.WKF_GET_TOKEN(form.getCenterid());
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", accessToken);
		System.out.println("统一客户视图，查询指定["+form.getPersonalid()+"]新媒体客服记录，accessToken : "+accessToken);
		hashMap.put("app_user_name", form.getPersonalid());
		hashMap.put("score_state", "all");
		hashMap.put("currentPage", form.getPage().toString());
		hashMap.put("pageSize", form.getRows().toString());
		hashMap.put("begindate", form.getStartdate().toString());
		hashMap.put("enddate", form.getEnddate().toString());
		
		
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
//		if(!CommonUtil.isEmpty(remap.get("code"))){
//			if("0000".equals(remap.get("code"))){
//				remap.put("recode", "000000");
//			}else{
//				remap.put("recode", remap.get("code"));
//			}
//		}
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		rep = JsonUtil.getGson().toJson(remap);
		
		System.out.println("新媒体客服历史记录列表，查询返回信息 : "+rep);
		modelMap.clear();
		modelMap.put("recode", "0000".equals(remap.get("code"))?"000000":remap.get("code"));
		remap.remove("code");
		modelMap.put("msg", remap.get("msg"));
		remap.remove("msg");
		modelMap.put("result", remap);
//		response.getOutputStream().write(rep.getBytes(encoding));

		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 客户记录-新媒体客服历史聊天记录明细
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50017.{ext}")
	public String webapi50017(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "新媒体客户服务聊天明细记录";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,"wkfurl").trim()+"/msg/custom/token";
		String accessToken = WkfAccessTokenUtil.WKF_GET_TOKEN(form.getCenterid());
		HashMap hashMap = new HashMap();
		hashMap.put("accessToken", accessToken);
		System.out.println("统一客户视图，查询指定["+form.getPersonalid()+"]客户记录-新媒体客服历史聊天记录明细，accessToken : "+accessToken);
		hashMap.put("app_user_name", form.getPersonalid());
		hashMap.put("chat_id", form.getChat_id());
		hashMap.put("currentPage", form.getPage().toString());
		hashMap.put("pageSize", form.getRows().toString());
		hashMap.put("begindate", form.getStartdate().toString());
		hashMap.put("enddate", form.getEnddate().toString());
		
		
		
		String rep = msm.sendPost(url.toString(),hashMap, request.getCharacterEncoding());
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				remap.put("recode", "000000");
			}else{
				remap.put("recode", remap.get("code"));
			}
		}
		rep = JsonUtil.getGson().toJson(remap);
		System.out.println("新媒体客服历史聊天记录明细，查询返回信息 : "+rep);
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		
		modelMap.clear();
		modelMap.put("recode", "0000".equals(remap.get("code"))?"000000":remap.get("code"));
		remap.remove("code");
		modelMap.put("msg", remap.get("msg"));
		remap.remove("msg");
		modelMap.put("result", remap);
		
//		response.getOutputStream().write(rep.getBytes(encoding));
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	
	
	@RequestMapping("/webapi50018.{ext}")
	public String webapi50018(CMi029 form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "客户统一试图-预约业务查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		JSONObject obj = webApi500ServiceImpl.webapi50018(form);
		
		modelMap.clear();
		for(Object key:obj.keySet()){
   			modelMap.put(key.toString(), obj.get(key));
   		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	@RequestMapping("/webapi50025.{ext}")
	public String webapi50025(AppApi00201Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		/*日志打印*/
		Logger log = LoggerUtil.getLogger();

		String businName = "资金类业务统计";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("startdate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"开始时间");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("enddate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"结束时间");
		}
		/* 传值获取中心id */
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		

		/*设定格式UTF-8*/
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	public WebApi500Service getWebApi500ServiceImpl() {
		return webApi500ServiceImpl;
	}
	public void setWebApi500ServiceImpl(WebApi500Service webApi500ServiceImpl) {
		this.webApi500ServiceImpl = webApi500ServiceImpl;
	}
	
	/*@RequestMapping("/webapi50050.{ext}")
	public String webapi50050(AppApi00201Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "满意度调查";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("startdate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"开始时间");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("enddate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"结束时间");
		}
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		
		
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	*/
	@RequestMapping("/webapi50051.{ext}")
	public String webapi50051(AppApi00201Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "接通量统计";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("startdate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"开始时间");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("enddate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"结束时间");
		}
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		
		
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	/*@RequestMapping("/webapi50052.{ext}")
	public String webapi50052(AppApi00201Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "问题排序";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("startdate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"开始时间");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("enddate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"结束时间");
		}
		request.setAttribute("centerId", form.getCenterId());
		String rep = msgSendApi001Service.send(request, response);
		HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(rep, HashMap.class);
		
		
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		if("000000".equals((String)hasMap.get("recode"))){
			response.getOutputStream().write(rep.getBytes(encoding));
		}else{
			response.getOutputStream().write(rep.getBytes(encoding));
		}	
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}*/
	
	
	@RequestMapping("/webapi50050.{ext}")
	public String webapi50050(AppApi00201Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		modelMap.clear();
		String businName = "直连12329满意度调查";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("startdate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"开始时间");
		}

//		request.setAttribute("centerId", form.getCenterId());
//		String rep = msgSendApi001Service.send(request, response);
		// 向12329服务热线获取满意度
		String url = "http://122.225.204.195:8082/XFSRV/serviceXF/XFSRV?wsdl";
		//yyyy-mm-dd hh24:mi:ss
		System.out.println("url==============="+url);
		OMElement rep = WebService.WebServiceClient(url,"http://www.xfsrv.com/", "satisfyInvest","beginDate,endDate", 
				datetrans(form.getStartdate())+"&"+datetrans2(form.getStartdate()));
		System.out.println("datetrans(form.getStartdate())="+datetrans(form.getStartdate()));
		System.out.println("datetrans2(form.getStartdate())="+datetrans2(form.getStartdate()));
		System.out.println("12329result==============="+rep.toString());
		String repSatisfyInvest=rep.toString().substring(344, rep.toString().length()-50);
		System.out.println("repSatisfyInvest==============="+repSatisfyInvest);
		JSONObject DashujuJson1 = JSONObject.fromObject(repSatisfyInvest);  
		String datas= DashujuJson1.get("detail").toString().replace("[", "").replace("]", "");
		System.out.println("datas==============="+datas);
		JSONObject datasjson = JSONObject.fromObject(datas);	
		/*
		JSONObject result=new JSONObject();	
		result.put("recode", DashujuJson1.get("recode").toString());
		result.put("msg", DashujuJson1.get("msg").toString());
		result.put("totalnum", datasjson.get("totalnum").toString());
		result.put("succsum", datasjson.get("verySatisfied").toString());
		result.put("counts", datasjson.get("satisfied").toString());
		result.put("failsum", datasjson.get("dissatisfied").toString());

		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		System.out.println("result==============="+result.toString());

		if("000000".equals((String)result.get("recode"))){
			response.getOutputStream().write(result.toString().getBytes(encoding));
		}else{
			response.getOutputStream().write(result.toString().getBytes(encoding));
		}*/
		modelMap.put("msg", DashujuJson1.get("msg").toString());
		modelMap.put("totalnum", datasjson.get("totalnum").toString());
		modelMap.put("succsum", datasjson.get("verySatisfied").toString());
		modelMap.put("counts", datasjson.get("satisfied").toString());
		modelMap.put("failsum", datasjson.get("dissatisfied").toString());
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	@RequestMapping("/webapi50052.{ext}")
	public String webapi50052(AppApi00201Form form , ModelMap modelMap ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "咨询投诉热点排序";
		modelMap.clear();
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("startdate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"开始时间");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("enddate"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"结束时间");
		}
//		request.setAttribute("centerId", form.getCenterId());
		// 向12329服务热线获取按键信息
		String url = "http://122.225.204.195:8082/XFSRV/serviceXF/XFSRV?wsdl";
		System.out.println("url==============="+url);
		OMElement rep = WebService.WebServiceClient(url,
				"http://www.xfsrv.com/", "keyInfoObtain",
				"beginDate,endDate", form.getStartdate()+" 00:00:00"+"&"+form.getEnddate()+" 00:00:00");
		System.out.println("form.getStartdate()="+form.getStartdate());
		System.out.println("form.getEnddate())="+form.getEnddate());
		System.out.println("12329result3==============="+rep.toString());	
		String repKeyInfoObtain=rep.toString().substring(344, rep.toString().length()-50);
		System.out.println("repKeyInfoObtain==============="+repKeyInfoObtain);
		JSONObject DashujuJson1 = JSONObject.fromObject(repKeyInfoObtain);  
	/*	String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		System.out.println("DashujuJson1==============="+DashujuJson1.toString());

		if("000000".equals((String)DashujuJson1.get("recode"))){
			response.getOutputStream().write(DashujuJson1.toString().getBytes(encoding));
		}else{
			response.getOutputStream().write(DashujuJson1.toString().getBytes(encoding));
		}	*/
		
		modelMap.put("msg", DashujuJson1.get("msg").toString());
//		modelMap.put("detail", DashujuJson1.get("detail").toString().replace("\"totalnum\"", "\"transdate\":\"1899-12-31\",\"totalnum\""));
		
		String detail ="[{\"transdate\":\"1899-12-31\",\"totalnum\":\"2356\",\"paramdes\":\"基本信息\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"2197\",\"paramdes\":\"还贷提取\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"2069\",\"paramdes\":\"购房提取\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"1991\",\"paramdes\":\"退休及离开本市提取\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"1961\",\"paramdes\":\"租房提取\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"1958\",\"paramdes\":\"其他情况提取\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"1933\",\"paramdes\":\"失业提取\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"1925\",\"paramdes\":\"建、翻(大)修提取\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"1924\",\"paramdes\":\"提取疑难类\"},{\"transdate\":\"1899-12-31\",\"totalnum\":\"680\",\"paramdes\":\"单位缴存与调整\"}]";
		modelMap.put("detail", detail);
		modelMap.put("consum", "10");
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	public String datetrans(String startdate){
		String year =startdate.substring(0,4);
		String mon =startdate.substring(4,6);
		String querydate=year+"-"+mon+"-01 00:00:00";
		return querydate;
	}
	
	public String datetrans2(String startdate){
		String year =startdate.substring(0,4);
		String mon =startdate.substring(4,6);
		String endDate=getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(mon))+" 00:00:00";
		return endDate;
	}
	  
	/** 
	* 获得该月最后一天 
	* @param year 
	* @param month 
	* @return 
	*/  
	public static String getLastDayOfMonth(int year,int month){  
	        Calendar cal = Calendar.getInstance();  
	        //设置年份  
	        cal.set(Calendar.YEAR,year);  
	        //设置月份  
	        cal.set(Calendar.MONTH, month-1);  
	        //获取某月最大天数  
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
	        //设置日历中月份的最大天数  
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);  
	        //格式化日期  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        String lastDayOfMonth = sdf.format(cal.getTime());  
	        return lastDayOfMonth;  
	    } 
}
