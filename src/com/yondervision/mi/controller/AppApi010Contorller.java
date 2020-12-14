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
import com.yondervision.mi.dto.Mi110;
import com.yondervision.mi.form.AppApi01001Form;
import com.yondervision.mi.form.AppApi01002Form;
import com.yondervision.mi.form.AppApi01005Form;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.service.AppApi103Service;
import com.yondervision.mi.service.AppApi700Service;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.impl.AppApi110ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.HtmlUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.security.AES;

/**
 * 物业费提取
 *
 */
@Controller
public class AppApi010Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	@Autowired
	private AppApi103Service appApi103ServiceImpl = null;
	@Autowired
	private AppApi110ServiceImpl appApi110Service = null;
	@Autowired
	private AppApi700Service appApi700ServiceImpl = null;
	
	public void setAppApi700ServiceImpl(AppApi700Service appApi700ServiceImpl) {
		this.appApi700ServiceImpl = appApi700ServiceImpl;
	}
	
	/**
	 * 物业费提取相关信息获取（账户信息+结算信息）
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi01001.{ext}")
	public String appApi00101(AppApi01001Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("物业费提取相关信息获取（账户信息+结算信息）");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue(),"用户ID");
		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		
		// 校验一下  是否是  身份认证的状态
		AES aes = new AES();
		List<Mi110> mi110List = new ArrayList<Mi110>();
		AppApi40102Form appapi40102form = new AppApi40102Form();
		appapi40102form.setCenterId(form.getCenterId());
		appapi40102form.setChannel(form.getChannel());
		appapi40102form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		String resStr = "";
		
		HttpServletRequest request1=(HttpServletRequest) request;
		HashMap m=new HashMap(request.getParameterMap());
		String useridTmp = "";
		if(!CommonUtil.isEmpty(m.get("userId"))){
			String usid = (String)request.getParameter("userId");
			useridTmp = aes.decrypt(usid);
		}
		
		if(!"20".equals(request.getParameter("channel"))){

			Mi103 mi103 = appApi103ServiceImpl.appApi10301Select(useridTmp);
			appapi40102form.setIdcardNumber(mi103.getCertinum());
			appapi40102form.setCardno(mi103.getCardno());
			appapi40102form.setUserId(useridTmp);
			mi110List = appApi110Service.appApi11006Select(appapi40102form);
			if(CommonUtil.isEmpty(mi110List)
					|| (!CommonUtil.isEmpty(mi110List) && !CommonUtil.isEmpty(mi110List.get(0).getFreeuse3())
							&& Constants.IS_NOT_VALIDFLAG.equals(mi110List.get(0).getFreeuse3().trim()))){
				throw new TransRuntimeErrorException(ERROR.AUTHFLAG_ERROR.getValue(),"");
			}
			
			m.put("bodyCardNumber", aes.encrypt((CommonUtil.isEmpty(mi103.getCertinum())?"":mi103.getCertinum()).getBytes("UTF-8")));
			m.put("cardnoNumber", aes.encrypt((CommonUtil.isEmpty(mi103.getCardno())?"":mi103.getCardno()).getBytes("UTF-8")));
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			resStr = msgSendApi001Service.send(wrapRequest, response);
		}else{
			String idcardNumber = "";
			String cardNo = "";
			if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
				idcardNumber = aes.decrypt(form.getBodyCardNumber());
			}
			if(!CommonUtil.isEmpty(form.getCardnoNumber())){
				cardNo = aes.decrypt(form.getCardnoNumber());
			}
			appapi40102form.setIdcardNumber(idcardNumber);
			appapi40102form.setCardno(cardNo);
			appapi40102form.setUserId(useridTmp);
			mi110List = appApi110Service.appApi11006Select(appapi40102form);
			if(CommonUtil.isEmpty(mi110List)
					|| (!CommonUtil.isEmpty(mi110List) && !CommonUtil.isEmpty(mi110List.get(0).getFreeuse3())
							&& Constants.IS_NOT_VALIDFLAG.equals(mi110List.get(0).getFreeuse3().trim()))){
				throw new TransRuntimeErrorException(ERROR.AUTHFLAG_ERROR.getValue(),"");
			}
			resStr = msgSendApi001Service.send(request, response);
		}
		
		System.out.println("物业费提取相关信息获取（账户信息+结算信息）返回报文=="+resStr);
		
		String tips = "";
		String tipsclassification = PropertiesReader.getProperty("properties.properties", "tipsclassification"+form.getCenterId());
		String keyword = PropertiesReader.getProperty("properties.properties", "tipskeyword_wyftqbl"+form.getCenterId());
		// 获取tips
		String tipsTmp = appApi700ServiceImpl.appapi70007(form.getCenterId(), tipsclassification, "", keyword, "").getContent();
		System.out.println("物业费提取信息获取温馨提示tipsTmp=="+tipsTmp);
		tips = HtmlUtil.delHTMLTag(tipsTmp);
		System.out.println("物业费提取信息获取温馨提示tips=="+tips);
		
		JSONObject resJsonObj = JSONObject.fromObject(resStr);
		String recode = null, msg = null;
		if (resJsonObj.has("recode")){
			recode = resJsonObj.getString("recode");
			if (resJsonObj.has("msg")){
				msg = resJsonObj.getString("msg");
			}
			if(!"000000".equals(recode)){
				HashMap map = new HashMap();
				map.put("recode", recode);
				map.put("msg", msg);
				if(resJsonObj.has("result")){
					map.put("result", resJsonObj.getJSONObject("result"));
				}
				map.put("tips", tips);
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
		
		HashMap map = new HashMap();
		map.put("recode", Constants.WEB_SUCCESS_CODE);
		map.put("msg", Constants.WEB_SUCCESS_MSG);
		map.put("result", resJsonObj.getJSONObject("result"));
		map.put("tips", tips);
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
	 * 物业费提取
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi01002.{ext}")
	public String appApi00102(AppApi01002Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("物业费提取");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue(),"用户ID");
		}
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		request.setAttribute("centerId", form.getCenterId());
		// 校验一下  是否是  身份认证的状态
		AES aes = new AES();
		List<Mi110> mi110List = new ArrayList<Mi110>();
		AppApi40102Form appapi40102form = new AppApi40102Form();
		appapi40102form.setCenterId(form.getCenterId());
		appapi40102form.setChannel(form.getChannel());
		appapi40102form.setUserId(aes.decrypt(form.getUserId()));
		appapi40102form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		
		if(!"20".equals(request.getParameter("channel"))){
			HttpServletRequest request1=(HttpServletRequest) request;
			HashMap m=new HashMap(request.getParameterMap());
			String sfz = "";
			if(!CommonUtil.isEmpty(m.get("userId"))){
				String usid = (String)request.getParameter("userId");
				sfz = aes.decrypt(usid);
			}
			Mi103 mi103 = appApi103ServiceImpl.appApi10301Select(sfz);
			appapi40102form.setIdcardNumber(mi103.getCertinum());
			appapi40102form.setCardno(mi103.getCardno());
			mi110List = appApi110Service.appApi11006Select(appapi40102form);
			if(CommonUtil.isEmpty(mi110List)
					|| (!CommonUtil.isEmpty(mi110List) && !CommonUtil.isEmpty(mi110List.get(0).getFreeuse3())
							&& Constants.IS_NOT_VALIDFLAG.equals(mi110List.get(0).getFreeuse3().trim()))){
				throw new TransRuntimeErrorException(ERROR.AUTHFLAG_ERROR.getValue(),"");
			}
			if(!CommonUtil.isEmpty(form.getIdcardNumber())){
				m.put("idcardNumber", form.getIdcardNumber());
			}else{
				m.put("idcardNumber", aes.encrypt((CommonUtil.isEmpty(mi103.getCertinum())?"":mi103.getCertinum()).getBytes("UTF-8")));
			}
			if(!CommonUtil.isEmpty(form.getCardnoNumber())){
				m.put("cardnoNumber", form.getCardnoNumber());
			}else{
				m.put("cardnoNumber", aes.encrypt((CommonUtil.isEmpty(mi103.getCardno())?"":mi103.getCardno()).getBytes("UTF-8")));
			}
			ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
			msgSendApi001Service.send(wrapRequest, response);
		}else{
			String idcardNumber = "";
			String cardNo = "";
			if(!CommonUtil.isEmpty(form.getIdcardNumber())){
				idcardNumber = aes.decrypt(form.getIdcardNumber());
			}
			if(!CommonUtil.isEmpty(form.getCardnoNumber())){
				cardNo = aes.decrypt(form.getCardnoNumber());
			}
			appapi40102form.setIdcardNumber(idcardNumber);
			appapi40102form.setCardno(cardNo);
			mi110List = appApi110Service.appApi11006Select(appapi40102form);
			if(CommonUtil.isEmpty(mi110List)
					|| (!CommonUtil.isEmpty(mi110List) && !CommonUtil.isEmpty(mi110List.get(0).getFreeuse3())
							&& Constants.IS_NOT_VALIDFLAG.equals(mi110List.get(0).getFreeuse3().trim()))){
				throw new TransRuntimeErrorException(ERROR.AUTHFLAG_ERROR.getValue(),"");
			}
			msgSendApi001Service.send(request, response);
		}

		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 提取规则检查（校验提取规则(允许提取次数等)，满足会直接发送短信验证码）
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/appapi01005.{ext}")
	public String appapi01005(AppApi01005Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("提取规则检查");
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

	public void setAppApi103ServiceImpl(AppApi103Service appApi103ServiceImpl) {
		this.appApi103ServiceImpl = appApi103ServiceImpl;
	}

	public void setAppApi110Service(AppApi110ServiceImpl appApi110Service) {
		this.appApi110Service = appApi110Service;
	}	
	
}
