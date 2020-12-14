/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dto.CMi401;
import com.yondervision.mi.dto.CMi402;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi401;
import com.yondervision.mi.form.ApiDataCommonForm;
import com.yondervision.mi.form.AppApi50001Form;
import com.yondervision.mi.form.AppApi50004Form;
import com.yondervision.mi.form.SendApiCommonForm;
import com.yondervision.mi.form.SendBatchApiCommonForm;
import com.yondervision.mi.form.SendMBApiCommonForm;
import com.yondervision.mi.form.SendSelectApiCommonForm;
import com.yondervision.mi.result.YfmapApi3003Result;
import com.yondervision.mi.service.WebApi029Service;
import com.yondervision.mi.service.WebApi302Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.SpringContextUtil;
import com.yondervision.mi.util.security.AES;

/**
 * @author CaoZhongYan
 */
@Controller
public class SendApiContorller {
	@Autowired
	private WebApi302Service webApi302Service;
	public WebApi302Service getWebApi302Service() {
		return webApi302Service;
	}
	public void setWebApi302Service(WebApi302Service webApi302Service) {
		this.webApi302Service = webApi302Service;
	}
	
	@Autowired
	private WebApi029Service webApi029ServiceImpl = null;
	
	public WebApi029Service getWebApi029ServiceImpl() {
		return webApi029ServiceImpl;
	}
	
