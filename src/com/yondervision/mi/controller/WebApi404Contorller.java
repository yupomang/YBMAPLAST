/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi108;
import com.yondervision.mi.dto.Mi108;
import com.yondervision.mi.form.WebApi40401Form;
import com.yondervision.mi.result.WebApi40401_queryResult;
import com.yondervision.mi.service.WebApi404Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi404Contorller 
* @Description: 意见反馈信息查询
* @author Caozhongyan
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi404Contorller {
	@Autowired
	private WebApi404Service webApi404ServiceImpl;

	public WebApi404Service getWebApi404ServiceImpl() {
		return webApi404ServiceImpl;
	}

	public void setWebApi401ServiceImpl(WebApi404Service webApi404ServiceImpl) {
		this.webApi404ServiceImpl = webApi404ServiceImpl;
	}

	/**
	 * 意见反馈信息查询
	 * @param form 意见反馈信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40401.{ext}")
	public String webapi40401(WebApi40401Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("意见反馈信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// TODO 业务处理
		WebApi40401_queryResult queryResult = webApi404ServiceImpl.webapi40401(form);
		if(queryResult.getList108().isEmpty()||queryResult.getList108().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"意见反馈信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList108());		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page404/page40401";
	}
	
	/**
	 * 确认人意见
	 * @param form 确认人意见参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40402.{ext}")
	public String webapi40402(CMi108 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("确认人意见");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));	
		if(CommonUtil.isEmpty(form.getUserid())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.APP_NO_LOGIN
					.getValue(),"用户ID");
		}
		// TODO 业务处理
		int i = webApi404ServiceImpl.webapi40402(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page404/page40402";
	}
}
