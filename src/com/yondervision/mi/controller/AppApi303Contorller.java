/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.controller
 * 文件名：     AppApi303Contorller.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.ParameterRequestWrapper;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.form.AppApi30301Form;
import com.yondervision.mi.form.AppApi30302Form;
import com.yondervision.mi.form.AppApi30303Form;
import com.yondervision.mi.form.AppApi30304Form;
import com.yondervision.mi.form.AppApi30305Form;
import com.yondervision.mi.form.AppApi30306Form;
import com.yondervision.mi.form.AppApi30309Form;
import com.yondervision.mi.form.AppApi30315Form;
import com.yondervision.mi.form.AppApi62601Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi30301Result;
import com.yondervision.mi.result.AppApi30302Result;
import com.yondervision.mi.result.AppApi30303Result;
import com.yondervision.mi.result.AppApi30305Result;
import com.yondervision.mi.result.AppApi30307Result;
import com.yondervision.mi.result.AppApi30308Result;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.AppApi303Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.security.AES;

import net.sf.json.JSONObject;

/**
 * 业务预约
 * 
 * @author LinXiaolong
 * 
 */
@Controller
public class AppApi303Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	@Autowired
	private AppApi103Service appApi103ServiceImpl = null;
	@Autowired
	private AppApi303Service appApi303Service;

	/**
	 * @return the appApi303Service
	 */
	public AppApi303Service getAppApi303Service() {
		return appApi303Service;
	}

	/**
	 * @param appApi303Service
	 *            the appApi303Service to set
	 */
	public void setAppApi303Service(AppApi303Service appApi303Service) {
		this.appApi303Service = appApi303Service;
	}

	/**
	 * 预约网点查询
	 * 
	 * @param form
	 *            centerId、bussinesstype
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30301.json")
	public String appApi30301(AppApi30301Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "预约网点查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));

			// 业务处理
			List<AppApi30301Result> result = this.getAppApi303Service()
					.appApi30301(form);

			modelMap.put("result", result);

			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "";
	}

	/**
	 * 预约日期人数查询
	 * 
	 * @param form
	 *            centerId、orgid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30302.json")
	public String appApi30302(AppApi30302Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "预约日期人数查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));

			// 业务处理
			List<AppApi30302Result> result = this.getAppApi303Service()
					.appApi30302(form);

			modelMap.put("result", result);

			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "";
	}

	/**
	 * 预约时间人数查询
	 * 
	 * @param form
	 *            centerId、orgid、orgiddate
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30303.json")
	public String appApi30303(AppApi30303Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "预约时间人数查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));

			// 业务处理
			List<AppApi30303Result> result = this.getAppApi303Service()
					.appApi30303(form);

			modelMap.put("result", result);

			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "";
	}

	/**
	 * 预约确定
	 * 
	 * @param form
	 *            centerId、userId、surplusAccount、orgid、orgiddate、orgidtime、bussinesstype
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30304.json")
	public String appApi30304(AppApi30304Form form, ModelMap modelMap,HttpServletRequest request) {
		Logger log = LoggerUtil.getLogger();
		String businName = "预约确定";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			ApiUserContext.getInstance();
			Object pid = request.getAttribute("MI040Pid");
			if(pid==null||"".equals(pid.toString())){
				throw new TransRuntimeErrorException(APP_ALERT.APP_YY.getValue(),"渠道应用不可以为空");
			}
			Object pidname = request.getAttribute("MI040PidName");
			form.setPid(pid.toString());
			form.setPidname(pidname.toString());
			// 业务处理
			String str = this.getAppApi303Service().appApi30304(form);
			String[] ary = str.split(",");
			
			modelMap.clear();
			modelMap.put("apponum", ary[0]);
			modelMap.put("content", ary[1]);//二维码内容
			modelMap.put("filePath", ary[2]);//二维码路径
			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "";
	}

	/**
	 * 我的预约
	 * 
	 * @param form
	 *            centerId、userId、surplusAccount
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30305.json")
	public String appApi30305(AppApi30305Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "我的预约";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			ApiUserContext.getInstance();
			// 业务处理
			List<AppApi30305Result> result = this.getAppApi303Service()
					.appApi30305(form);

			modelMap.put("result", result);

			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "";
	}

	/**
	 * 预约撤销
	 * 
	 * @param form
	 *            centerId、appointid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30306.json")
	public String appApi30306(AppApi30306Form form, ModelMap modelMap, HttpServletRequest request) {
		Logger log = LoggerUtil.getLogger();
		String businName = "预约撤销";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			ApiUserContext.getInstance();
			Object pid = request.getAttribute("MI040Pid");
			if(pid==null||"".equals(pid.toString())){
				throw new TransRuntimeErrorException(APP_ALERT.APP_YY.getValue(),"渠道应用不可以为空");
			}
			Object pidname = request.getAttribute("MI040PidName");
			form.setPid(pid.toString());
			form.setPidname(pidname.toString());
			// 业务处理
			this.getAppApi303Service().appApi30306(form);

			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "";
	}

	/**
	 * 注意事项
	 * 
	 * @param form
	 *            centerId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30307.json")
	public String appApi30307(AppApi62601Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "预约注意事项";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));

			// 业务处理
			List<AppApi30307Result> result = this.getAppApi303Service().appApi30307(form);
			modelMap.put("result", result);

			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "";
	}
	/**
	 * 预约业务类型查询
	 * 
	 * @param form
	 *            centerId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30308.json")
	public String appApi30308(AppApiCommonForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "预约业务类型查询";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));

			// 业务处理
			List<AppApi30308Result> result = this.getAppApi303Service().appApi30308(form);

			modelMap.put("result", result);

			modelMap.put("recode", Constants.APP_SUCCESS_CODE);
			modelMap.put("msg", Constants.APP_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}

		return "";
	}
	
	/**
	 * 查询核心系统网点可预约信息(网点及人数)
	 * 
	 * @param form
	 *            centerId、orgid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30309.json")
	public String appApi30309(AppApi30309Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "查询核心预约网点及人数信息";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));				
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1 = (HttpServletRequest) request;
			HashMap m = new HashMap(request.getParameterMap());
//			m.put("selectValue", new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8"));
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 查询核心网点预约时间段、预约人数查询
	 * 
	 * @param form
	 *            centerId、orgid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30310.json")
	public String appApi30310(AppApi30309Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "预约网点预约时间人数查询";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1 = (HttpServletRequest) request;
			HashMap m = new HashMap(request.getParameterMap());
//			m.put("selectValue", new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8"));
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 网点预约添加
	 * 
	 * @param form
	 *            centerId、orgid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30311.json")
	public String appApi30311(AppApi30309Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "预约网点添加";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
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
			m.put("bodyCardNumber", mi103.getCertinum()==null?"":mi103.getCertinum());
			m.put("cardnoNumber", mi103.getCardno()==null?"":mi103.getCardno());
			m.put("value1", mi103.getAccname()==null?"":mi103.getAccname());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 网点预约查询
	 * 
	 * @param form
	 *            centerId、orgid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30312.json")
	public String appApi30312(AppApi30309Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "预约网点添加";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
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
			m.put("cardnoNumber", mi103.getCardno()==null?"":mi103.getCardno());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 网点预约撒消
	 * 
	 * @param form
	 *            centerId、orgid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/appapi30313.json")
	public String appApi30313(AppApi30309Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "预约网点撒消";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
				
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
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
			m.put("cardnoNumber", mi103.getCardno()==null?"":mi103.getCardno());
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			msgSendApi001Service.send(request, response);
		}
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}

	@RequestMapping("/appapi30314.json")
	public String appApi30314(AppApi30301Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "查询是否已经预约";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
				
		JSONObject result = appApi303Service.appApi30314(form);
		modelMap.clear();
		modelMap.put("recode", result.get("recode"));
		modelMap.put("msg", result.get("msg"));
		modelMap.put("result", result.get("result")==null?"[]":result.get("result"));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	@RequestMapping("/appapi30315.json")
	public String appApi30315(AppApi30309Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "查询是否为节假日";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		List<Mi627> list = appApi303Service.appApi30315(form);
		modelMap.clear();
		if(CommonUtil.isEmpty(list)){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "未查询到相关节假日信息");
		}else{
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", "成功");
			modelMap.put("result", list);
		}
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	@RequestMapping("/appapi30316.json")
	public String appApi30316(AppApi30301Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "预约办结确认";
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
				
		JSONObject result = appApi303Service.appApi30316(form);
		modelMap.clear();
		modelMap.put("recode", result.get("recode"));
		modelMap.put("msg", result.get("msg"));
		modelMap.put("result", result.get("result")==null?"[]":result.get("result"));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	public AppApi103Service getAppApi103ServiceImpl() {
		return appApi103ServiceImpl;
	}

	public void setAppApi103ServiceImpl(AppApi103Service appApi103ServiceImpl) {
		this.appApi103ServiceImpl = appApi103ServiceImpl;
	}

	public MsgSendApi001Service getMsgSendApi001Service() {
		return msgSendApi001Service;
	}
}
