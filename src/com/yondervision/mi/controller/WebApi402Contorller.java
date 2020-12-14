/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi102;
import com.yondervision.mi.dto.Mi102;
import com.yondervision.mi.service.WebApi402Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi403Contorller 
* @Description:中心客服通讯信息查询
* @author Caozhongyan
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi402Contorller {
	@Autowired
	private WebApi402Service webApi402ServiceImpl;

	public WebApi402Service getWebApi402ServiceImpl() {
		return webApi402ServiceImpl;
	}

	public void setWebApi402ServiceImpl(WebApi402Service webApi402ServiceImpl) {
		this.webApi402ServiceImpl = webApi402ServiceImpl;
	}

	/**
	 * 中心客服通讯信息填加
	 * @param form 中心客服通讯填加参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40205.{ext}")
	public String webapi40205(CMi102 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("中心客服通讯信息填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		webApi402ServiceImpl.webapi40205(form);			
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page402/page40201";
	}
	
	/**
	 * 中心客服通讯删除
	 * @param form 中心客服通讯信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40206.{ext}")
	public String webapi40206(HttpServletRequest request , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		String businName="中心客服通讯信息填加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(request.getParameter("serviceid")));
		// TODO 业务处理
		List<String> list=new ArrayList<String>();
		String[] ary=request.getParameter("serviceid").split(",");
		for(int i=0;i<ary.length;i++){
			list.add(ary[i]);
		} 
		webApi402ServiceImpl.webapi40206(list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page402/page40201";
	}
	
	/**
	 * 中心客服通讯信息修改
	 * @param form 中心客服通讯修改参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40207.{ext}")
	public String webapi40207(CMi102 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("中心客服通讯信息填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		int i = webApi402ServiceImpl.webapi40207(form);	
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page402/page40201";
	}
	
	/**
	 * 中心客服通讯信息查询
	 * @param form 中心客服通讯信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40208.{ext}")
	public String webapi40208(CMi102 form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("中心客服通讯信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));		
	
		List<Mi102> list = webApi402ServiceImpl.webapi40208(form);
		if(list.isEmpty()){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"中心客服通讯信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		modelMap.put("total",list.size());
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page402/page40201";
	}	
}
