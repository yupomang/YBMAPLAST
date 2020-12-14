/**
 * 
 */
package com.yondervision.mi.controller;

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
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.security.AES;


/** 
* @ClassName: AppApi041Contorller 
* @Description: 单位业务_缴存基数变更
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03
* 
*/ 
@Controller
public class AppApi041Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	/**
	 * @deprecated 单位业务_缴存基数变更
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi04101.{ext}")
	public String appapi04101(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("缴存基数变更");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		ApiUserContext.getInstance();
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")){
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt("{\"msg\":\"成功\",\"recode\":\"000000\"}".getBytes("UTF-8")).getBytes("UTF-8"));
		}else
		{
			response.getOutputStream().write("{\"msg\":\"成功\",\"recode\":\"000000\"}".getBytes("UTF-8"));
		}

		System.out.println("新线程实例化");
		YbmapThread YbThread1 = new YbmapThread(); 
		YbThread1.setRequest(request);
		YbThread1.setResponse(response);
		YbThread1.setAESFlag(form.getAESFlag());
		YbThread1.setCenterId(form.getCenterId());
		YbThread1.setUserId(form.getUserId());
		YbThread1.setUsertype(form.getUsertype());
		YbThread1.setDeviceType(form.getDeviceType());
		YbThread1.setDeviceToken(form.getDeviceToken());
		YbThread1.setCurrenVersion(form.getCurrenVersion());
		YbThread1.setBuzType(form.getBuzType());
		YbThread1.setChannel(form.getChannel());
		YbThread1.setAppid(form.getAppid());
		YbThread1.setAppkey(form.getAppkey());
		YbThread1.setAppToken(form.getAppToken());
		YbThread1.setClientIp(form.getClientIp());
		YbThread1.setTellCode(form.getTellCode());
		YbThread1.setBrcCode(form.getBrcCode());
		YbThread1.setChannelSeq(form.getChannelSeq());
		YbThread1.setTranDate(form.getTranDate());
		YbThread1.setBusinName(form.getBusinName());
		YbThread1.setBodyCardNumber(form.getBodyCardNumber());
		YbThread1.setCertinumType(form.getCertinumType());
		YbThread1.setClientMAC(form.getClientMAC());
		YbThread1.setFlag(form.getFlag());
		YbThread1.setUnitaccnum(form.getUnitaccnum());
		YbThread1.setQdfilename(form.getQdfilename());
		YbThread1.setQdfilepath(form.getQdfilepath());
		YbThread1.setQdapprnum(form.getQdapprnum());
		YbThread1.setUKseq(form.getUKseq());
		YbThread1.setUnitaccname(form.getUnitaccname());
		YbThread1.setIsfinished(form.getIsfinished());
		YbThread1.setCoreflag(form.getCoreflag());
		
		Thread thread = new Thread(YbThread1);       			
		thread.start();
		System.out.println("新线程已开始");
		return "/index";
	}	
	
	
	/**
	 * @deprecated 基数调整结果查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi04102.{ext}")
	public String appapi04102(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("基数调整结果查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")){
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
	 * @deprecated 基数差额查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi04103.{ext}")
	public String appapi04103(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("基数差额查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")){
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
	
	
	public class YbmapThread implements Runnable 
    { 
		/** 全加密标志 */
		private String AESFlag = "";
		/** 中心ID */			
		private String centerId = "";			
		/** 用户ID */			
		private String userId = "";			
		/** 用户类型 */			
		private String usertype = "";			
		/** 设备区分 1-iOS,2-Android,3-pc */			
		private String deviceType = "";			
		/** 设备识别码 移动设备专用，非移动设备变量名上传，对应值为空 */			
		private String deviceToken = "";			
		/** 当前版本 对应渠道当前版本号，如果没有默认上传“1.0” */			
		private String currenVersion = "";			
		/** 业务类型 */			
		private String buzType = "";			
		private String channel = "";			
		/** 应用标识 */			
		private String appid = "";			
		/** 应用KEY */			
		private String appkey = "";			
		/** 应用TOKEN */			
		private String appToken = "";			
		/** 客户端IP地址 */			
		private String clientIp = "";			
		/** 网厅特殊参数:柜员号 */			
		private String tellCode = "";			
		/** 网厅特殊参数:机构号 */			
		private String brcCode = "";			
		/** 网厅特殊参数:实例号 */			
		private String channelSeq = "";			
		/** 网厅特殊参数:业务日期 */			
		private String tranDate = "";			
		/** 业务名称 */			
		private String businName = "";			
		/** 身份证后加入 */			
		private String bodyCardNumber = "";					
		/** 账户类型  1-身份证，2-军官证，3-护照，4-外国人永久居住证，5-港奥通行证*/	
		private String certinumType="";
		/**客户端MAC地址*/
		private String clientMAC = "";

	   private String flag = "";
       private String unitaccnum;//单位公积金账号
 	   private String qdfilename;//上传批量文件名
	   private String qdfilepath;//文件路径
	   private String qdapprnum;//渠道唯一标识
	   private String UKseq;//UK序列号
	   private String unitaccname;//单位名称
	   private String isfinished;//是否办结（0办结  1非办结）
	   private String coreflag;
	   private HttpServletRequest request;
	   private HttpServletResponse response;
	   
	   public void run() 
	   {    		   
		   try {
				request.setAttribute("AESFlag",AESFlag);
				request.setAttribute("centerId",centerId);
				request.setAttribute("userId",userId);
				request.setAttribute("usertype",usertype);
				request.setAttribute("deviceType",deviceType);
				request.setAttribute("deviceToken",deviceToken);
				request.setAttribute("currenVersion",currenVersion);
				request.setAttribute("buzType",buzType);
				request.setAttribute("channel",channel);
				request.setAttribute("appid",appid);
				request.setAttribute("appkey",appkey);
				request.setAttribute("appToken",appToken);
				request.setAttribute("clientIp",clientIp);
				request.setAttribute("tellCode",tellCode);
				request.setAttribute("brcCode",brcCode);
				request.setAttribute("channelSeq",channelSeq);
				request.setAttribute("tranDate",tranDate);
				request.setAttribute("businName",businName);
				request.setAttribute("bodyCardNumber",bodyCardNumber);
				request.setAttribute("certinumType",certinumType);
				request.setAttribute("clientMAC",clientMAC);
				request.setAttribute("flag",flag);
				request.setAttribute("coreflag",coreflag);
				request.setAttribute("unitaccnum",unitaccnum);
				request.setAttribute("qdfilename",qdfilename);
				request.setAttribute("qdfilepath",qdfilepath);
				request.setAttribute("qdapprnum",qdapprnum);
				request.setAttribute("UKseq",UKseq);
				request.setAttribute("unitaccname",unitaccname);
				request.setAttribute("isfinished",isfinished);
				String rep=msgSendApi001Service.send(request, response);
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
	  }

	public String getAESFlag() {
		return AESFlag;
	}

	public void setAESFlag(String aESFlag) {
		AESFlag = aESFlag;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getCurrenVersion() {
		return currenVersion;
	}

	public void setCurrenVersion(String currenVersion) {
		this.currenVersion = currenVersion;
	}

	public String getBuzType() {
		return buzType;
	}

	public void setBuzType(String buzType) {
		this.buzType = buzType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getTellCode() {
		return tellCode;
	}

	public void setTellCode(String tellCode) {
		this.tellCode = tellCode;
	}

	public String getBrcCode() {
		return brcCode;
	}

	public void setBrcCode(String brcCode) {
		this.brcCode = brcCode;
	}

	public String getChannelSeq() {
		return channelSeq;
	}

	public void setChannelSeq(String channelSeq) {
		this.channelSeq = channelSeq;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getBusinName() {
		return businName;
	}

	public void setBusinName(String businName) {
		this.businName = businName;
	}

	public String getBodyCardNumber() {
		return bodyCardNumber;
	}

	public void setBodyCardNumber(String bodyCardNumber) {
		this.bodyCardNumber = bodyCardNumber;
	}

	public String getCertinumType() {
		return certinumType;
	}

	public void setCertinumType(String certinumType) {
		this.certinumType = certinumType;
	}

	public String getClientMAC() {
		return clientMAC;
	}

	public void setClientMAC(String clientMAC) {
		this.clientMAC = clientMAC;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUnitaccnum() {
		return unitaccnum;
	}

	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}

	public String getQdfilename() {
		return qdfilename;
	}

	public void setQdfilename(String qdfilename) {
		this.qdfilename = qdfilename;
	}

	public String getQdfilepath() {
		return qdfilepath;
	}

	public void setQdfilepath(String qdfilepath) {
		this.qdfilepath = qdfilepath;
	}

	public String getQdapprnum() {
		return qdapprnum;
	}

	public void setQdapprnum(String qdapprnum) {
		this.qdapprnum = qdapprnum;
	}

	public String getUKseq() {
		return UKseq;
	}

	public void setUKseq(String uKseq) {
		UKseq = uKseq;
	}

	public String getUnitaccname() {
		return unitaccname;
	}

	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getIsfinished() {
		return isfinished;
	}

	public void setIsfinished(String isfinished) {
		this.isfinished = isfinished;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getCoreflag() {
		return coreflag;
	}

	public void setCoreflag(String coreflag) {
		this.coreflag = coreflag;
	}
	   

   }
}
	
