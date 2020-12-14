/**
 * 
 */
package com.yondervision.mi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi020Form;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.security.AES;


/** 
* @ClassName: AppApi024Contorller 
* @Description: 结息对账单查询
* @author Caozhongyan
* @date 2016年7月20日 下午10:06:03   
* 
*/ 
@Controller
public class AppApi025Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}
	
	/**
	 * @deprecated 结息对账单查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02501.{ext}")
	public String appapi02501(AppApi020Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("结息对账单查询");	
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
	 * @deprecated 根据单位账号查询U盾信息
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02502.{ext}")
	public String appapi02502(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("根据单位账号查询U盾信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		request.setAttribute("centerId", form.getCenterId());
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
			String rep=msgSendApi001Service.send(request, response).replace("null", "\"\"");
//			String rep="{\"trsCode\":\"1001\",\"trsChildCode\":\"1\",\"resTime\":\"\",\"resCode\":\"000\",\"chanCode\":\"NETHALL\",\"resMsg\":\"交易成功\",\"resTrsTell\":\"130800013539\",\"resTrsBank\":\"05740013\",\"resDate\":\"{'subSystem':'10'}\",\"resTrsCent\":\"\",\"unitCode\":\"NBGJJ\"},\"web\":{\"id\":\"3182705\",\"tradeStatus\":\"0\",\"pool\":\"{'unitcertitype':'R','ukeyflag':'0','header5':'','ukeystate':'2','ukeystate5':'','ukeystate4':'','ukeystate3':'','ukeystate2':'','header4':'','ukeystate1':'2','header3':'','header2':'','header1':'李喆','orgcode':'91330200079206801M','unitaccname':'宁波旭贤教育投资管理有限公司','ukeyflag3':'','ukeyflag2':'','ukeyflag5':'','ukeyflag4':'','ukeyflag1':'1','headerphone':'18157426050','headerphone5':'','recode':'000000','ukeynum2':'','headerphone2':'','headerphone1':'','ukeynum3':'','headerphone4':'','ukeynum1':'80G903631751','headerphone3':'','unitcertinum':'91330200079206801M','ukeynum4':'','ukeynum5':'','msg':'成功','header':'李喆','ukeynum':'3169944082'}";
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
			response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
			log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}else
		{
			String rep=msgSendApi001Service.send(request, response).replace("null", "\"\"");
			response.getOutputStream().write(rep.getBytes("UTF-8"));
			log.info("rep="+rep);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "/index";
		}
	}	
	
	/**
	 * @deprecated 根据U盾序列号查询U盾信息
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/appapi02503.{ext}")
	public String appapi02503(AppApi030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("根据U盾序列号查询U盾信息");	
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
	