	public void setWebApi029ServiceImpl(WebApi029Service webApi029ServiceImpl) {
		this.webApi029ServiceImpl = webApi029ServiceImpl;
	}
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	/**
	 * @deprecated 渠道调用定制（报盘）推送接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi30290.{ext}")
	public String appapi30290(CMi401 form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
			
		log.info(LOG.START_BUSIN.getLogText("渠道调用定制（报盘）推送接口开始"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerid"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		AES aes = new AES(form.getCenterid() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		
		
//		if(CommonUtil.isEmpty(form.getCertinum())){
//			log.error(ERROR.PARAMS_NULL.getLogText("certinum"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"证件号码");
//		}
		if(CommonUtil.isEmpty(form.getTel())){
			log.error(ERROR.PARAMS_NULL.getLogText("tel"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"电话号码");
		}else{
			form.setTel(aes.decrypt(form.getTel()));
		}
		if(CommonUtil.isEmpty(form.getTitle())){
//			log.error(ERROR.PARAMS_NULL.getLogText("title"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息标题");
			form.setTitle("渠道发送定制短信息");
		}
		if(CommonUtil.isEmpty(form.getDetail())){
//			log.error(ERROR.PARAMS_NULL.getLogText("detail"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息描述");
			form.setDetail("渠道发送定制短信息");
		}
//		if(CommonUtil.isEmpty(form.getTheme())){
//			log.error(ERROR.PARAMS_NULL.getLogText("theme"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息主题");
//		}
		if(CommonUtil.isEmpty(form.getTsmsg())){
			log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息内容");
		}
		request.setAttribute("centerId", form.getCenterid());
		
		//	add by lwj 2017-5-28 保山中心临时处理方法， 直接调用YF封装接口进行发送， 不入库。
		if ("00087500".equals(form.getCenterid()))	{
			try {
				HashMap result = this.sendSmsCheckAndMessage00087500(form, request, response);
				if (null == result) {
					modelMap.clear();
					modelMap.put("recode", "000001");
					modelMap.put("remsg", "处理失败");
					log.info(LOG.END_BUSIN.getLogText("核心推送个人短信息结束"));
					return "";
				} else {
					modelMap.clear();
					modelMap.put("recode", "000000");
					modelMap.put("remsg", "处理成功");
					log.info(LOG.END_BUSIN.getLogText("渠道调用定制（报盘）推送接口结束"));
					return "";
				}
			} catch(Exception e){
				modelMap.clear();
				modelMap.put("recode", "000001");
				modelMap.put("remsg", e.getMessage());
				log.info(LOG.END_BUSIN.getLogText("核心推送个人短信息结束"));
				return "";
			}
			
		}//end add
		else {
		
		//	以下为增加保山临时处理办法前代码逻辑
		String mi100key = "";
		try{
			List<Mi040> list040 = webApi302Service.getChannelPid(form.getCenterid(), "70");
			form.setPid(list040.get(0).getPid());
//			form.setPid("70000133");
			form.setMsgsource(Constants.DATA_SOURCE_YBMAP);
			Mi401 mi401 = webApi302Service.insertCustomizationWaitMessage(form);
			form.setCommsgid(mi401.getCommsgid());
			form.setCenterid(mi401.getCenterid());
			form.setPusMessageType(mi401.getPusMessageType());
			
//			webApi302Service.webapi30202_insertWaitSend(form, modelMap, request, response);
			webApi302Service.insertSendTable(form ,request, response);
		}catch(Exception e){
			modelMap.clear();
			modelMap.put("recode", "000001");
			modelMap.put("remsg", e.getMessage());
			log.info(LOG.END_BUSIN.getLogText("核心推送个人短信息结束"));
			return "";
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("remsg", "处理成功");
		log.info(LOG.END_BUSIN.getLogText("渠道调用定制（报盘）推送接口结束"));
		return "";
		//end		以下为增加保山临时处理办法前代码逻辑
		}
	}
	
	/**
	 * add by lwj @2017-05-28 保山自定义短信临时解决方案
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private HashMap sendSmsCheckAndMessage00087500(CMi401 form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		AES aes = new AES(form.getCenterid() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		String rep = "";
		String seqno = CommonUtil.getSystemDateNumOnly();
		System.out.println("【保山自定义短信临时解决方案，渠道主动发送短信息"+form.getCenterid()+"】---等推送短信息流水号---【"+seqno+" , 手机号："+form.getTel()+"】");
		
		//	取YF接口地址
		Mi011 mi011 = null;
		Mi011Example example = new Mi011Example();
		List<Mi011> resultList = new ArrayList<Mi011>();
		Mi011DAO mi011Dao = (Mi011DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi011Dao");
		String req = "";
		try {
			example.createCriteria().andCenteridEqualTo(form.getCenterid());
			resultList = mi011Dao.selectByExample(example);
		
			if (CommonUtil.isEmpty(resultList)) {
				throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯处理类失败，通讯表中无记录");
			}else {
				if(CommonUtil.isEmpty(resultList.get(0).getClassname())) {
					throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯处理类失败，通讯表通信接口类为空");
				}
			}
			mi011 = resultList.get(0);
		
		} catch (NoRollRuntimeErrorException e) {
			throw e;
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "未知错误，请查看日志或者联系管理员。");
			throw tre;
		}
		String url = mi011.getUrl();
		System.out.println("【保山自定义短信临时解决方案，获取接口地址成功:【" + url + "】");
		
		HashMap hashMap = new HashMap();
		hashMap.put("channel", form.getChannel());
		hashMap.put("centerId", form.getCenterid());
		hashMap.put("phone", aes.encrypt(form.getTel().getBytes()));
		hashMap.put("elements", aes.encrypt(form.getTsmsg().getBytes("utf-8")));
		url = url +"/appapi50098.json";
		System.out.println("###### ===== SMS ===== URL:"+url);
		System.out.println("【渠道主动发送短信息"+form.getCenterid()+"】---等推送短信息流水号---【"+seqno+"】,URL："+url);
		rep = msm.sendPost(url, hashMap, encoding);
		
		System.out.println("【渠道主动发送短信息"+form.getCenterid()+"】---等推送短信息流水号---【"+seqno+"】，返回信息为【"+rep+"】");
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("recode"))){
			return remap;
		}else{
			return null;
		}
	}
	
	
	/**
	 * @deprecated 渠道接口——模板消息推送接口
	 * @param form 城市码，电话号，
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/** @deprecated */
	  @RequestMapping({"/appapi30291.{ext}"})
	  public String appapi30291(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
	    Logger log = LoggerUtil.getLogger();
	    log.info(ERRCODE.LOG.START_BUSIN.getLogText(new String[] { "核心推送个人短信息开始" }));
	    System.out.println("YBMAPZH : appapi30291 ,centerid:" + form.getCenterid());
	    log.debug("YBMAPZH : appapi30291 ,centerid : " + ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { CommonUtil.getStringParams(form) }));
	    log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(new String[] { CommonUtil.getStringParams(form) }));
	    log.debug("【***推送开始时间1***】" + CommonUtil.getSystemDate());
	    ApiUserContext.getInstance();
	    if (CommonUtil.isEmpty(form.getCenterid())) {
	      log.error(ERRCODE.ERROR.PARAMS_NULL.getLogText(new String[] { "centerId" }));
	      throw new TransRuntimeErrorException(ERRCODE.WEB_ALERT.PARAMS_NULL.getValue(), new String[] { "城市中心ID" });
	    }
	    String commsgid = "";
	    String mbzf;
	    try { mbzf = PropertiesReader.getProperty("properties.properties", 
	        "mbzf_" + form.getCenterid()).trim();
	    }
	    catch (Exception e)
	    {
	      mbzf = "";
	    }
	    try
	    {
//	      change by Carter King 
		   if ("18".equals(form.getTheme())) {
//	      if (!"18".equals(form.getTheme())) {
	        AppApi50004Form form50004 = new AppApi50004Form();
	        form50004.setSmstype("03");
	        form50004.setMessage(form.getApiData1());
	        form50004.setVcode(form.getApiData2());
	        form50004.setTel(form.getApiData3());
	        form50004.setJgh(form.getApiData4());
	        form50004.setCenterId(form.getCenterid());
	        form50004.setSendTheme(form.getTheme());
	        System.out.println("【核心系统发送短信验证码处理】");
	        HashMap hm = this.webApi302Service.sendSmsCheckAndMessage(form50004, request, response);
	        System.out.println("hashmap================"+hm.toString());
	        modelMap.clear();
	        modelMap.put("recode", hm.get("recode"));
	        modelMap.put("msg", hm.get("msg"));
	        modelMap.put("miSeqno", "");
	        log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { "核心推送个人短信息结束" }));
	        return "";
	      }
		   //注释by Carter King 20180228
/*	      if (mbzf.indexOf("," + form.getTheme() + ",") >= 0) {
	        AppApi50004Form form50004 = new AppApi50004Form();
	        form50004.setSmstype("03");
	        form50004.setMessage(form.getTsmsg());
	        form50004.setTel(form.getTel());
	        form50004.setCenterId(form.getCenterid());
	        form50004.setSendTheme(form.getTheme());
	        form50004.setJgh(form.getJgh() == null ? "53" : form.getJgh());
	        System.out.println("【核心系统发送短信验证码处理】");
	        HashMap hm = this.webApi302Service.sendSmsCheckAndMessage(form50004, request, response);
	        modelMap.clear();
	        System.out.println("【核心系统发送短信验证码处理】" + hm.get("success"));
	        System.out.println("【核心系统发送短信验证码处理】" + "true".equals(hm.get("success")));
	        if ("true".equals(hm.get("success").toString())) {
	          modelMap.put("recode", "000000");
	          modelMap.put("msg", "短信发送成功");
	        } else {
	          modelMap.put("recode", "000009");
	          modelMap.put("msg", "短信发送失败");
	        }
	        modelMap.put("miSeqno", "");
	        log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { "核心推送个人短信息结束" }));
	        return "";
	      }*/

	      AppApi50001Form form1 = new AppApi50001Form();
	      form1.setCenterId(form.getCenterid());
	      form1.setTel(form.getTel());
	      form1.setChannel("70");
	      form1.setUserId(form.getTel());
	      log.debug("【***推送开始时间2***】" + CommonUtil.getSystemDate());
	      //查询mi029中渠道有没有该用户
	      Mi029 mi029 = this.webApi029ServiceImpl.webapi02910(form1);
	      if (CommonUtil.isEmpty(mi029))
	      {
	        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
	        Mi011DAO mi011Dao = (Mi011DAO)SpringContextUtil.getBean("mi011Dao");
	        Mi011Example example = new Mi011Example();
	        example.createCriteria().andCenteridEqualTo(form.getCenterid());
	        List resultList = mi011Dao.selectByExample(example);
	        if (CommonUtil.isEmpty(resultList)) {
	          throw new TransRuntimeErrorException(ERRCODE.ERROR.CONNECT_ERROR.getValue(), new String[] { "获取通讯处理类失败，通讯表中无记录" });
	        }
	        if (CommonUtil.isEmpty(((Mi011)resultList.get(0)).getClassname())) {
	          throw new TransRuntimeErrorException(ERRCODE.ERROR.CONNECT_ERROR.getValue(), new String[] { "获取通讯处理类失败，通讯表通信接口类为空" });
	        }

	        Mi011 result = (Mi011)resultList.get(0);
	        //注释by  Carter King 宁波不用确认个人信息
/*	        String url = result.getUrl() + "/webapi50019.json";//通过手机号确认并更新个人信息
	        HashMap map = new HashMap();
	        map.put("tel", form.getTel());
	        map.put("centerId", form.getCenterid());
	        String rep = msm.sendPost(url, map, request.getCharacterEncoding());
	        HashMap repmap = (HashMap)JsonUtil.getGson().fromJson(rep, HashMap.class);
	        log.debug("【***推送开始时间3***】" + CommonUtil.getSystemDate());
	        if ("000000".equals((String)repmap.get("recode")))
	        {
	          if ("1".equals((String)repmap.get("isExist"))) {
	            log.debug("【***推送开始时间3.1***】" + CommonUtil.getSystemDate());
	            form1.setBodyCardNumber((String)repmap.get("certinum"));
	            mi029 = this.webApi029ServiceImpl.webapi02901(form1);
	            if (!CommonUtil.isEmpty(mi029)) {
	              if (!mi029.getTel().trim().equals(form.getTel())) {
	                mi029.setTel(form.getTel());
	              }
	              this.webApi029ServiceImpl.webapi02915(form1, request, response);
	              this.webApi029ServiceImpl.webapi02917(mi029, form1, repmap);
	              List list040 = this.webApi029ServiceImpl.webapi02923(form1, request, response);
	              if (!CommonUtil.isEmpty(list040)) {
	                request.setAttribute("MI040Pid", ((Mi040)list040.get(0)).getPid());
	                this.webApi029ServiceImpl.webapi02912(mi029, form1, request, response);
	              }
	            } else {
	              mi029 = this.webApi029ServiceImpl.webapi02916(form1, repmap);
	              log.debug("【***推送开始时间3.2***】" + CommonUtil.getSystemDate());
	              List list040 = this.webApi029ServiceImpl.webapi02923(form1, request, response);
	              System.out.println("###$$$%%%  ========  核心单笔推送6");
	              if (!CommonUtil.isEmpty(list040)) {
	                request.setAttribute("MI040Pid", ((Mi040)list040.get(0)).getPid());
	                System.out.println("###$$$%%%  ========  核心单笔推送7");
	                this.webApi029ServiceImpl.webapi02912(mi029, form1, request, response);
	                System.out.println("###$$$%%%  ========  核心单笔推送8");
	              }
	            }
	          } else {
	            modelMap.clear();
	            modelMap.put("recode", "999999");
	            modelMap.put("msg", "客户信息不存在！");
	            modelMap.put("miSeqno", "");
	            return "";
	          }
	        } else {
	          modelMap.clear();
	          modelMap.put("recode", "999999");
	          modelMap.put("msg", "客户信息不存在");
	          modelMap.put("miSeqno", "");
	          return "";
	        }*/
	        log.debug("【***推送开始时间4***】" + CommonUtil.getSystemDate());

	        List list040 = this.webApi029ServiceImpl.webapi02923(form1, request, response);
	        if (!CommonUtil.isEmpty(list040)) {
	          request.setAttribute("MI040Pid", ((Mi040)list040.get(0)).getPid());
	          this.webApi029ServiceImpl.webapi02912(mi029, form1, request, response);
	        }
	        log.debug("【***推送开始时间5***】" + CommonUtil.getSystemDate());
	      } else {
	        System.out.println("###$$$%%%  ========  核心单笔推送21");
	        //查询mi040表里70渠道是否存在
	        List list040 = this.webApi029ServiceImpl.webapi02923(form1, request, response);
	        if (!CommonUtil.isEmpty(list040)) {
	          System.out.println("###$$$%%%  ========  核心单笔推送22");
	          request.setAttribute("MI040Pid", ((Mi040)list040.get(0)).getPid());
	          //渠道用户表mi031查询，条件pid:70000133 ,UserId手机号码
	          this.webApi029ServiceImpl.webapi02912(mi029, form1, request, response);
	        }
	      }
	      log.debug("【***推送开始时间6***】" + CommonUtil.getSystemDate());
	      commsgid = this.webApi302Service.insertTemplateParam(form, mi029, request, response);
	      log.debug("【***推送结束时间7***】" + CommonUtil.getSystemDate());
	    } catch (Exception e) {
	      e.printStackTrace();
	      modelMap.clear();
	      modelMap.put("recode", "999999");
	      modelMap.put("msg", "系统异常请联系管理员");
	      modelMap.put("miSeqno", commsgid);
	      return "";
	    }
	    if (CommonUtil.isEmpty(commsgid)) {
	      modelMap.clear();
	      modelMap.put("recode", "999999");
	      modelMap.put("msg", form.getSendseqno() + "消息ID无数据");
	      return "";
	    }

	    modelMap.clear();
	    modelMap.put("recode", "000000");
	    modelMap.put("msg", "处理成功");
	    modelMap.put("miSeqno", commsgid);
	    log.info(ERRCODE.LOG.END_BUSIN.getLogText(new String[] { "核心推送个人短信息结束" }));
	    return "";
	  }	
	
	/**
	 * @deprecated 渠道调用定制（报盘）推送接口
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi30292.{ext}")
	public String appapi30292(CMi401 form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String commsgid = "";	
		log.info(LOG.START_BUSIN.getLogText("渠道调用定制（报盘）推送接口开始"));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		AES aes = new AES(form.getCenterid() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
		
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerid"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
//		if(CommonUtil.isEmpty(form.getCertinum())){
//			log.error(ERROR.PARAMS_NULL.getLogText("certinum"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"证件号码");
//		}
		if(CommonUtil.isEmpty(form.getTel())){
			log.error(ERROR.PARAMS_NULL.getLogText("tel"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"电话号码");
		}
		if(CommonUtil.isEmpty(form.getTitle())){
//			log.error(ERROR.PARAMS_NULL.getLogText("title"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息标题");
			form.setTitle("渠道发送定制短信息");
		}
		if(CommonUtil.isEmpty(form.getDetail())){
//			log.error(ERROR.PARAMS_NULL.getLogText("detail"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息描述");
			form.setDetail("渠道发送定制短信息");
		}
//		if(CommonUtil.isEmpty(form.getTheme())){
//			log.error(ERROR.PARAMS_NULL.getLogText("theme"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息主题");
//		}
		if(CommonUtil.isEmpty(form.getTsmsg())){
			log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息内容");
		}
		request.setAttribute("centerId", form.getCenterid());
		String mi100key = "";
		try{
			List<Mi040> list040 = webApi302Service.getChannelPid(form.getCenterid(), "70");
			form.setPid(list040.get(0).getPid());
//			form.setPid("70000133");
			form.setMsgsource(Constants.DATA_SOURCE_YBMAP);
			Mi401 mi401 = webApi302Service.insertCustomizationWaitMessage(form);
			form.setCommsgid(mi401.getCommsgid());
			form.setCenterid(mi401.getCenterid());
			form.setPusMessageType(mi401.getPusMessageType());
			
			webApi302Service.insertSendTable(form ,request, response);
			commsgid = mi401.getCommsgid();
		}catch(Exception e){
			modelMap.clear();
			modelMap.put("recode", "000001");
			modelMap.put("remsg", e.getMessage());
			log.info(LOG.END_BUSIN.getLogText("核心推送个人短信息结束"));
			return "";
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("remsg", "处理成功");
		modelMap.put("miSeqno", commsgid);
		log.info(LOG.END_BUSIN.getLogText("渠道调用定制（报盘）推送接口结束"));
		return "";
	}	
	
	
	
	
//	@RequestMapping("/yfmapapi3003.{ext}")
//	public String yfmapapi3003(SendSelectApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//			
//		log.info(LOG.START_BUSIN.getLogText("核心推送个人短信息开始"));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));		
//
//		if(CommonUtil.isEmpty(form.getCenterId())){
//			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
//		}
//		if(CommonUtil.isEmpty(form.getSendNo())){
//			log.error(ERROR.PARAMS_NULL.getLogText("sendNo"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"核心流水号");
//		}
//		if(CommonUtil.isEmpty(form.getMiSeqno())){
//			log.error(ERROR.PARAMS_NULL.getLogText("miSeqno"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"");
//		}
//		request.setAttribute("centerId", form.getCenterId());
//		List<YfmapApi3003Result> list = webApi302Service.webapi30210_CSPSend_Select(form, modelMap, request, response);
//		if(list.size()==0){	
//			modelMap.clear();
//			modelMap.put("recode", "000001");
//			modelMap.put("remsg", "未查到相关数据");
//			return "";
//		}
//		
//		modelMap.clear();
//		modelMap.put("recode", "000000");
//		modelMap.put("remsg", "处理成功");
//		modelMap.put("list", list);
//		log.info(LOG.END_BUSIN.getLogText("核心推送个人短信息结束"));
//		return "";
//	}
	
	class YfmapThread implements Runnable 
	{ 
		private String msg; 
		private int seqno;
		public void setMsg(String msg) 
		{ 
			this.msg = msg;
		} 
		public void setSeqno(int seqno){
			this.seqno = seqno;			
		}
		public void run() 
		{ 
			System.out.println(msg);
			int sum = 0;
			for(int i=0;i<90000;i++){
				for(int j=0;j<9000;j++){
					sum++;
				}
			}
			System.out.println("线程执行完毕："+sum+"    msg:"+msg);
			Gson gson=new Gson();
			SendBatchApiCommonForm api3002 = gson.fromJson(msg, new TypeToken<SendBatchApiCommonForm>(){}.getType());
//			webApi302Service.webapi30210_CSPSend_Batch(api3002, null, null, null,seqno);
		} 
		
	}

	
	
	/**
	 * 模板消息单笔
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/yfmapapi3004.{ext}")
//	public String yfmapapi3004(SendMBApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//		System.out.println("3004核心推送模板信息开始SendApiContorller");	
//		log.info(LOG.START_BUSIN.getLogText("3004核心推送模板信息开始"));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));		
//
//		if(CommonUtil.isEmpty(form.getCenterId())){
//			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
//		}
//		if(CommonUtil.isEmpty(form.getSendKey())){
//			log.error(ERROR.PARAMS_NULL.getLogText("sendKey"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"关键字段");
//		}
//		if(CommonUtil.isEmpty(form.getTheme())){
//			log.error(ERROR.PARAMS_NULL.getLogText("theme"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息主题");
//		}		
//		if(CommonUtil.isEmpty(form.getChanel())){
//			log.error(ERROR.PARAMS_NULL.getLogText("chanel"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息渠道");
//		}
//		request.setAttribute("centerId", form.getCenterId());
//		String mi100key = "";
//		try{			
//			mi100key = webApi302Service.webapi30211_CSP_MB_Send_Alone(form, modelMap, request, response);
//		}catch(TransRuntimeErrorException e){
//			modelMap.clear();
//			modelMap.put("recode", "000001");
//			modelMap.put("remsg", e.getMessage());
//			modelMap.put("miSeqno", mi100key);
//			log.info(LOG.END_BUSIN.getLogText("3004核心推送模板信息结束"));
//			return "";
//		}
//		modelMap.clear();
//		modelMap.put("recode", "000000");
//		modelMap.put("remsg", "处理成功");
//		modelMap.put("miSeqno", mi100key);
//		log.info(LOG.END_BUSIN.getLogText("3004核心推送模板信息结束"));
//		return "";
//	}	
	
	/**
	 * 模板消息批量
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/yfmapapi3005.{ext}")
//	public String yfmapapi3005(SendMBApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//			
//		log.info(LOG.START_BUSIN.getLogText("3005核心推送批量模板信息开始"));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));		
//		if(CommonUtil.isEmpty(form.getCenterId())){
//			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
//		}
//		if(CommonUtil.isEmpty(form.getTitle())){
//			log.error(ERROR.PARAMS_NULL.getLogText("title"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息标题");
//		}
//		if(CommonUtil.isEmpty(form.getChanel())){
//			log.error(ERROR.PARAMS_NULL.getLogText("chanel"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"消息渠道");
//		}
//		if(CommonUtil.isEmpty(form.getFileName())){
//			log.error(ERROR.PARAMS_NULL.getLogText("fileName"));
//			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文件名");
//		}
//		request.setAttribute("centerId", form.getCenterId());
//		String mi100key = "";
//		try{			
//			mi100key = webApi302Service.webapi30211_CSP_MB_Send_Batch(form, modelMap, request, response);
//		}catch(Exception e){
//			modelMap.clear();
//			modelMap.put("recode", "000001");
//			modelMap.put("remsg", e.getMessage());
//			modelMap.put("miSeqno", mi100key);
//			log.info(LOG.END_BUSIN.getLogText("3005核心推送批量模板信息结束"));
//			return "";
//		}
//		modelMap.clear();
//		modelMap.put("recode", "000000");
//		modelMap.put("remsg", "处理成功");
//		modelMap.put("miSeqno", mi100key);
//		log.info(LOG.END_BUSIN.getLogText("3005核心推送批量模板信息结束"));
//		return "";
//	}
	
	
	
}

